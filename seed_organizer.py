import os
import shutil

from utils.fileutils import GetAllFilePaths, ReadFileToLines

tool_name = "infer"
oracle_name = "oracle1"

def main():
    seed_filter_path = os.getcwd() + os.sep + tool_name + "-" + oracle_name + "-seed-filter"
    seed_dir_path = os.getcwd() + os.sep + "seeds" + os.sep + tool_name
    tmp_seed_filter_paths = ReadFileToLines(seed_filter_path)
    seed_filter_paths = []
    for tmp_seed_filter_path in tmp_seed_filter_paths:
        seed_filter_path = os.getcwd() + tmp_seed_filter_path[tmp_seed_filter_path.index(os.sep + "seeds" + os.sep):]
        seed_filter_paths.append(seed_filter_path)
    seed_paths = GetAllFilePaths(seed_dir_path)
    set1 = set(seed_paths)
    set2 = set(seed_filter_paths)
    set3 = set1 - set2
    print("New seed size: " + str(len(set3)))
    new_seed_dir_path = os.getcwd() + os.sep+ "seeds" + os.sep + tool_name + "-new-" + oracle_name
    if not os.path.exists(new_seed_dir_path):
        os.mkdir(new_seed_dir_path)
    for seed_path in set3:
        index = seed_path.index(tool_name)
        new_seed_path = new_seed_dir_path + seed_path[index + len(tool_name):]
        new_seed_category_path = new_seed_path[:new_seed_path.rfind(os.sep)]
        if not os.path.exists(new_seed_category_path):
            # print(new_seed_category_path)
            os.mkdir(new_seed_category_path)
        shutil.copy(seed_path, new_seed_path)

main()