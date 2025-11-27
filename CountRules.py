import json

from utils.fileutils import GetAllFilePaths

def analyzeSonarQube():
    report_paths = GetAllFilePaths("/Users/username/evaluation/sonarqube-seed-reports/seed-reports/uncategorized")
    cnt = 0
    warnings = 0
    vul2cnt = dict()
    for report_path in report_paths:
        with open(report_path, "r", encoding="utf-8") as f:
            data = json.load(f)
            total = int(data["total"])
            warnings = warnings + total
            if total > 0:
                # print(report_path)
                cnt = cnt + 1
                warnings = warnings + total
            issues = data["issues"]
            for issue in issues:
                rule = issue["rule"]
                if rule not in vul2cnt.keys():
                    vul2cnt[rule] = 0
                vul2cnt[rule] = vul2cnt[rule] + 1
    print(vul2cnt)
    print("# Rules: " + str(len(vul2cnt.keys())))
    print("# Warnings: " + str(warnings))

def analyzeInfer():
    report_paths = GetAllFilePaths("/Users/username/evaluation/infer-oracle1-full-local/seed-reports")
    cnt = 0
    warnings = 0
    vul2cnt = dict()
    for report_path in report_paths:
        if not report_path.endswith(".json"):
            continue
        with open(report_path, "r", encoding="utf-8") as f:
            data = json.load(f)
            warnings = warnings + len(data)
            for item in data:
                bug_type = item["bug_type"]
                if bug_type not in vul2cnt.keys():
                    vul2cnt[bug_type] = 0
                vul2cnt[bug_type] = vul2cnt[bug_type] + 1
            # for issue in issues:
            #     rule = issue["rule"]
            #     if rule not in vul2cnt.keys():
            #         vul2cnt[rule] = 0
            #     vul2cnt[rule] = vul2cnt[rule] + 1
    print(vul2cnt)
    print("# Rules: " + str(len(vul2cnt.keys())))
    print("# Warnings: " + str(warnings))


def analyzeSpotBugs():
    report_paths = GetAllFilePaths("/Users/username/evaluation/spotbugs-oracle2-part1-local/seed-reports")
    # report_paths.extend(GetAllFilePaths("/Users/username/evaluation/spotbugs-oracle2-part2-local/seed-reports"))
    report_paths.extend(GetAllFilePaths("/Users/username/evaluation/spotbugs-oracle2-part3-local/seed-reports"))
    warnings = 0
    vul2cnt = dict()
    import xml.etree.ElementTree as ET
    for report_path in report_paths:
        with open(report_path, "r", encoding="utf-8") as f:
            try:
                tree = ET.parse(report_path)
                root = tree.getroot()
                bug_instances = root.findall('BugInstance')
                warnings = warnings + len(bug_instances)
                for bug_instance in bug_instances:
                    bug_type = bug_instance.get('type')
                    if bug_type not in vul2cnt.keys():
                        vul2cnt[bug_type] = 0
                    vul2cnt[bug_type] = vul2cnt[bug_type] + 1
            except ET.ParseError as e:
                print(f"Fail to parse XML: {e}")
    print(vul2cnt)
    print("# Rules: " + str(len(vul2cnt.keys())))
    print("# Warnings: " + str(warnings))

def analyzeSemgrep():
    warnings = 0
    vul2cnt = dict()
    report_paths = GetAllFilePaths("/Users/username/evaluation/semgrep-oracle3-full/seed-reports")
    print(len(report_paths))
    from parser.ReportParser import ParseSemgrepReport
    for report_path in report_paths:
        if not report_path.endswith(".json"):
            continue
        report = ParseSemgrepReport(report_path, "/Users/username/evaluation/semgrep-oracle3-full/mock", "/Users/username/evaluation/semgrep-oracle3-full/mock")
        violations = report.getViolations()
        warnings = warnings + len(violations)
        for violation in violations:
            vul_type = violation.getViolationType()
            if vul_type not in vul2cnt.keys():
                vul2cnt[vul_type] = 0
            vul2cnt[vul_type] = vul2cnt[vul_type] + 1
        # print(report)
    print("# Rules: " + str(len(vul2cnt.keys())))
    print("# Warnings: " + str(warnings))
            

def analyzeLockBud():
    warnings = 0
    vul2cnt = dict()
    report_paths = GetAllFilePaths("/Users/username/evaluation/lockbud-oracle1-full-local-test/seed-reports")
    from parser.ReportParser import ParseLockbudReport
    try:
        for report_path in report_paths:
            report = ParseLockbudReport(report_path, "/Users/username/evaluation/mock", "/Users/username/evaluation/mock")
            warnings = warnings + len(report.getViolations())
            for violation in report.getViolations():
                if violation.getViolationType() not in vul2cnt.keys():
                    vul2cnt[violation.getViolationType()] = 0
                vul2cnt[violation.getViolationType()] = vul2cnt[violation.getViolationType()] + 1
    except Exception as e:
        print(e)
        return None
    print("# Rules: " + str(len(vul2cnt.keys())))
    print("# Warnings: " + str(warnings))

def analyzePrusti():
    warnings = 0
    vul2cnt = dict()
    report_paths = GetAllFilePaths("/Users/username/evaluation/prusti-oracle1-full-local-test/seed-reports")
    from parser.ReportParser import ParsePrustiReport
    try:
        for report_path in report_paths:
            report = ParsePrustiReport(report_path, "/Users/username/evaluation/mock", "/Users/username/evaluation/mock")
            warnings = warnings + len(report.getViolations())
            for violation in report.getViolations():
                if violation.getViolationType() not in vul2cnt.keys():
                    vul2cnt[violation.getViolationType()] = 0
                vul2cnt[violation.getViolationType()] = vul2cnt[violation.getViolationType()] + 1
    except Exception as e:
        print(e)
        return None
    print("# Rules: " + str(len(vul2cnt.keys())))
    print("# Warnings: " + str(warnings))

def main():
    # analyzeInfer()
    # analyzeSonarQube()
    # analyzeSpotBugs()
    # analyzeSemgrep()
    # analyzeLockBud()
    analyzePrusti()

main()