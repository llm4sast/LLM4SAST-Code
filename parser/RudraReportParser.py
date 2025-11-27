import os, re
from parser.Report import Report, Violation

class RudraReportParser():
    def __init__(self, report):
        self.report = report

    def remove_escape_sequences(self, text):
        escape_pattern = re.compile(r'\x1b\[[0-9;]*[mK]')
        return escape_pattern.sub('', text)
    
    def parse_tool(self):
        return "Rudra"
    
    def parse_checker(self):
        analyzer = self.report.get('analyzer')
        if analyzer:
            analyzer_prefix = analyzer.split(':/')[0] if ':/' in analyzer else analyzer
            return analyzer_prefix
        return "Checker information not found."

    def parse_description(self):
        return
        base_description = self.report.get('description', "Description information not found.")
        source = self.report.get('source')
        if source:
            _, unsafe_output, generic_output = self._get_source_functions_info(source)
            full_description = f"{base_description}\n{unsafe_output}{generic_output}"
        else:
            full_description = base_description
        return full_description

    def parse_location(self):
        location = self.report.get('location')
        if location:
            try:
                file_path, _, line_col_info = location.partition(':')
                start_line, start_col, end_line, end_col = map(int, line_col_info.replace(' ', '').split(':'))
                return {
                    "file": file_path,
                    "start_line": start_line,
                    "start_column": start_col,
                    "end_line": end_line,
                    "end_column": end_col
                }
            except ValueError:
                return f"Error parsing location: {location}"
        return "Location information not found."

    def parse_code(self):
        return
        source = self.report.get('source')
        if source:
            clean_source = self.remove_escape_sequences(source)
            return clean_source
        return "Source information not found."

    def _get_source_functions_info(self, source):
        clean_source = self.remove_escape_sequences(source)

        pattern = r'\x1b\[(\d+(;\d+)*)m([^\x1b]+)\x1b\[0m'
        matches = re.finditer(pattern, source)

        unsafe_functions = []
        generic_functions = []
        location_info = self.parse_location()
        for match in matches:
            color_code = match.group(1)
            colored_string = match.group(3)
            start_index_in_original = match.start()
            end_index_in_original = match.end()

            offset_start = len(self.remove_escape_sequences(source[:start_index_in_original]))
            offset_end = len(self.remove_escape_sequences(source[:end_index_in_original])) - len(colored_string)

            start_lines = clean_source[:offset_start].split('\n')
            start_line_number = len(start_lines)
            start_column_number = len(start_lines[-1]) + 1

            end_lines = clean_source[:offset_end + len(colored_string)].split('\n')
            end_line_number = len(end_lines)
            end_column_number = len(end_lines[-1]) + 1

            if '31' in color_code or '33' in color_code:
                unsafe_functions.append({
                    "string": colored_string,
                    "start_line": start_line_number+location_info["start_line"]-1,
                    "start_column": start_column_number,
                    "end_line": end_line_number+location_info["start_line"]-1,
                    "end_column": end_column_number
                })
            elif '36' in color_code:
                generic_functions.append({
                    "string": colored_string,
                    "start_line": start_line_number+location_info["start_line"]-1,
                    "start_column": start_column_number,
                    "end_line": end_line_number+location_info["start_line"]-1,
                    "end_column": end_column_number
                })

        unsafe_output = ""
        if unsafe_functions:
            unsafe_output = "Unsafe Function:\n"
            for func in unsafe_functions:
                unsafe_output += f"  {func['string']}, " \
                                 f"Start Line: {func['start_line']}, Start Column: {func['start_column']}, " \
                                 f"End Line: {func['end_line']}, End Column: {func['end_column']}\n"
        else:
            unsafe_output = "No unsafe functions found in source.\n"

        generic_output = ""
        if generic_functions:
            generic_output = "Generic Function:\n"
            for func in generic_functions:
                generic_output += f"  {func['string']}, " \
                                  f"Start Line: {func['start_line']}, Start Column: {func['start_column']}, " \
                                  f"End Line: {func['end_line']}, End Column: {func['end_column']}\n"
        else:
            generic_output = "No generic functions found in source.\n"

        return unsafe_functions, generic_functions
    
    def parse_single_report(self, result:Report):
        checker_info = self.parse_checker()
        location_info = self.parse_location()

        # todo: 调用时即产生rudra report
        # tmp = result.report_path.replace("resource/report", "seeds")
        # print(tmp)


        if checker_info == "UnsafeDataflow":
            
            unsafe_functions, generic_functions = self._get_source_functions_info(self.report.get('source'))

            # todo: bug, all m can reach n?
            for m in unsafe_functions:
                for n in generic_functions:
                    result.appendViolation(Violation(
                        vul_type="unsafe_dataflow",
                        start_line= m["start_line"],
                        start_column1= m["start_column"],
                        start_column2= m["end_column"],
                        end_line= n["start_line"],
                        end_column1= m["start_column"],
                        end_column2= m["end_column"],
                        description=str(self.report.get('description')),
                        diagnosis=None
                    ))
        elif checker_info == "SendSyncVariance":
            result.appendViolation(Violation(
                vul_type="send_sync_variance",
                start_line= location_info["start_line"],
                start_column1= location_info["start_column"],
                start_column2= -1,
                end_line= location_info["end_line"],
                end_column1= location_info["end_column"],
                end_column2= -1,
                description=str(self.report.get('description')),
                diagnosis=None
                )
            )
        elif checker_info == "UnsafeDestructor":
            result.appendViolation(Violation(
                vul_type="unsafe_destructor",
                start_line= location_info["start_line"],
                start_column1= location_info["start_column"],
                start_column2= -1,
                end_line= location_info["end_line"],
                end_column1= location_info["end_column"],
                end_column2= -1,
                description=str(self.report.get('description')),
                diagnosis=None
                )
            )

        return result
    
    def gen_prompt(self):
        single_report_dict = self.parse_single_report()
        single_report_dict["Description"] = "Rudra report that " + single_report_dict["Description"] + "\n However, it is a false positive. \n"
        single_report_dict["Tool_Bug_Type"] = "False Positive"
        return single_report_dict


if __name__ == '__main__':
    pass

