import os
import hashlib

from shutil import rmtree

from utils.fileutils import GetAllFilePaths, GetDirectDirpaths

def calculate_file_hash(file_path, algorithm='sha256', buffer_size=65536):
    hash_func = hashlib.new(algorithm)
    try:
        with open(file_path, 'rb') as f:
            while True:
                data = f.read(buffer_size)
                if not data:
                    break
                hash_func.update(data)
        return hash_func.hexdigest()
    except FileNotFoundError:
        raise FileNotFoundError(f"File Not Found: {file_path}")
    except IsADirectoryError:
        raise IsADirectoryError(f"Dir not file: {file_path}")
    except Exception as e:
        raise Exception(f"Error: {str(e)}")

def seed_selection():
    dir_paths = GetDirectDirpaths(os.getcwd() + os.sep + "seeds" + os.sep + "spotbugs")
    rule_file = open(os.getcwd() + os.sep + "spotbugs-rule-names", "r")
    lines = rule_file.readlines()
    rule_names = set()
    for line in lines:
        rule_names.add(line.strip())
    succ = 0
    fail = 0
    for dir_path in dir_paths:
        rule_name = dir_path.split(os.sep)[-1]
        if rule_name in rule_names:
            succ = succ + 1
        else:
            fail = fail + 1
            rmtree(dir_path)
    print(succ)
    print(fail)

def main():
    file_paths = GetAllFilePaths(os.getcwd() + os.sep + "seeds" + os.sep + "spotbugs")
    hash2cnt = {}
    hash2list = {}
    for file_path in file_paths:
        hash_value = str(calculate_file_hash(file_path))[0:16]
        if hash_value not in hash2list.keys():
            hash2cnt[hash_value] = 0
            hash2list[hash_value] = []
        hash2cnt[hash_value] = hash2cnt[hash_value] + 1
        hash2list[hash_value].append(file_path)
    for k, v in hash2list.items():
        if len(v) > 1:
            print(k)
            retain_path = v[0]
            print(retain_path)
            for i in range(1, len(v)):
                os.remove(v[i])
            # for path in v:
            #     print(path)
    print("hash size: " + str(len(hash2list.keys())))

# seed_selection()
main()