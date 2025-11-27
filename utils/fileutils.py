import os, json
from typing import List

def GetJarListStr(dependency_dir_path: str) -> str:
    jar_paths = GetAllFilePaths(dependency_dir_path)
    jar_str = "."
    for jar_path in jar_paths:
        jar_str += ":"
        jar_str += jar_path
    return jar_str

def CountFiles(directory):
    file_count = 0
    for root, dirs, files in os.walk(directory):
        file_count += len(files)
    return file_count

def CleanCode(code) -> str:
    start_index = 0
    i = code.find("```java")
    if i != -1:
        start_index = i + len("```java")
    code = code[start_index:]
    i = code.rfind("```")
    if i != -1:
        code = code[:i]
    return code

def GetSeedPaths(target_path):
    seed_paths = []
    for file_path in GetAllFilePaths(target_path):
        if file_path.endswith(".rs"):
            seed_paths.append(file_path)
    return seed_paths

def ReadFileToStr(file_path: str):
    try:
        with open(file_path, 'r', encoding='utf-8') as file:
            content = file.read()
        return content
    except Exception as e:
        print(e)

def ReadFileToLines(file_path: str) -> list[str]:
    try:
        with open(file_path, 'r', encoding='utf-8') as file:
            tmp_lines = file.readlines()
        lines = []
        for tmp_line in tmp_lines:
            lines.append(tmp_line.strip())
        return lines
    except Exception as e:
        print(e)
    
def WriteStrToFile(file_path: str, content: str):
    try:
        with open(file_path, mode="w", encoding="utf-8") as file:
            file.write(content)
    except Exception as e:
        print(e)

def WriteListToFile(file_path, lines):
    try:
        with open(file_path, 'w') as file:
            for line in lines:
                file.write(line + '\n')
    except Exception as e:
        print(f"Fail to write string in lines: {e}")

def GetDirectDirpaths(target_path):
    dir_paths = []
    dir_names = os.listdir(target_path)
    for dir_name in dir_names:
        dir_path = os.path.join(target_path, dir_name)
        if os.path.isdir(dir_path):
            dir_paths.append(dir_path)
    return dir_paths

def GetDirectFilepaths(target_path) -> list[str]:
    file_paths = []
    file_names = os.listdir(target_path)
    for file_name in file_names:
        file_path = os.path.join(target_path, file_name)
        if os.path.isdir(file_path):
            file_paths.append(file_path)
    return file_paths

def GetAllFilePaths(target_path) -> list[str]:
    file_paths = []
    file_names = os.listdir(target_path)
    for file_name in file_names:
        file_path = os.path.join(target_path, file_name)
        if os.path.isdir(file_path):
            file_paths.extend(GetAllFilePaths(file_path))
        else:
            file_paths.append(file_path)
    return file_paths

def GetCargoProjectPaths(target_path) -> List[str]:
    cargo_paths = []
    rule_paths = GetDirectDirpaths(target_path)
    for rule_path in rule_paths:
        cargo_paths.extend(GetDirectDirpaths(rule_path))
    return cargo_paths

def ReadJsonToDict(file_path: str) -> dict:
    try:
        with open(file_path, 'r', encoding='utf-8') as file:
            data = json.load(file)
            return data
    except Exception as e:
        print("Failed Json Path: " + file_path)
        print(f"{e}")

def WriteDictToJson(file_path: str, data: dict, indent: int = 4) -> None:
    if not isinstance(data, dict):
        raise TypeError("Data should be a dict!")
    try:
        with open(file_path, 'w', encoding='utf-8') as file:
            json.dump(data, file, indent=indent, ensure_ascii=False)
    except Exception as e:
        print(f"Failed to write dict to json path: {file_path}")
        print(e)