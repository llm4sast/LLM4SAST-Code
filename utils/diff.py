from typing import List

from utils.config import *
from parser.Report import Report

def DiffAnalysis(seed_report:Report, mutant_report:Report) -> List[List]:
    # print("Seed Report")
    # print(seed_report)
    # print("Mutant Report")
    # print(mutant_report)
    bug_instances = []
    all_src_warning_len = len(seed_report.getViolations())
    all_dst_warning_len = len(mutant_report.getViolations())
    src_bug2cnt = dict()
    dst_bug2cnt = dict()
    for violation in seed_report.getViolations():
        bug_type = violation.getViolationType()
        if bug_type not in src_bug2cnt.keys():
            src_bug2cnt[bug_type] = 0
        src_bug2cnt[bug_type] += 1
    for violation in mutant_report.getViolations():
        bug_type = violation.getViolationType()
        if bug_type not in dst_bug2cnt.keys():
            dst_bug2cnt[bug_type] = 0
        dst_bug2cnt[bug_type] += 1
    unique_src_keys = set()
    unique_dst_keys = set()
    both_keys = set()
    if ORACLE1:
        if all_src_warning_len != all_dst_warning_len:
            for src_key in src_bug2cnt.keys():
                if src_key not in dst_bug2cnt.keys():
                    unique_src_keys.add(src_key)
                else:
                    both_keys.add(src_key)
            for dst_key in dst_bug2cnt.keys():
                if dst_key not in src_bug2cnt.keys():
                    unique_dst_keys.add(dst_key)
                else:
                    both_keys.add(dst_key)
            for unique_src_key in unique_src_keys:
                bug_instance = []
                bug_instance.append(unique_src_key)
                bug_instance.append("FN")
                bug_instance.append(seed_report.getReportPath())
                bug_instance.append(seed_report.getCodePath())
                bug_instance.append(mutant_report.getReportPath())
                bug_instance.append(mutant_report.getCodePath())
                bug_instances.append(bug_instance)
            for unique_dst_key in unique_dst_keys:
                bug_instance = []
                bug_instance.append(unique_dst_key)
                bug_instance.append("FP")
                bug_instance.append(seed_report.getReportPath())
                bug_instance.append(seed_report.getCodePath())
                bug_instance.append(mutant_report.getReportPath())
                bug_instance.append(mutant_report.getCodePath())
                bug_instances.append(bug_instance)
            for key in both_keys:
                src_warning_len = src_bug2cnt[key]
                dst_warning_len = dst_bug2cnt[key]
                if src_warning_len != dst_warning_len:
                    bug_instance = []
                    bug_instance.append(key)
                    if src_warning_len < dst_warning_len:
                        bug_instance.append("FP")
                    else:
                        bug_instance.append("FN")
                    bug_instance.append(seed_report.getReportPath())
                    bug_instance.append(seed_report.getCodePath())
                    bug_instance.append(mutant_report.getReportPath())
                    bug_instance.append(mutant_report.getCodePath())
                    bug_instances.append(bug_instance)
    if ORACLE2:
        # seed修复后的mutant警告数量应该比seed少，所以如果多了则意味着出现bug
        if all_src_warning_len < all_dst_warning_len:
            for src_key in src_bug2cnt.keys():
                if src_key in dst_bug2cnt.keys():
                    both_keys.add(src_key)
            for dst_key in dst_bug2cnt.keys(): 
                if dst_key not in src_bug2cnt.keys():
                    unique_dst_keys.add(dst_key)
                else:
                    both_keys.add(dst_key)
            # for unique_src_key in unique_src_keys:
            #     bug_instance = []
            #     bug_instance.append(unique_src_key)
            #     bug_instance.append("FP")
            #     bug_instance.append(seed_report.getReportPath())
            #     bug_instance.append(seed_report.getCodePath())
            #     bug_instance.append(mutant_report.getReportPath())
            #     bug_instance.append(mutant_report.getCodePath())
            #     bug_instances.append(bug_instance)
            for unique_dst_key in unique_dst_keys:
                bug_instance = []
                bug_instance.append(unique_dst_key)
                bug_instance.append("oracle2_bug (type-I)")
                bug_instance.append(seed_report.getReportPath())
                bug_instance.append(seed_report.getCodePath())
                bug_instance.append(mutant_report.getReportPath())
                bug_instance.append(mutant_report.getCodePath())
                bug_instances.append(bug_instance)
            for key in both_keys:
                src_warning_len = src_bug2cnt[key]
                dst_warning_len = dst_bug2cnt[key]
                if src_warning_len < dst_warning_len:
                    bug_instance = []
                    bug_instance.append(key)
                    bug_instance.append("oracle2_bug (type-II)")
                    bug_instance.append(seed_report.getReportPath())
                    bug_instance.append(seed_report.getCodePath())
                    bug_instance.append(mutant_report.getReportPath())
                    bug_instance.append(mutant_report.getCodePath())
                    bug_instances.append(bug_instance)
    if ORACLE3:
        # mutant警告数量应该比seed多，所以如果少了则意味着出现bug
        if all_src_warning_len > all_dst_warning_len:
            for src_key in src_bug2cnt.keys():
                if src_key not in dst_bug2cnt.keys():
                    unique_src_keys.add(src_key)
                else:
                    both_keys.add(src_key)
            for dst_key in dst_bug2cnt.keys(): 
                if dst_key in src_bug2cnt.keys():
                    both_keys.add(dst_key)
            for unique_src_key in unique_src_keys:
                bug_instance = []
                bug_instance.append(unique_src_key)
                bug_instance.append("oracle3_bug (type-I)")
                bug_instance.append(seed_report.getReportPath())
                bug_instance.append(seed_report.getCodePath())
                bug_instance.append(mutant_report.getReportPath())
                bug_instance.append(mutant_report.getCodePath())
                bug_instances.append(bug_instance)
            for key in both_keys:
                src_warning_len = src_bug2cnt[key]
                dst_warning_len = dst_bug2cnt[key]
                if src_warning_len > dst_warning_len:
                    bug_instance = []
                    bug_instance.append(key)
                    bug_instance.append("oracle3_bug (type-II)")
                    bug_instance.append(seed_report.getReportPath())
                    bug_instance.append(seed_report.getCodePath())
                    bug_instance.append(mutant_report.getReportPath())
                    bug_instance.append(mutant_report.getCodePath())
                    bug_instances.append(bug_instance)
    return bug_instances