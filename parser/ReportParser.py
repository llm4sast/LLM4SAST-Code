import toml, json
from parser.Report import Report, Violation
from parser.RudraReportParser import RudraReportParser
from parser.LockbudReportParser import LockbudReportParser
from parser.MiraiReportParser import MiraiReportParser
from parser.MircheckerReportParser import MircheckerReportParser
from parser.SemgrepReportParser import SemgrepReportParser
from parser.PrustiReportParser import PrustiReportParser
from parser.MiriReportParser import MiriReportParser

CRASH_REPORT_PATHS = []

def ParseMiriReport(report_path, code_path, root_seed_path):
    try:
        with open(report_path, "r") as file:
            report = file.read()
            parser = MiriReportParser(report)
            result = Report(report_path=report_path, code_path=code_path, root_seed_path=root_seed_path)
            result = parser.parse_single_report(result)
            return result
    except Exception as e:
        print(e)
        return None

def ParseRudraReport(report_path, code_path, root_seed_path):
    try:
        with open(report_path, 'r', encoding='utf-8') as file:
            toml_data = file.read()
        data = toml.loads(toml_data)
        reports = data.get('reports', [])
        result = Report(report_path=report_path, code_path=code_path, root_seed_path=root_seed_path)
        for report in reports:
            parser = RudraReportParser(report)
            result = parser.parse_single_report(result)
        return result
    except Exception as e:
        print(e)
        return None
    
def ParseLockbudReport(report_path, code_path, root_seed_path):
    try:
        with open(report_path, "r") as file:
            report = file.read()
            result = Report(report_path=report_path, code_path=code_path, root_seed_path=root_seed_path, tool_name = "LockBud")
            parser = LockbudReportParser(report)
            result = parser.parse_single_report(result)
            return result
    except Exception as e:
        print(e)
        return None
    
def ParseMiraiReport(report_path, code_path, root_seed_path):
    try:
        with open(report_path, "r") as file:
            report = file.read()
            parser = MiraiReportParser(report)
            result = Report(report_path=report_path, code_path=code_path, root_seed_path=root_seed_path)
            result = parser.parse_single_report(result)
            return result
    except Exception as e:
        print(e)
        return None

def ParseMircheckerReport(report_path, code_path, root_seed_path):
    try:
        with open(report_path, "r") as file:
            report = file.read()
            parser = MircheckerReportParser(report)
            result = Report(report_path=report_path,code_path=code_path, root_seed_path=root_seed_path)
            result = parser.parse_single_report(result)
            return result
    except Exception as e:
        print(e)
        return None

def ParseSemgrepReport(report_path, code_path, root_seed_path):
    try:
        with open(report_path, "r") as file:
            report = json.load(file)
            parser = SemgrepReportParser(report)
            result = Report(report_path=report_path, code_path=code_path, root_seed_path=root_seed_path, tool_name="semgrep")
            result = parser.parse_single_report(result)
            return result
    except Exception as e:
        print("Fail to parse semgrep report: " + report_path)
        print(type(e))
        print(e)
        return None

def ParsePrustiReport(report_path: str, code_path: str, root_seed_path: str) -> Report:
    try:
        with open(report_path, "r") as file:
            report = file.read()
            parser = PrustiReportParser(report)
            report = Report(report_path=report_path, code_path=code_path, root_seed_path=root_seed_path, tool_name="prusti")
            report = parser.parse_single_report(report)
            return report   
    except Exception as e:
        print(e)
        return None

def ParsePMDReport(report_path: str, code_path: str, root_seed_path: str) -> Report:
    report = Report(report_path, code_path, root_seed_path, "pmd")
    try:
        with open(report_path, "r") as json_file:
            root_node = json.load(json_file)
            report_nodes = root_node["files"]
            for report_node in report_nodes:
                filename = report_node["filename"]
                if filename != code_path:
                    print("Report is not equal to code_path: " + report_path)
                    print(filename)
                    print(code_path)
                    continue
                violations = report_node["violations"]
                for violation in violations:
                    vio_type = violation["rule"]
                    start_line = violation["beginline"]
                    end_line = violation["endline"]
                    violation = Violation(vio_type, start_line, end_line)
                    report.appendViolation(violation)
            if "processingErrors" in root_node.keys() or "configurationErrors" in root_node.keys():
                CRASH_REPORT_PATHS.append(report_path)      
    except json.JSONDecodeError:
        print(f"Exceptional Json Path: {report_path}")
        import traceback
        traceback.print_exc()
    except IOError:
        import traceback
        traceback.print_exc()
    return report

def ParseSpotBugsReport(report_path: str, code_path: str, root_seed_path: str) -> Report:
    report = Report(report_path, code_path, root_seed_path, "spotbugs")
    try:
        with open(report_path, "r") as json_file:
            root_node = json.load(json_file)
            report_nodes = root_node["files"]
            for report_node in report_nodes:
                filename = report_node["filename"]
                if filename != code_path:
                    print("Report is not equal to code_path: " + report_path)
                    print(filename)
                    print(code_path)
                    continue
                violations = report_node["violations"]
                for violation in violations:
                    vio_type = violation["rule"]
                    start_line = violation["beginline"]
                    end_line = violation["endline"]
                    violation = Violation(vio_type, start_line, end_line)
                    report.appendViolation(violation)
            if "processingErrors" in root_node.keys() or "configurationErrors" in root_node.keys():
                CRASH_REPORT_PATHS.append(report_path)      
    except json.JSONDecodeError:
        print(f"Exceptional Json Path: {report_path}")
        import traceback
        traceback.print_exc()
    except IOError:
        import traceback
        traceback.print_exc()
    return report

def ParseInferReport(report_path: str, code_path: str, root_seed_path: str) -> Report:
    report = Report(report_path, code_path, root_seed_path, "infer")
    try:
        with open(report_path, "r") as json_file:
            bug_nodes = json.load(json_file)
            for bug_node in bug_nodes:
                bug_type = bug_node["bug_type"]
                if bug_type.startswith("INTEGER_OVERFLOW") or bug_type.startswith("BUFFER_OVERRUN"):
                    bug_type = bug_type[:bug_type.rfind("_")]
                violation = Violation(bug_type, bug_node["line"], bug_node["line"])
                report.appendViolation(violation)
    except json.JSONDecodeError:
        print(f"Exceptional Json Path: {report_path}")
        import traceback
        traceback.print_exc()
    except IOError:
        import traceback
        traceback.print_exc()
    return report

import xml.etree.ElementTree as ET
def ParseSpotBugsReport(report_path: str, code_path: str, root_seed_path: str) -> Report:
    report = Report(report_path, code_path, root_seed_path, "spotbugs")
    try:
        tree = ET.parse(report_path)
        root = tree.getroot()
        for bug_instance in root.findall('BugInstance'):
            bug_type = bug_instance.get('type')
            for source_line in bug_instance.findall('SourceLine'):
                start_line = source_line.get("start")
                end_line = source_line.get("end")
                violation = Violation(
                    bug_type,
                    start_line,
                    end_line
                )
                report.appendViolation(violation)
    except ET.ParseError as e:
        print(f"Fail to parse XML: {e}")
        return None
    return report

def ParseSonarQubeReport(report_path: str, code_path: str, root_seed_path: str) -> Report:
    import os
    if not os.path.exists(report_path):
        print("Report path not existed: " + report_path)
        return None
    with open(report_path, "r", encoding="utf-8") as json_file:
        root = json.load(json_file)
        total = int(root["total"])
        if total > 10000:
            return None
        report = Report(report_path, code_path, root_seed_path, "sonarqube")
        issues = root["issues"]
        for issue in issues:
            if "component" in issue and "textRange" in issue:
                ruleName = issue["rule"]
                textRange = issue["textRange"]
                startLine = int(textRange["startLine"])
                endLine = int(textRange["endLine"])
                violation = Violation(ruleName, startLine, endLine)
                report.appendViolation(violation)
        return report