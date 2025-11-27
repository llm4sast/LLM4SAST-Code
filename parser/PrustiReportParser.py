import re
import toml
import os 
from typing import List
import json

from parser.Report import Report, Violation

prusti_verify_errors = {
    #CWE-125 CWE-787
    'the array or slice index may be out of bounds': 'out_of_bound',
    'the range end value may be out of bounds when slicing': 'out_of_bound',
    'the range end may be smaller than the start when slicing': 'out_of_bound',
    #CWE-190
    'with overflow': 'integer_overflow',
    'value might not fit into the target type': 'integer_overflow',
    #CWE-369
    'attempt to divide by zero': 'divided_by_zero',
    'attempt to calculate the remainder with a divisor of zero': 'divided_by_zero',
    #PANIC
    'panic!(..) statement might be reachable': 'Panic',
    'statement might panic': 'Panic',
    'unimplemented!(..) statement might be reachable': 'Panic',
    'unreachable!(..) statement might be reachable': 'Panic',
}
skip_errors = {
    'implicit type invariant expected by the function call might not hold': 'invariant',
    'the asserted expression might not hold': 'Assertion',
}

class prustiViolation:
    def __init__(self, vul_type, start_line, start_column1, start_column2, end_line, end_column1, end_column2):
        self.__vul_type = vul_type
        self.start_line = start_line
        self.end_line = end_line
        self.start_column1 = start_column1
        self.start_column2 = start_column2
        self.end_column1 = end_column1
        self.end_column2 = end_column2

    def getVulType(self):
        return self.__vul_type
    
    def __str__(self):
        return (f"\tVulnerability Type: {self.__vul_type}"+"\n"
                f"\tStart Line: {self.start_line}"+"\n"
                f"\tEnd Line: {self.end_line}"+"\n"
                f"\tStart Columns: ({self.start_column1}, {self.start_column2})"+"\n"
                f"\tEnd Columns: ({self.end_column1}, {self.end_column2})"+"\n"
        )

class PrustiReport:
    def __init__(self, report_path, code_path):
        self.report_path = report_path
        self.code_path = code_path
        self.__violation_list = []

    def appendViolation(self, prusti_violation: prustiViolation): 
        self.__violation_list.append(prusti_violation)
    
    def updateViolation(self, prusti_violations: List[prustiViolation]):
        self.__violation_list.extend(prusti_violations)
    
    def __str__(self):
        report_info = f"Report Path: {self.report_path}\n"
        report_info += f"Code Path: {self.code_path}\n"
        report_info += "Violations:\n"

        if not self.__violation_list:
            report_info += "  No violations found.\n"
        else:
            for index,violation in enumerate(self.__violation_list):
                report_info += f"  violation{index}:\n" + str(violation) +"\n"

        return report_info

class PrustiReportParser():
    def __init__(self, report):
        self.report = report

    def parse_tool(self):
        return "prusti"
    
    def parse_checker(self):
        return

    def parse_description(self):
        return

    def parse_location(self):
        return

    def parse_code(self):
        return

    def parse_single_report(self, result:Report):
        data = self.report
        lines = data.splitlines()
        for i, line in enumerate(lines):
            if line.startswith('warning: [Prusti: verification error]'):
                error_info = line[len('warning: [Prusti: verification error]'):].strip()
                if any(k in error_info for k, _ in prusti_verify_errors.items()):
                    bug_type = next((v for k, v in prusti_verify_errors.items() if k in error_info), None)
                else:
                    continue
                if i < len(lines) - 1:
                    next_line = lines[i+1]
                    if next_line.strip().startswith('-->'):
                        next_line = next_line.strip().replace('-->', '').strip()
                        file_path, _, line_col_info = next_line.partition(':')                        
                        line_col_info = line_col_info.split(' (')[0]
                        start_line, start_col = map(int, line_col_info.replace(' ', '').split(':'))
                        result.appendViolation(Violation(
                            vul_type=bug_type, 
                            start_line=start_line, 
                            end_line=start_line,
                            # start_column1=start_col, 
                            # start_column2=-1, 
                            # end_column1=-1, 
                            # end_column2=-1,
                            # description=error_info,
                            # diagnosis=None
                        ))
        
        return result
    
    def gen_prompt(self):
        return


if __name__ == '__main__':
    pass


