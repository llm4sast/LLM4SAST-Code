import re
import toml
from parser.Report import Report, Violation
import os 
from typing import List
import json

mirai_verify_errors = {
#CWE-369
'divide by zero' : 'divided_by_zero',
'calculate the remainder with a divisor of zero' : 'divided_by_zero',
# CWE-190
'attempt to divide with overflow' : 'integer_overflow',
'attempt to calculate the remainder with overflow' : 'integer_overflow',
'attempt to shift left with overflow' : 'integer_overflow',
'attempt to multiply with overflow' : 'integer_overflow',
'attempt to negate with overflow' : 'integer_overflow',
'attempt to add with overflow' : 'integer_overflow',
'attempt to shift right with overflow' : 'integer_overflow',
# CWE-191
'attempt to subtract with overflow' : 'integer_overflow',
# CWE-129
'slice index starts at after slice end':'out_of_bound',
# CWE-125 CWE-787
'capacity overflow' : 'out_of_bound',
r'range end index {} out of range for slice of length {}':'out_of_bound',
r'range start index {} out of range for slice of length {}':'out_of_bound',
r'source slice length ({}) does not match destination slice length ({})':'out_of_bound',
'slice length exceeds capacity':'out_of_bound',
'index out of bounds':'out_of_bound',
'byte index is not a char boundary':'out_of_bound',
'effective offset is outside allocated range':'out_of_bound',
# CWE-188
'possible misaligned pointer dereference':'pointer_misalignment',
'Box<Shared> should have an aligned pointer':'pointer_misalignment',
'deallocates the pointer with layout information inconsistent with the allocation':'pointer_misalignment',
'reallocates the pointer with layout information inconsistent with the allocation':'pointer_misalignment',
# CWE-416
'the pointer points to memory that has already been deallocated':'use_after_free_or_double_free',
# CWE-908
'The union is not fully initialized by this assignment':'uninitialization',
# CWE-617
'statement is reachable':'statement is reachable',
'panic with':'panic',
'explicit panic':'panic',
'assertion failed':'assertion failed',
'Option::unwrap()':'Option::unwrap()',
'unsatisfied precondition':'unsatisfied precondition',
'unsatisfied postcondition':'unsatisfied postcondition',
'false verification condition':'false verification condition',

#others
'assumption is provably true and can be deleted':'assumption is provably true and can be deleted',
'this is unreachable, mark it as such by using the verify_unreachable! macro':'this is unreachable, mark it as such by using the verify_unreachable! macro',
'multiple post conditions must be on the same execution path':'multiple post conditions must be on the same execution path',
'assumption is provably false and it will be ignored':'assumption is provably false and it will be ignored',
}
skip_errors = {
'The analysis of this function timed out':'The analysis of this function timed out',
'incomplete analysis of call':'Incomplete analysis of call',
'the called function could not be completely analyzed':'The called function could not be completely analyzed',
'the called function did not resolve to an implementation with a MIR body':'The called function did not resolve to an implementation with a MIR body',
'Inline assembly code cannot be analyzed by MIRAI':'Inline assembly code cannot be analyzed by MIRAI',
}

class MiraiReportParser():
    def __init__(self, report):
        self.report = report

    def parse_tool(self):
        return "Mirai"
    
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
            if line.startswith('warning: [MIRAI]'):
                error_info = line[len('warning: [MIRAI]'):].strip()
                if any(k in error_info for k, _ in mirai_verify_errors.items()):
                    bug_type = next((v for k, v in mirai_verify_errors.items() if k in error_info), None)
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
                            start_column1=start_col, 
                            start_column2=-1, 
                            end_line=start_line, 
                            end_column1=-1, 
                            end_column2=-1,
                            description=error_info,
                            diagnosis=None
                        ))

        
        return result
    
    def gen_prompt(self):
        return


if __name__ == '__main__':
    pass


