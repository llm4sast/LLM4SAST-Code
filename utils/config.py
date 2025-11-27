import os, toml
from shutil import rmtree
from utils.fileutils import GetJarListStr, ReadFileToLines

CONFIG = toml.load(os.path.join(os.getcwd(), "cfg"))

DEBUG = CONFIG["DEBUG"]
LOCAL_TESTING = CONFIG["LOCAL_TESTING"]
LLM_MODEL = CONFIG["LLM_MODEL"]
ORACLE1_MUTANT_CNT = CONFIG["ORACLE1_MUTANT_CNT"]
EVALUATION_PATH = CONFIG["EVALUATION_PATH"]
TIMEOUT = CONFIG["TIMEOUT"]
PMD_PATH = CONFIG["PMD_PATH"]
INFER_PATH = CONFIG["INFER_PATH"]
GUMTREE_PATH=CONFIG["GUMTREE_PATH"]
JAVAC_PATH = CONFIG["JAVAC_PATH"]
INFER_DEPENDENCY_DIR_PATH = CONFIG["INFER_DEPENDENCY_PATH"]
SPOTBUGS_PATH = CONFIG["SPOTBUGS_PATH"]
SPOTBUGS_DEPENDENCY_DIR_JAR = CONFIG["SPOTBUGS_DEPENDENCY_PATH"]
SPOPTBUGS_DEPENDENCY_PATH = GetJarListStr(SPOTBUGS_DEPENDENCY_DIR_JAR)
INFER_DEPENDENCY_PATH = GetJarListStr(INFER_DEPENDENCY_DIR_PATH)
GOOGLE_FORMAT_PATH = CONFIG["GOOGLE_FORMAT_PATH"]
SONARQUBE_PROJECT_KEY = CONFIG["SONARQUBE_PROJECT_KEY"]
SONAR_SCANNER_PATH = CONFIG["SONAR_SCANNER_PATH"]

ORACLE1 = CONFIG["OracleSelection"]["ORACLE1"]
ORACLE2 = CONFIG["OracleSelection"]["ORACLE2"]
ORACLE3 = CONFIG["OracleSelection"]["ORACLE3"]

LOCKBUD = CONFIG["ToolSelection"]["LOCKBUD"]
RUDRA = CONFIG["ToolSelection"]["RUDRA"]
SEMGREP = CONFIG["ToolSelection"]["SEMGREP"]
MIRCHECKER = CONFIG["ToolSelection"]["MIRCHECKER"]
MIRAI = CONFIG["ToolSelection"]["MIRAI"]
PRUSTI = CONFIG["ToolSelection"]["PRUSTI"]
PMD = CONFIG["ToolSelection"]["PMD"]
INFER = CONFIG["ToolSelection"]["INFER"]
SPOTBUGS = CONFIG["ToolSelection"]["SPOTBUGS"]
SONARQUBE = CONFIG["ToolSelection"]["SONARQUBE"]

MUTANT_DIR_PATH = os.path.join(EVALUATION_PATH, "mutants")
DIFF_DIR_PATH = os.path.join(EVALUATION_PATH, "diff")
REPORT_DIR_PATH = os.path.join(EVALUATION_PATH, "reports")
RESPONSE_DIR_PATH = os.path.join(EVALUATION_PATH, "response")
BUG_DIR_PATH = os.path.join(EVALUATION_PATH, "bugs")
CLASS_DIR_PATH = os.path.join(EVALUATION_PATH, "classes")

SEED_PATH = CONFIG["SEED_PATH"]
SEED_REPORT_PATH = os.path.join(EVALUATION_PATH, "seed-reports")
CHECKER_DIR_PATH = os.path.join(os.getcwd(), "checker")
PATCH_PATH = os.path.join(os.getcwd(), "patch")
CHECKER_DOC_PATH = os.path.join(os.getcwd(), "checker-doc")
UTILS_PATH = os.path.dirname(os.path.abspath(__file__))

SPOTBUGS_RULE_NAMES = ReadFileToLines("/Users/username/projects/LLM4SAST/resources/spotbugs-rule-names")

def CreateSubDirs(rule_names, tool_path):
    if not os.path.exists(tool_path):
        os.mkdir(tool_path)
    for rule_name in rule_names:
        if ".DS_Store" in rule_name:
            continue
        rule_path = os.path.join(tool_path, rule_name)
        if not os.path.exists(rule_path):
            os.mkdir(rule_path)
        project_names = os.listdir(os.path.join(SEED_PATH, rule_name))
        for project_name in project_names:
            if SEMGREP:
                project_name = project_name[:-3]
            if PMD or SPOTBUGS or SONARQUBE or INFER:
                project_name = project_name[:-5]
            project_path = os.path.join(rule_path, project_name)
            if not os.path.exists(project_path):
                os.mkdir(project_path)

def InitLocalEva(tool_seeds_path: str):
    if os.path.exists(REPORT_DIR_PATH):
            rmtree(REPORT_DIR_PATH)
    if os.path.exists(MUTANT_DIR_PATH):
        rmtree(MUTANT_DIR_PATH)
    if os.path.exists(DIFF_DIR_PATH):
        rmtree(DIFF_DIR_PATH)
    if os.path.exists(SEED_REPORT_PATH):
        rmtree(SEED_REPORT_PATH)
    if os.path.exists(BUG_DIR_PATH):
        rmtree(BUG_DIR_PATH)
    if os.path.exists(CLASS_DIR_PATH):
        rmtree(CLASS_DIR_PATH)
    os.mkdir(REPORT_DIR_PATH)
    os.mkdir(MUTANT_DIR_PATH)
    os.mkdir(DIFF_DIR_PATH)
    os.mkdir(SEED_REPORT_PATH)
    os.mkdir(BUG_DIR_PATH)
    os.mkdir(CLASS_DIR_PATH)
    rule_names = os.listdir(tool_seeds_path)
    for rule_name in rule_names:
        rule_path = os.path.join(SEED_REPORT_PATH, rule_name)
        os.mkdir(rule_path)
    CreateSubDirs(rule_names, MUTANT_DIR_PATH)
    CreateSubDirs(rule_names, DIFF_DIR_PATH)
    CreateSubDirs(rule_names, REPORT_DIR_PATH)
    CreateSubDirs(rule_names, CLASS_DIR_PATH)

def InitEva(tool_seeds_path: str):
    os.mkdir(EVALUATION_PATH)
    os.mkdir(SEED_REPORT_PATH)
    os.mkdir(REPORT_DIR_PATH)
    os.mkdir(MUTANT_DIR_PATH)
    os.mkdir(DIFF_DIR_PATH)
    os.mkdir(RESPONSE_DIR_PATH)
    os.mkdir(BUG_DIR_PATH)
    os.mkdir(CLASS_DIR_PATH)
    rule_names = os.listdir(tool_seeds_path)
    for rule_name in rule_names: 
        rule_path = os.path.join(SEED_REPORT_PATH, rule_name)
        os.mkdir(rule_path)
    CreateSubDirs(rule_names, MUTANT_DIR_PATH)
    CreateSubDirs(rule_names, DIFF_DIR_PATH)
    CreateSubDirs(rule_names, REPORT_DIR_PATH)
    CreateSubDirs(rule_names, RESPONSE_DIR_PATH)
    CreateSubDirs(rule_names, CLASS_DIR_PATH)