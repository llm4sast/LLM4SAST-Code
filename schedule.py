from typing import List

from model.prompt import *
from utils.config import *
from utils.invoker import *
from utils.fileutils import *
from utils.diff import DiffAnalysis

from model.LLM import query
from parser.Report import Report
from parser.ReportParser import ParseLockbudReport, ParseRudraReport, ParseMircheckerReport, ParseSemgrepReport, ParsePrustiReport, ParseMiraiReport, ParseInferReport, ParseSonarQubeReport, ParseSpotBugsReport, ParsePMDReport

project2dep = dict()
project2crate = dict()
  
def TestingFromResponse(seed_reports: List[Report]):
    print("Testing from Response...")
    key2report = dict()
    for seed_report in seed_reports:
        key = seed_report.getRuleName() + os.sep + seed_report.getProjectName()
        if key in key2report:
            print("Unexpected error in seed mapping!")
            os.exit(-1)
        key2report[key] = seed_report
    bug_instances = []
    response_paths = GetAllFilePaths(RESPONSE_DIR_PATH)
    for response_path in response_paths:
        if ".DS_Store" in response_path:
            continue
        tokens = response_path.split(os.sep)
        project_name = tokens[-2]
        rule_name = tokens[-3]
        if SONARQUBE:
            rule_name = "uncategorized"
        mutant_dir_path = os.path.join(MUTANT_DIR_PATH, rule_name, project_name)
        mutant_report_dir_path = os.path.join(REPORT_DIR_PATH, rule_name, project_name)
        seed_report = key2report[rule_name + os.sep + project_name]
        if seed_report == None:
            print("Fail to get seed report file!")
            os.exit(-1)
        response = ReadFileToStr(response_path)
        file_len = len(GetAllFilePaths(mutant_dir_path))
        mutant_paths = []
        mutant_report_paths = []
        try:
            for uncleaned_code in response.split("==="):
                code = CleanCode(uncleaned_code)
                file_len = file_len + 1
                mutant_path = os.path.join(mutant_dir_path, "mutant_" + str(file_len) + ".java")
                mutant_report_path = os.path.join(mutant_report_dir_path, "mutant_" + str(file_len))
                WriteStrToFile(mutant_path, code)
                mutant_paths.append(mutant_path)
                mutant_report_paths.append(mutant_report_path)
        except Exception as e:
            print(e)
            return bug_instances
        for idx, mutant_report_path in enumerate(mutant_report_paths):
            mutant_path = mutant_paths[idx]
            mutant_report = InvokeSAST4Report(mutant_path, mutant_report_path, seed_report)
            if mutant_report != None:
                new_bug_instances = DiffAnalysis(seed_report, mutant_report)
                if len(new_bug_instances) > 0 != None:
                    bug_instances.extend(new_bug_instances)
    # for idx, seed_report in enumerate(seed_reports):
    #     print(f"ID: {idx}, Seed Path: {seed_report.getCodePath()}")
    #     mutant_paths = []
    #     mutant_report_paths = []
    #     rule_name = seed_report.getRuleName()
    #     project_name = seed_report.getProjectName()
    #     print(seed_report.getCodePath())
    #     if SEMGREP:
    #         project_name = project_name[:-3]
    #     mutant_dir_path = os.path.join(MUTANT_DIR_PATH, rule_name, project_name)
    #     mutant_report_dir_path = os.path.join(REPORT_DIR_PATH, rule_name, project_name)
    #     response_dir_path = os.path.join(RESPONSE_DIR_PATH, rule_name, project_name)
    #     print("response: " + str(response_dir_path))
    #     response_paths = GetAllFilePaths(response_dir_path)
    #     for response_path in response_paths:
    #         print(f"response_path: {response_path}")
    #         response = ReadFileToStr(response_path)
    #         file_len = len(GetAllFilePaths(mutant_dir_path))
    #         try:
    #             for uncleaned_code in response.split("==="):
    #                 code = CleanCode(uncleaned_code)
    #                 file_len = file_len + 1
    #                 mutant_path = os.path.join(mutant_dir_path, "mutant_" + str(file_len) + ".java")
    #                 mutant_report_path = os.path.join(mutant_report_dir_path, "mutant_" + str(file_len))
    #                 WriteStrToFile(mutant_path, code)
    #                 mutant_paths.append(mutant_path)
    #                 mutant_report_paths.append(mutant_report_path)
    #         except Exception as e:
    #             print(e)
    #             return bug_instances
    #     for idx, mutant_report_path in enumerate(mutant_report_paths):
    #         mutant_path = mutant_paths[idx]
    #         mutant_report = InvokeSAST4Report(mutant_path, mutant_report_path, seed_report)
    #         if mutant_report != None:
    #             new_bug_instances = DiffAnalysis(seed_report, mutant_report)
    #             if len(new_bug_instances) > 0 != None:
    #                 bug_instances.extend(new_bug_instances)
    return bug_instances

def InvokeSAST4Report(mutant_path, mutant_report_path, seed_report: Report) -> Report:
    if not SEMGREP and not mutant_path.endswith(".java"):
        rule_name = seed_report.getRuleName()
        project_name = seed_report.getProjectName()
        tool_name = seed_report.getToolName()
        project2dep = ReadJsonToDict(os.path.join(os.getcwd(), "dependency", tool_name + "_project2dep.json"))
        project2crate = ReadJsonToDict(os.path.join(os.getcwd(), "dependency", tool_name + "_project2crate.json"))
        dependency_path = project2dep[rule_name][project_name]
        crates_lib_path = project2crate[rule_name][project_name]
    mutant_report: Report = None
    if LOCKBUD and InvokeLockbud(mutant_path, mutant_report_path, dependency_path, crates_lib_path):
        mutant_report = ParseLockbudReport(mutant_report_path, mutant_path, seed_report.getProjectPath())
    if RUDRA and InvokeRudra(mutant_path, mutant_report_path, dependency_path, crates_lib_path):
        mutant_report = ParseRudraReport(mutant_report_path, mutant_path, seed_report.getProjectPath())
    if SEMGREP:
        mutant_report_path += ".json"
        if InvokeSemgrep(mutant_path, mutant_report_path):
            mutant_report = ParseSemgrepReport(mutant_report_path, mutant_path, seed_report.getProjectPath())
    if MIRCHECKER and InvokeMirChecker(mutant_path, mutant_report_path, dependency_path, crates_lib_path):
        mutant_report = ParseMircheckerReport(mutant_report_path, mutant_path, seed_report.getProjectPath())
    if MIRAI and InvokeMirai(mutant_path, mutant_report_path, dependency_path, crates_lib_path):
        mutant_report = ParseMiraiReport(mutant_report_path, mutant_path, seed_report.getProjectPath())
    if PRUSTI and InvokePrusti(mutant_path, mutant_report_path, dependency_path, crates_lib_path):
        mutant_report = ParsePrustiReport(mutant_report_path, mutant_path, seed_report.getProjectPath())
    
    if PMD:
        tokens = mutant_path.split(os.sep)
        rule_names = tokens[-3].split("_")
        category_name = rule_names[0]
        rule_name = rule_names[1]
        mutant_report_path += ".json"
        if InvokePMD(mutant_path, category_name, rule_name, mutant_report_path):
            mutant_report = ParsePMDReport(mutant_report_path, mutant_path, seed_report.getProjectPath())
    if SONARQUBE:
        InvokeFormatter(mutant_path)
        mutant_report_path += ".json"
        if InvokeSonarQube(mutant_path, mutant_report_path):
            mutant_report = ParseSonarQubeReport(mutant_report_path, mutant_path, seed_report.getProjectPath())
    tokens = mutant_path.split(os.sep)
    mutant_name = tokens[-1].split(".")[0]
    if INFER:
        class_dir_path = os.path.join(CLASS_DIR_PATH, tokens[-3], tokens[-2], mutant_name)
        InvokeFormatter(mutant_path)
        if InvokeInfer(mutant_path, mutant_report_path, class_dir_path):
            src_report_path = os.path.join(mutant_report_path, "report.json")
            target_report_path = mutant_report_path + ".json"
            if os.path.exists(src_report_path):
                shutil.move(src_report_path, target_report_path)
                mutant_report = ParseInferReport(target_report_path, mutant_path, seed_report.getProjectPath())
                shutil.rmtree(mutant_report_path)
    if SPOTBUGS:
        # seed_report_path = os.path.join(SEED_REPORT_PATH, tokens[-2], seed_name + ".json")
        class_dir_path = os.path.join(CLASS_DIR_PATH, tokens[-3], tokens[-2], mutant_name)
        InvokeFormatter(mutant_path)
        if CompileJavaSourceProgram(mutant_path, class_dir_path):
            if InvokeSpotBugs(class_dir_path, mutant_report_path):
                mutant_report = ParseSpotBugsReport(mutant_report_path, mutant_path, seed_report.getProjectPath())
            else:
                return None
        else:
            return None
    return mutant_report

# one-query mapping to one-violation
def RunOracle(prompt, seed_report: Report) -> List[List]:
    bug_instances = []
    rule_name = seed_report.getRuleName()
    project_name = seed_report.getProjectName()
    if SEMGREP:
        project_name = project_name[:-3]  
    response_dir_path = os.path.join(RESPONSE_DIR_PATH, rule_name, project_name)
    mutant_dir_path = os.path.join(MUTANT_DIR_PATH, rule_name, project_name)
    mutant_report_dir_path = os.path.join(REPORT_DIR_PATH, rule_name, project_name)
    mutant_paths: List[str] = []
    mutant_report_paths: List[str] = []
    
    suffix = "." + seed_report.getCodePath().split(".")[1]
    QUERY_LLM = CONFIG["QUERY_LLM"]
    if QUERY_LLM:
        # Generate new mutants and reports
        response_file_len = len(GetAllFilePaths(response_dir_path))
        response_path = os.path.join(response_dir_path, project_name + "_" + str(response_file_len + 1))
        response = query(llm_query, prompt)
        WriteStrToFile(response_path, response)
        file_len = len(GetAllFilePaths(mutant_dir_path))
        try:
            for uncleaned_code in response.split("==="):
                code = CleanCode(uncleaned_code)
                file_len = file_len + 1
                mutant_path = os.path.join(mutant_dir_path, "mutant_" + str(file_len) + suffix)
                mutant_report_path = os.path.join(mutant_report_dir_path, "mutant_" + str(file_len))
                WriteStrToFile(mutant_path, code)
                mutant_paths.append(mutant_path)
                mutant_report_paths.append(mutant_report_path)
        except Exception as e:
            print(e)
            return bug_instances
    else:
        # Read old mutants and reports
        mutant_paths = GetAllFilePaths(mutant_dir_path)
        for mutant_path in mutant_paths:
            mutant_report_path = mutant_path.replace("mutants", "reports")[:-3]
    for idx, mutant_report_path in enumerate(mutant_report_paths):
        mutant_path = mutant_paths[idx]
        mutant_report = InvokeSAST4Report(mutant_path, mutant_report_path, seed_report)
        if mutant_report != None:
            new_bug_instances = DiffAnalysis(seed_report, mutant_report)
            if len(new_bug_instances) > 0:
                bug_instances.extend(new_bug_instances)
    return bug_instances

def AutomatedTesting(seed_reports: List[Report]) -> List[List]:
    print("Begin to perform automated testing...")
    all_bug_instances = []
    for idx, seed_report in enumerate(seed_reports):
        tool_name = seed_report.getToolName()
        print(f"{idx} -> Query LLM for: {seed_report.getCodePath()}")
        for violation in seed_report.getViolations():
            bug_type = violation.getViolationType()
            if SPOTBUGS:
                if bug_type not in SPOTBUGS_RULE_NAMES:
                    continue
            # if INFER and (bug_type.startswith("INTEGER_OVERFLOW") or bug_type.startswith("BUFFER_OVERRUN")):
            #     bug_type = bug_type[:bug_type.rfind("_")]
            if SONARQUBE:
                bug_type = bug_type.replace("java:S", "RSPEC-")
            bug_doc = GetBugDocument(tool_name, bug_type)
            if bug_doc == None:
                print("No documents of rule: " + bug_type)
                continue
            if bug_type == "UNKNOWN" or bug_type == "TESTING" or bug_type == "TESTING1" or bug_type == "TESTING2" or bug_type == "TESTING3":
                continue
            if bug_type == "NOISE_FIELD_REFERENCE" or bug_type == "NOISE_METHOD_CALL" or bug_type == "NOISE_NULL_DEREFERENCE" or bug_type == "NOISE_OPERATION":
                continue
            if ORACLE1:
                oracle1_prompt = oracle1_prompt_template.format(
                    input_program = ReadFileToStr(seed_report.getCodePath()),
                    tool = tool_name,
                    report = violation.getPromptDescription(),
                    bug_type = bug_type,
                    bug_description = bug_doc
                )
                # print(oracle1_prompt)
                bug_instances = RunOracle(oracle1_prompt, seed_report)
                all_bug_instances.extend(bug_instances)
            if ORACLE2:
                oracle2_prompt = oracle2_prompt_template.format(
                    input_program = ReadFileToStr(seed_report.getCodePath()),
                    tool = tool_name,
                    report = violation.getPromptDescription(),
                    bug_type = bug_type,
                    bug_document = bug_doc
                )
                # print(oracle2_prompt)
                bug_instances = RunOracle(oracle2_prompt, seed_report)
                all_bug_instances.extend(bug_instances)
            if ORACLE3:
                oracle3_prompt = oracle3_prompt_template.format(
                    input_program = ReadFileToStr(seed_report.getCodePath()),
                    tool = tool_name,
                    report = violation.getPromptDescription(),
                    bug_type = bug_type,
                    bug_doc = bug_doc
                )
                # print(oracle3_prompt)
                bug_instances = RunOracle(oracle3_prompt, seed_report)
                all_bug_instances.extend(bug_instances)
    print("Automated testing is finished.")
    return all_bug_instances

def AnalyzeSeedProjects(seed_projects_path) -> List[Report]:
    print("Begin to analyze seed projects: " + seed_projects_path)
    seed_reports: List[Report] = []
    if SEMGREP:
        seed_project_paths = GetAllFilePaths(seed_projects_path)
    else:
        seed_project_paths = GetCargoProjectPaths(seed_projects_path)
    for idx, seed_project_path in enumerate(seed_project_paths):
        print(f"Index: {idx}, Seed Project Path: {seed_project_path}")
        tokens = seed_project_path.split(os.sep)
        rule_name = tokens[-2]
        seed_report_path = os.path.join(SEED_REPORT_PATH, rule_name, tokens[-1])
        seed_report = None
        if LOCKBUD:
            InvokeCargoLockbud(seed_project_path, seed_report_path)
            seed_report = ParseLockbudReport(seed_report_path, seed_project_path, seed_project_path)
        if RUDRA:
            InvokeCargoRudra(seed_project_path, seed_report_path)
            seed_report = ParseRudraReport(seed_report_path, seed_project_path, seed_project_path)
        if MIRCHECKER:
            if InvokeCargoMirChecker(seed_project_path, seed_report_path):
                seed_report = ParseMircheckerReport(seed_report_path, seed_project_path, seed_project_path)
        if SEMGREP:
            seed_report_path = seed_report_path[:-3] + ".json"
            InvokeSemgrep(seed_project_path, seed_report_path)
            seed_report = ParseSemgrepReport(seed_report_path, seed_project_path, seed_project_path)
        if MIRAI:
            InvokeCargoMirai(seed_project_path, seed_report_path)
            seed_report = ParseMiraiReport(seed_report_path, seed_project_path, seed_project_path)
        if PRUSTI:
            InvokeCargoPrusti(seed_project_path, seed_report_path)
            seed_report = ParsePrustiReport(seed_report_path, seed_project_path, seed_project_path)
        if seed_report == None:
            continue
        seed_reports.append(seed_report)
    return seed_reports

def AnalyzeJavaSeeds(seed_projects_path) -> List[Report]:
    seed_paths = GetAllFilePaths(seed_projects_path)
    print("Seed Size: " + str(len(seed_paths)))
    print("Begin to generate seed reports...")
    reports: List[Report] = []
    succ = 0
    fail = 0
    for seed_path in seed_paths:
        if not seed_path.endswith(".java"):
            print("Input is not Java file: " + seed_path)
            continue
        InvokeFormatter(seed_path)
        tokens = seed_path.split(os.sep)
        seed_name = tokens[-1].split(".")[0]
        rule_name = tokens[-2]
        if PMD:
            seed_report_path = os.path.join(SEED_REPORT_PATH, rule_name, seed_name + ".json")
            from parser.ReportParser import ParsePMDReport
            if InvokePMD(seed_path, rule_name, seed_report_path):
                if not os.path.exists(seed_report_path):
                    continue
                seed_report = ParsePMDReport(seed_report_path, seed_path, seed_path)
                reports.append(seed_report)
        if SONARQUBE:
            seed_report_path = os.path.join(SEED_REPORT_PATH, rule_name, seed_name + ".json")
            from parser.ReportParser import ParseSonarQubeReport
            if InvokeSonarQube(seed_path, seed_report_path):
                if os.path.exists(seed_report_path):
                    seed_report = ParseSonarQubeReport(seed_report_path, seed_path, seed_path)
                    reports.append(seed_report)
                else:
                    print("Seed report file is not existed: " + seed_report_path)
        if SPOTBUGS:
            seed_report_path = os.path.join(SEED_REPORT_PATH, rule_name, seed_name + ".xml")
            class_dir_path = os.path.join(CLASS_DIR_PATH, rule_name, seed_name, "seed")
            from parser.ReportParser import ParseSpotBugsReport
            if CompileJavaSourceProgram(seed_path, class_dir_path):
                succ = succ + 1
                if InvokeSpotBugs(class_dir_path, seed_report_path):
                    if not os.path.exists(seed_report_path):
                        continue
                    seed_report = ParseSpotBugsReport(seed_report_path, seed_path, seed_path)
                    reports.append(seed_report)
            else:
                fail = fail + 1
        if INFER:
            seed_report_dir_path = os.path.join(SEED_REPORT_PATH, rule_name, seed_name)
            class_dir_path = os.path.join(CLASS_DIR_PATH, rule_name, seed_name)
            from parser.ReportParser import ParseInferReport
            if InvokeInfer(seed_path, seed_report_dir_path, class_dir_path):
                succ = succ + 1
                src_seed_report_path = os.path.join(seed_report_dir_path, "report.json")
                target_seed_report_path = os.path.join(SEED_REPORT_PATH, rule_name, seed_name + ".json")
                if os.path.exists(src_seed_report_path):
                    shutil.move(src_seed_report_path, target_seed_report_path)
                    seed_report = ParseInferReport(target_seed_report_path, seed_path, seed_path)
                    reports.append(seed_report)
                shutil.rmtree(seed_report_dir_path)
            else:
                fail = fail + 1
    if SPOTBUGS or INFER:
        print("Succ compilation: " + str(succ))
        print("Fail compilation: " + str(fail))
    return reports
