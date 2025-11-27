from parser.Report import Report, Violation

class MiriReportParser():
    
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
            if line.startswith('error: Undefined Behavior: '):
                error_info = line[len('error: Undefined Behavior: '):].strip()
                if i < len(lines) - 1:
                    next_line = lines[i+1]
                    if next_line.strip().startswith('-->'):
                        next_line = next_line.strip().replace('-->', '').strip()
                        file_path, _, line_col_info = next_line.partition(':')                        
                        line_col_info = line_col_info.split(' (')[0]
                        start_line, start_col = map(int, line_col_info.replace(' ', '').split(':'))
                        result.appendViolation(Violation(
                            vul_type=error_info, 
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