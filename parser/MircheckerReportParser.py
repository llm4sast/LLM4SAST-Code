import os 
from parser.Report import Report, Violation

mirchecker_bug_types = {
    "overflow":"integer_overflow",
    "index out of bound":"out_of_bound",
    "with a divisor of zero":"divided_by_zero",
    "by zero":"divided_by_zero",
    # "panic":"panic",
    "double-free or use-after-free":"double_free_or_use_after_free"
}

class MircheckerReportParser():
    def __init__(self, report):
        self.report = report

    def parse_tool(self):
        return "Mirchecker"
    
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
            if line.startswith('warning: [MirChecker]'):
                error_info = line[len('warning: [MirChecker]'):].strip()
                
                for bug_type, bug_type_name in mirchecker_bug_types.items():
                    if bug_type in error_info:
                        if i < len(lines) - 1:
                            next_line = lines[i+1]
                            if next_line.strip().startswith('-->'):
                                next_line = next_line.strip().replace('-->', '').strip()
                                file_path, _, line_col_info = next_line.partition(':')
                                line_col_info = line_col_info.split(' (')[0]
                                start_line, start_col = map(int, line_col_info.replace(' ', '').split(':'))
                                result.appendViolation(Violation(
                                    vul_type=bug_type_name, 
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


