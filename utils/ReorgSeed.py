import os
import re
import toml
import shutil
import subprocess

from pathlib import Path
from utils.config import *

UTILS_PATH = os.path.dirname(os.path.abspath(__file__))

RUST_KEYWORDS = {
    "as", "async", "await", "break", "const", "continue", "crate", "dyn", "else",
    "enum", "extern", "false", "fn", "for", "if", "impl", "in", "let", "loop",
    "match", "mod", "move", "mut", "pub", "ref", "return", "self", "static",
    "struct", "super", "trait", "true", "type", "unsafe", "use", "where", "while",
}

def convert_singlefile_to_cargo_bin_project(project_path, rs_file_path):
    file_name = os.path.splitext(os.path.basename(rs_file_path))[0]

    if file_name in RUST_KEYWORDS:
        file_name = f"{file_name}_lib"
    project_path = os.path.join(os.path.dirname(rs_file_path), file_name)
    subprocess.run(['cargo', 'new', project_path, '--bin'], check=True)
    src_dir = os.path.join(project_path, 'src')
    shutil.move(rs_file_path, os.path.join(src_dir, 'main.rs'))

def convert_singlefile_to_cargo_lib_project(project_path, rs_file_path):
    file_name = os.path.splitext(os.path.basename(rs_file_path))[0]
    if file_name in RUST_KEYWORDS:
        file_name = f"{file_name}_lib"
    project_path = os.path.join(os.path.dirname(rs_file_path), file_name)
    subprocess.run(['cargo', 'new', project_path, '--lib'], check=True)
    src_dir = os.path.join(project_path, 'src')
    shutil.move(rs_file_path, os.path.join(src_dir, 'lib.rs'))

def add_dependency_to_cargo(project_path, dependency):
    subprocess.run(['cargo', 'add', dependency], cwd=project_path, check=True)

def add_local_dependency_to_cargo(project_path, dependency_path):
    subprocess.run(['cargo', 'add', '--path', dependency_path], cwd=project_path, check=True)

def IsCargoProject(directory):
    return os.path.isfile(os.path.join(directory, 'Cargo.toml'))

def check_cargo_project(project_path):
    subprocess.run(['cargo', 'check'], cwd=project_path, check=True)

def set_rust_version(version):
    subprocess.run(['rustup', 'default', version], check=True)

def reorg_rudra(testcases_path):
    set_rust_version(CONFIG["ToolConfig"]["RUDRA_RUSTC_VERSION"])
    rudra_testcases = os.path.join(testcases_path, "rudra")
    for root, _, files in os.walk(rudra_testcases):
        for file in files:
            if file.endswith('.rs'):
                rs_file_path = os.path.join(root, file)
                project_name = os.path.splitext(file)[0]
                project_path = os.path.join(root, project_name)


                convert_singlefile_to_cargo_lib_project(project_path, rs_file_path)
    for root, dirs, files in os.walk(rudra_testcases):
        if IsCargoProject(root):
            check_cargo_project(root)

def reorg_semgrep(testcase_path):
    semgrep_testcases = os.path.join(testcase_path, "semgrep")
    for root, dirs, files in os.walk(semgrep_testcases):
        for file in files:
            if file.endswith('.yml'):
                file_path = os.path.join(root, file)
                os.remove(file_path)

def reorg_mirchecker(testcase_path):
    set_rust_version(CONFIG["ToolConfig"]["MIRCHECKER_RUSTC_VERSION"])
    mirchecker_testcases = os.path.join(testcase_path, "mirchecker")
    shutil.move(os.path.join(testcase_path, "macros"), os.path.join(mirchecker_testcases, "macros"))
    for root, dirs, files in os.walk(mirchecker_testcases):
        for file in files:
            if file.endswith('.md') or file.endswith('.py'):
                file_path = os.path.join(root, file)
                os.remove(file_path)

            elif file=='Cargo.toml':
                file_path = os.path.join(root, file)
                with open(file_path, 'r') as f:
                    content = f.read()
                    if "path = \"../../../macros\"" in content:
                        content = content.replace('path = "../../../macros"', 'path = "../../macros"')
                        with open(file_path, 'w') as f:
                            f.write(content)

    for root, dirs, files in os.walk(mirchecker_testcases):
        if IsCargoProject(root):
            check_cargo_project(root)

def reorg_lockbud(testcase_path):
    set_rust_version(CONFIG["ToolConfig"]["LOCKBUD_RUSTC_VERSION"])
    lockbud_testcases = os.path.join(testcase_path, "lockbud")
    for root, dirs, files in os.walk(lockbud_testcases):
        if IsCargoProject(root):
            check_cargo_project(root)

def remove_test_attributes(content):
    return re.sub(r'#\[test\]\s*', '', content)

def reorg_mirai(testcase_path):
    set_rust_version(CONFIG["ToolConfig"]["MIRAI_RUSTC_VERSION"])
    mirai_testcases = os.path.join(testcase_path, "mirai")
    os.remove(os.path.join(mirai_testcases,"integration_tests.rs"))
    os.remove(os.path.join(mirai_testcases, "call_graph", "type_relations.json"))
    os.remove(os.path.join(mirai_testcases, "call_graph", "type_relations.rs_disabled"))
    for root, _, files in os.walk(mirai_testcases):
        for file in files:
            if file.endswith('.rs'):
                rs_file_path = os.path.join(root, file)
                project_name = os.path.splitext(file)[0]
                project_path = os.path.join(root, project_name)
                convert_singlefile_to_cargo_lib_project(project_path, rs_file_path)
                if project_name == "contract_annotations":
                    with open(os.path.join(project_path, "src", "lib.rs"), 'r') as lib_file:
                        lib_content = lib_file.read()
                    lib_content = remove_test_attributes(lib_content)
                    with open(os.path.join(project_path, "src", "lib.rs"), 'w') as lib_file:
                        lib_file.write(lib_content)
    for root, dirs, files in os.walk(mirai_testcases):
        if IsCargoProject(root):
            print(f"Checking Cargo project in: {root}")
            with open(os.path.join(root, "src", "lib.rs"), 'r') as lib_file:
                lib_content = lib_file.read()
                if "use mirai_annotations" or "extern crate mirai_annotations;" in lib_content:
                    add_dependency_to_cargo(root, 'mirai_annotations')
                if "use contracts" in lib_content:
                    add_dependency_to_cargo(root, 'contracts')
            check_cargo_project(root)

def reorg_prusti(testcase_path):
    set_rust_version(CONFIG["ToolConfig"]["PRUSTI_RUSTC_VERSION"])

    prusti_testcases = os.path.join(testcase_path, "prusti")
    os.remove(os.path.join(prusti_testcases, "Cargo.toml"))
    os.remove(os.path.join(prusti_testcases, "README.md"))
    shutil.rmtree(os.path.join(prusti_testcases, "tests_old"))
    os.remove(os.path.join(os.path.join(prusti_testcases, "tests"), "cargotest.rs"))
    os.remove(os.path.join(os.path.join(prusti_testcases, "tests"), "compiletest.rs"))

    for root_name in os.listdir(os.path.join(prusti_testcases, "tests")):
        root_path = os.path.join(os.path.join(prusti_testcases, "tests"), root_name)
        if not root_name.endswith("cargo_verify") and os.path.isdir(root_path):
            for subroot, dirs, files in os.walk(root_path):
                for file in files:
                    file_path = os.path.join(subroot, file)
                    if file.endswith("stderr") or file.endswith("stdout") or (file.endswith("md") and subroot != root_path):
                        os.remove(file_path)
                    if file.endswith(".rs") and subroot != root_path:
                        if file.startswith("\d"):
                            shutil.move(file_path, os.path.join(root_path, file))
                        else:
                            shutil.move(file_path, os.path.join(root_path, "n_" + file))
            for subroot, dirs, files in os.walk(root_path, topdown=False):
                for dir_name in dirs:
                    dir_path = os.path.join(subroot, dir_name)
                    if not os.listdir(dir_path):
                        os.rmdir(dir_path)
            for subroot, dirs, files in os.walk(root_path, topdown=False):
                for file in files:
                    if not IsCargoProject(subroot):
                        convert_singlefile_to_cargo_lib_project(subroot, os.path.join(subroot, file))

    for root, _, files in os.walk(os.path.join(prusti_testcases, "tests")):
        if IsCargoProject(root):
            print(f"Checking Cargo project in: {root}")
            toml_content = ""
            with open(os.path.join(root, "Cargo.toml"), 'r') as toml_file:
                toml_content = toml_file.read()
            data = toml.loads(toml_content)
            dependencies = data.get('dependencies', {})
            if dependencies:
                prusti_contracts = dependencies.get('prusti-contracts', {})
                prusti_contracts_path = Path(os.path.join(prusti_testcases, "prusti-contracts"))
                link_path = Path(os.path.join(root, prusti_contracts["path"]))
                if not link_path.exists():
                    link_path.parent.symlink_to(prusti_contracts_path)
            else:
                with open(os.path.join(root, "Cargo.toml"), 'r') as toml_file:
                    content = toml_file.read()
                dependency_str = f'prusti-contracts = {{ path = "prusti-contracts/prusti-contracts", version = "0.2.0" }}'
                dependencies_match = re.search(r"\[dependencies\]", content)
                new_content = ""
                if dependencies_match:
                    dependencies_end = dependencies_match.end()
                    new_content = content[:dependencies_end] + "\n" + dependency_str + content[dependencies_end:]
                else:
                    new_content = content + "\n[dependencies]\n" + dependency_str

                with open(os.path.join(root, "Cargo.toml"), "w") as f:
                    f.write(new_content)
                with open(os.path.join(root, "Cargo.toml"), 'r') as toml_file:
                    toml_content = toml_file.read()
                data = toml.loads(toml_content)
                dependencies = data.get('dependencies', {})
                prusti_contracts = dependencies.get('prusti-contracts', {})
                prusti_contracts_path = Path(os.path.join(prusti_testcases, "prusti-contracts"))
                link_path = Path(os.path.join(root, prusti_contracts["path"]))
                if not link_path.exists():
                    link_path.parent.symlink_to(prusti_contracts_path)

    # don't check prusti testcase because some of them will deliberately fail (have prusti macro only for cargo prusti or have faulty code) 
    #for root, _, files in os.walk(os.path.join(prusti_testcases, "tests")):
    #    if IsCargoProject(root):
    #        check_cargo_project(root)

if __name__ == "__main__":
    project_path = os.path.join(SEED_PATH, "rudra_testcases", "panic_safety", "insertion_sort")
    rs_file_path = os.path.join(SEED_PATH, "rudra_testcases", "panic_safety", "insertion_sort.rs")
    convert_singlefile_to_cargo_lib_project(project_path, rs_file_path)