import os

from typing import List

class Violation:
    def __init__(self, vul_type: str, start_line: int, end_line: int):
        self.__vul_type = vul_type
        self.__start_line = start_line
        self.__end_line = end_line
        # self.__start_column1 = start_column1
        # self.__start_column2 = start_column2
        # self.__end_column1 = end_column1
        # self.__end_column2 = end_column2
        # self.__description = description
        # self.__diagnosis = diagnosis

    def getViolationType(self):
        return self.__vul_type
    
    # def getDescription(self):
    #     return self.__description
    
    # def getDiagnosis(self):
    #     return self.__diagnosis
    
    def getPromptDescription(self) -> str:
        return f"Vulnerability Type: {self.__vul_type}, " + f"Start Line: {self.__start_line}, " + f"End Line: {self.__end_line}"
    
    def __str__(self):
        res = (f"Vulnerability Type: {self.__vul_type}"+"\n"
                f"Start Line: {self.__start_line}"+"\n"
                f"End Line: {self.__end_line}")
        # if self.__start_column1 == -1:
        #     res = (f"Vulnerability Type: {self.__vul_type}"+"\n"
        #         f"Start Line: {self.__start_line}"+"\n"
        #         f"End Line: {self.__end_line}")
        # else:
        #     res = (f"Vulnerability Type: {self.__vul_type}"+"\n"
        #         f"Description: {self.__description}"+"\n"
        #         f"Start Line: {self.__start_line}"+"\n"
        #         f"End Line: {self.__end_line}"
        #         f"Start Columns: ({self.__start_column1}, {self.__start_column2})"+"\n"
        #         f"End Columns: ({self.__end_column1}, {self.__end_column2})"+"\n")
        return res

class Report:
    def __init__(self, report_path, code_path, root_seed_path, tool_name: str):
        self.__report_path = report_path
        self.__code_path = code_path
        self.__root_seed_path = root_seed_path # seed project path in SEED directory
        tokens = code_path.split(os.sep)
        if tokens[-1].endswith(".java"):
            self.__project_name = tokens[-1].split(".")[0]
        else:
            self.__project_name = tokens[-1]
        self.__rule_name = tokens[-2]
        self.__tool_name = tool_name
        self.__violation_list: List[Violation] = []

    def appendViolation(self, violation: Violation):
        self.__violation_list.append(violation)
    
    def updateViolation(self, violations: List[Violation]):
        self.__violation_list.extend(violations)

    def getViolations(self) -> List[Violation]:
        return self.__violation_list

    def getReportPath(self):
        return self.__report_path
    
    def getRootSeedPath(self):
        return self.__root_seed_path

    # cargo -> return project path
    # rs file -> return file path = getCodePath()
    def getProjectPath(self):
        return self.__code_path

    # cargo proejct -> return code path
    def getCodePath(self):
        if self.__code_path.endswith(".rs") or self.__code_path.endswith(".java"):
            return self.__code_path
        target_path = self.__code_path + os.sep + "src" + os.sep + "main.rs"
        if os.path.exists(target_path):
            return target_path
        else:
            return self.__code_path + os.sep + "src" + os.sep + "lib.rs"

    def getToolName(self):
        return self.__tool_name
    
    def getProjectName(self):
        return self.__project_name
    
    def getRuleName(self):
        return self.__rule_name

    def getPromptDescription(self):
        # report_info = f"File name: {self.__project_name}\n"
        report_info = ""
        if not self.__violation_list:
            report_info += "No violations found.\n"
        else:
            report_info += f"Violation List Size: {len(self.__violation_list)}\n"
            for index,violation in enumerate(self.__violation_list):
                report_info += f"Violation {index + 1}:\n" + str(violation)
        return report_info

    def __str__(self):
        report_info = "\n------------------------Begin Report------------------------\n"
        report_info += f"Report Path: {self.__report_path}\n"
        report_info += f"Code Path: {self.__code_path}\n"
        if not self.__violation_list:
            report_info += "No violations found.\n"
        else:
            report_info += f"Violation List Size: {len(self.__violation_list)}\n"
            for index,violation in enumerate(self.__violation_list):
                report_info += f"Violation {index + 1}:\n" + str(violation) + "\n"
        report_info += "------------------------End Report------------------------\n"
        return report_info