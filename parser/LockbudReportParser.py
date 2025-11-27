import re, json
from parser.Report import Report, Violation

class LockbudReportParser():    
    def __init__(self, report):
        self.report = report

    def parse_tool(self):
        return "Lockbud"
    
    def parse_checker(self):
        return

    def parse_description(self):
        return

    def parse_location(self):
        return

    def parse_code(self):
        return
    
    def extract_atomicity_violations(self):
        pattern = re.compile(r'"AtomicityViolation":\s*{(.*?)}\s*}', re.DOTALL)
        matches = pattern.findall(self.report)
        result = list()
        for match in matches:
            json_data = '{' + match + '}'
            try:
                violation_info = json.loads(json_data)

                # print("AtomicityViolation Info:")
                # print(f"Bug Kind: {violation_info['bug_kind']}")
                # print(f"Possibility: {violation_info['possibility']}")
                # print(f"Function Name: {violation_info['diagnosis']['fn_name']}")
                # print(f"Atomic Reader: {violation_info['diagnosis']['atomic_reader']}")
                # print(f"Atomic Writer: {violation_info['diagnosis']['atomic_writer']}")
                # print(f"Dependency Kind: {violation_info['diagnosis']['dep_kind']}")
                # print(f"Explanation: {violation_info['explanation']}")
                # print("\n")
                atomic_reader = violation_info['diagnosis']['atomic_reader']
                file_path, _, line_col_info = atomic_reader.partition(':')
                
                if  ".cargo/registry/src/" in file_path:
                    continue

                line_col_info = line_col_info.split(' (')[0]
                atomic_reader_start_line, atomic_reader_start_col, atomic_reader_end_line, atomic_reader_end_col = map(int, line_col_info.replace(' ', '').split(':'))
                atomic_writer = violation_info['diagnosis']['atomic_writer']
                file_path, _, line_col_info = atomic_writer.partition(':')
                line_col_info = line_col_info.split(' (')[0]
                atomic_writer_start_line, atomic_writer_start_col, atomic_writer_end_line, atomic_writer_end_col = map(int, line_col_info.replace(' ', '').split(':'))
                result.append(Violation(
                    vul_type= "atomicity_violation",
                    start_line=atomic_reader_start_line,
                    end_line=atomic_writer_start_line,
                    # start_column1=atomic_reader_start_col,
                    # start_column2=atomic_reader_end_col,
                    # end_column1=atomic_writer_start_col,
                    # end_column2=atomic_writer_end_col,
                    # description=str(violation_info['explanation']),
                    # diagnosis=str(violation_info['diagnosis'])
                ))
            except json.JSONDecodeError as e:
                print("AtomicityViolation Info:")
                print(f"Error decoding JSON: {e}")
            except Exception as e:
                print(e)
        return result
        
    def extract_use_after_free(self):
        pattern = re.compile(r'"UseAfterFree":\s*{(.*?)}\s*}', re.DOTALL)
        matches = pattern.findall(self.report)
        result = list()
        for match in matches:
            
            json_data = '{' + match + '}'
            try:
                violation_info = json.loads(json_data)
                # print("UseAfterFree Info:")
                # print(f"Bug Kind: {violation_info['bug_kind']}")
                # print(f"Possibility: {violation_info['possibility']}")
                # print(f"Diagnosis: {violation_info['diagnosis']}")
                # print(f"Explanation: {violation_info['explanation']}")
                # print("\n")
                pattern1 = r'Raw ptr is used at (?P<file>[^:]+):(?P<used_start_line>\d+):(?P<used_start_col>\d+):\s*(?P<used_end_line>\d+):(?P<used_end_col>\d+)[^:]+after dropped at (?P<file_drop>[^:]+):(?P<drop_start_line>\d+):(?P<drop_start_col>\d+):\s*(?P<drop_end_line>\d+):(?P<drop_end_col>\d+)'
                pattern2 = r'Escape to Global: Raw ptr [^:]+? at (?P<file>[^:]+):(?P<used_start_line>\d+):(?P<used_start_col>\d+):\s*(?P<used_end_line>\d+):(?P<used_end_col>\d+).*?escapes to.*?but pointee is dropped at\s+(?P<file_drop>[^:]+):(?P<drop_start_line>\d+):(?P<drop_start_col>\d+):\s*(?P<drop_end_line>\d+):(?P<drop_end_col>\d+)'
                pattern3 = r'Escape to Param/Return: Raw ptr [^:]+? at (?P<file>[^:]+):(?P<used_start_line>\d+):(?P<used_start_col>\d+):\s*(?P<used_end_line>\d+):(?P<used_end_col>\d+).*?escapes to.*?but pointee is dropped at\s+(?P<file_drop>[^:]+):(?P<drop_start_line>\d+):(?P<drop_start_col>\d+):\s*(?P<drop_end_line>\d+):(?P<drop_end_col>\d+)'

                diagnosis_match = re.search(pattern1, violation_info['diagnosis'])
                if not diagnosis_match:
                    diagnosis_match = re.search(pattern2, violation_info['diagnosis'])
                if not diagnosis_match:
                    diagnosis_match = re.search(pattern3, violation_info['diagnosis'])
                
                if diagnosis_match:
                    file_name = diagnosis_match.group('file')
                    if  ".cargo/registry/src/" in file_name:
                        continue
                    used_start_line = diagnosis_match.group('used_start_line')
                    used_start_col = diagnosis_match.group('used_start_col')
                    used_end_line = diagnosis_match.group('used_end_line')
                    used_end_col = diagnosis_match.group('used_end_col')
                    
                    drop_file_name = diagnosis_match.group('file_drop')
                    drop_start_line = diagnosis_match.group('drop_start_line')
                    drop_start_col = diagnosis_match.group('drop_start_col')
                    drop_end_line = diagnosis_match.group('drop_end_line')
                    drop_end_col = diagnosis_match.group('drop_end_col')
                    
                    result.append(Violation(
                        vul_type= "use_after_free",
                        start_line=drop_start_line,
                        end_line=used_start_line,
                        # start_column1=drop_start_col,
                        # start_column2=drop_end_col,
                        # end_column1=used_start_col,
                        # end_column2=used_end_col,
                        # description=str(violation_info['explanation']),
                        # diagnosis=str(violation_info['diagnosis'])
                    ))

            except json.JSONDecodeError as e:
                print("UseAfterFree Info:")
                print(f"Error decoding JSON: {e}")
            except Exception as e:
                print(e)
        return result

    
    def extract_double_lock(self):
        pattern = re.compile(r'"DoubleLock":\s*{(.*?)}\s*}', re.DOTALL)
        matches = pattern.findall(self.report)
        result = list()
        for match in matches:
            json_data = '{' + match + '}'
            
            try:
                violation_info = json.loads(json_data)
                # print("DoubleLock Info:")
                # print(f"Bug Kind: {violation_info['bug_kind']}")
                # print(f"Possibility: {violation_info['possibility']}")
                # print(f"First_lock_type: {violation_info['diagnosis']['first_lock_type']}")
                # print(f"First_lock_span: {violation_info['diagnosis']['first_lock_span']}")
                # print(f"Second_lock_type: {violation_info['diagnosis']['second_lock_type']}")
                # print(f"Second_lock_span: {violation_info['diagnosis']['second_lock_span']}")
                # print(f"Callchains: {violation_info['diagnosis']['callchains']}")
                # print(f"Explanation: {violation_info['explanation']}")
                # print("\n")
                
                file_path, _, line_col_info = violation_info['diagnosis']['first_lock_span'].partition(':')
                
                if  ".cargo/registry/src/" in file_path:
                    continue
                line_col_info = line_col_info.split(' (')[0]
                lock1_start_line, lock1_start_col, lock1_end_line, lock1_end_col = map(int, line_col_info.replace(' ', '').split(':'))

                file_path, _, line_col_info = violation_info['diagnosis']['second_lock_span'].partition(':')

                line_col_info = line_col_info.split(' (')[0]
                lock2_start_line, lock2_start_col, lock2_end_line, lock2_end_col = map(int, line_col_info.replace(' ', '').split(':'))

                result.append(Violation(
                    vul_type= "double_lock",
                    start_line=lock1_start_line,
                    end_line=lock2_start_line,
                    # start_column1=lock1_start_col,
                    # start_column2=lock1_end_col,
                    # end_column1=lock2_start_col,
                    # end_column2=lock2_end_col,
                    # description=str(violation_info['explanation']),
                    # diagnosis=str(violation_info['diagnosis'])
                ))
                
            except json.JSONDecodeError as e:
                print("DoubleLock Info:")
                print(f"Error decoding JSON: {e}")
            except Exception as e:
                print(e)
        return result

    def extract_conflict_lock(self):
        pattern = re.compile(r'"ConflictLock":\s*{(.*?)}\s*}', re.DOTALL)
        matches = pattern.findall(self.report)
        result = list()
        for match in matches:
            json_data = '{' + match + '}'
            try:
                violation_info = json.loads(json_data)
                # print("ConflictLock Info:")
                # print(f"Bug Kind: {violation_info['bug_kind']}")
                # print(f"Possibility: {violation_info['possibility']}")
                for diagnosis in violation_info['diagnosis']:
                    # print(f"First_lock_type: {diagnosis['first_lock_type']}")
                    # print(f"First_lock_span: {diagnosis['first_lock_span']}")
                    # print(f"Second_lock_type: {diagnosis['second_lock_type']}")
                    # print(f"Second_lock_span: {diagnosis['second_lock_span']}")
                    # print(f"Callchains: {diagnosis['callchains']}")
                    file_path, _, line_col_info = diagnosis['first_lock_span'].partition(':')
                    if  ".cargo/registry/src/" in file_path:
                        continue
                    line_col_info = line_col_info.split(' (')[0]
                    lock1_start_line, lock1_start_col, lock1_end_line, lock1_end_col = map(int, line_col_info.replace(' ', '').split(':'))

                    file_path, _, line_col_info = diagnosis['second_lock_span'].partition(':')

                    line_col_info = line_col_info.split(' (')[0]
                    lock2_start_line, lock2_start_col, lock2_end_line, lock2_end_col = map(int, line_col_info.replace(' ', '').split(':'))

                    result.append(Violation(
                        vul_type= "conflict_lock",
                        start_line=lock1_start_line,
                        end_line=lock2_start_line,
                        # start_column1=lock1_start_col,
                        # start_column2=lock1_end_col,
                        # end_column1=lock2_start_col,
                        # end_column2=lock2_end_col,
                        # description=str(violation_info['explanation']),
                        # diagnosis=str(violation_info['diagnosis'])
                    ))

                # print(f"Explanation: {violation_info['explanation']}")
                # print("\n")
            except json.JSONDecodeError as e:
                print("ConflictLock Info:")
                print(f"Error decoding JSON: {e}")
            except Exception as e:
                print(e)

        return result
    
    def extract_condvar_deadlock(self):
        pattern = re.compile(r'"CondvarDeadlock":\s*{(.*?)}\s*}', re.DOTALL)
        matches = pattern.findall(self.report)
        result = list()
        for match in matches:
            json_data = '{' + match + '}'
            try:
                violation_info = json.loads(json_data)
                # print("CondvarDeadlock Info:")
                # print(f"Bug Kind: {violation_info['bug_kind']}")
                # print(f"Possibility: {violation_info['possibility']}")
                # print(f"Condvar_wait_type: {violation_info['diagnosis']['condvar_wait_type']}")
                # print(f"Condvar_wait_callsite_span: {violation_info['diagnosis']['condvar_wait_callsite_span']}")
                # print(f"Condvar_notify_type: {violation_info['diagnosis']['condvar_notify_type']}")
                # print(f"Condvar_notify_callsite_span: {violation_info['diagnosis']['condvar_notify_callsite_span']}")
                # print(f"Deadlocks: {violation_info['diagnosis']['deadlocks']}")
                # print(f"Explanation: {violation_info['explanation']}")
                # print("\n")
                file_path, _, line_col_info = violation_info['diagnosis']['condvar_wait_callsite_span'].partition(':')
                if  ".cargo/registry/src/" in file_path:
                    continue
                line_col_info = line_col_info.split(' (')[0]
                wait_start_line, wait_start_col, wait_end_line, wait_end_col = map(int, line_col_info.replace(' ', '').split(':'))

                file_path, _, line_col_info = violation_info['diagnosis']['condvar_notify_callsite_span'].partition(':')

                line_col_info = line_col_info.split(' (')[0]
                notify_start_line, notify_start_col, notify_end_line, notify_end_col = map(int, line_col_info.replace(' ', '').split(':'))

                result.append(Violation(
                    vul_type= "condvar_deadlock",
                    start_line=wait_start_line,
                    end_line=notify_start_line,
                    # start_column1=wait_start_col,
                    # start_column2=wait_end_col,
                    # end_column1=notify_start_col,
                    # end_column2=notify_end_col,
                    # description=str(violation_info['explanation']),
                    # diagnosis=str(violation_info['diagnosis'])
                ))

            except json.JSONDecodeError as e:
                print("CondvarDeadlock Info:")
                print(f"Error decoding JSON: {e}")
            except Exception as e:
                print(e)        
        return result
    
    def extract_invalid_free(self):
        pattern = re.compile(r'"InvalidFree":\s*{(.*?)}\s*}', re.DOTALL)
        matches = pattern.findall(self.report)
        result = list()
        for match in matches:
            json_data = '{' + match + '}'
            try:
                violation_info = json.loads(json_data)
                # print("InvalidFree Info:")
                # print(f"Bug Kind: {violation_info['bug_kind']}")
                # print(f"Possibility: {violation_info['possibility']}")
                # print(f"Diagnosis: {violation_info['diagnosis']}")
                # print(f"Explanation: {violation_info['explanation']}")
                # print("\n")
                path_pattern = re.compile(r'(?<=at\s)(?P<filename>[^:]+):(?P<start_line>\d+):(?P<start_column>\d+)\s*:\s*(?P<end_line>\d+):(?P<end_column>\d+)')

                # path_pattern = re.compile(r'(/[^:]+):\d+:\d+')
                match = path_pattern.search(violation_info['diagnosis'])
                if match:
                    if  ".cargo/registry/src/" in match.group('filename'):
                        continue
                    result.append(Violation(
                        vul_type= "invalid_free",
                        start_line=match.group('start_line'),
                        end_line=match.group('end_line'),
                        # start_column1=match.group('start_column'),
                        # start_column2=match.group('end_column'),
                        # end_column1=match.group('start_column'),
                        # end_column2=match.group('end_column'),
                        # description=str(violation_info['explanation']),
                        # diagnosis=str(violation_info['diagnosis'])
                    ))

            except json.JSONDecodeError as e:
                print("InvalidFree Info:")
                print(f"Error decoding JSON: {e}")
            except Exception as e:
                print(e)
        return result

    def parse_single_report(self, result:Report) -> Report:
        atomicity_violations = self.extract_atomicity_violations()
        use_after_free = self.extract_use_after_free()
        double_lock = self.extract_double_lock()
        conflict_lock = self.extract_conflict_lock()
        condvar_deadlock = self.extract_condvar_deadlock()
        invalid_free = self.extract_invalid_free()

        result.updateViolation(atomicity_violations)
        result.updateViolation(use_after_free)
        result.updateViolation(double_lock)
        result.updateViolation(conflict_lock)
        result.updateViolation(condvar_deadlock)
        result.updateViolation(invalid_free)
        return result
    
    def gen_prompt(self):
        return