import os
import json
import toml
import shutil
import tempfile
import subprocess

from typing import List, Tuple
from utils.config import *
from utils.ReorgSeed import IsCargoProject
from utils.fileutils import ReadFileToStr, WriteStrToFile

succ_invocation = 0
fail_invocation = 0
succ_compilation = 0
fail_compilation = 0
failed_commands = []
timeout_commands = []


from utils.config import GUMTREE_PATH
from utils.invoker import InvokeCommandsWithOutput

def InvokeProgramDifferencing(file_path1, file_path2) -> str:
    cmds = [
        GUMTREE_PATH,
        "axmldiff",
        file_path1,
        file_path2
    ]
    return InvokeCommandsWithOutput(cmds)

def GetBuggyCode(tool_name, violation_type):
    file_path = os.path.join(PATCH_PATH, tool_name, violation_type + "_bug.rs")
    return ReadFileToStr(file_path)

def GetFixedCode(tool_name, violation_type):
    file_path = os.path.join(PATCH_PATH, tool_name, violation_type + "_patch.rs")
    return ReadFileToStr(file_path)

def GetBugDocument(tool_name, violation_type) -> str:
    doc_path = os.path.join(CHECKER_DOC_PATH, tool_name, violation_type)
    if os.path.exists(doc_path):
        return ReadFileToStr(doc_path)
    else:
        return None

def CompileJavaSourceProgram(program_path: str, output_dir_path) -> bool:
    if not os.path.exists(program_path):
        return False
    if not os.path.exists(output_dir_path):
        os.mkdir(output_dir_path)
    global succ_compilation
    global fail_compilation
    dependency_path = ""
    if SPOTBUGS:
        dependency_path = GetJarListStr(SPOTBUGS_DEPENDENCY_DIR_JAR)
        cmds = [
            JAVAC_PATH,
            "-cp",
            dependency_path,
            "-d",
            output_dir_path,
            program_path
        ]
    else:
        cmds = [
            JAVAC_PATH,
            "-d",
            output_dir_path,
            program_path
        ]
    try:
        subprocess.run(
            cmds,
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            check=True,
            timeout=TIMEOUT
        )
        succ_compilation = succ_compilation + 1
        return True
    except subprocess.CalledProcessError:
        fail_compilation = fail_compilation + 1
        return False
    except subprocess.TimeoutExpired:
        fail_compilation = fail_compilation + 1
        timeout_commands.append(" ".join(cmds))
        return False

def InvokeCommands(cmds: List[str]) -> bool:
    try:
        subprocess.run(
            cmds,
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            check=True,
            timeout=TIMEOUT
        )
        return True
    except subprocess.CalledProcessError:
        failed_commands.append(" ".join(cmds))
        return False
    except subprocess.TimeoutExpired:
        timeout_commands.append(" ".join(cmds))
        return False

def InvokeCommandsWithContext(cmds: List[str], cwd_path: str) -> bool:
    try:
        subprocess.run(
            cmds,
            cwd=cwd_path,
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            check=True,
            timeout=TIMEOUT
        )
        return True
    except subprocess.CalledProcessError:
        print("Fail to invoke commands: " + " ".join(cmds))
        return False
    except subprocess.TimeoutExpired:
        print("Invocation timeout!")
        return False

def InvokeCommandsWithOutput(cmds: List[str]) -> str:
    try:
        result = subprocess.run(
            cmds,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            check=True,
            timeout=TIMEOUT,
            text=True
        )
        return result.stdout
    except subprocess.CalledProcessError as e:
        error_msg = f"Fail to invoke commands: {' '.join(cmds)}\nError output:\n{e.stdout}"
        return error_msg
    except subprocess.TimeoutExpired as e:
        error_msg = f"Cmd invocation timeout!\nPartial output:\n{e.stdout.decode('utf-8') if e.stdout else ''}"
        return error_msg
    except Exception as e:
        return f"Unexpected error: {str(e)}"

def InvokeFormatter(input_filepath: str) -> bool:
    if not os.path.exists(input_filepath):
        return False
    cmds = [
        "java",
        "-jar",
        GOOGLE_FORMAT_PATH,
        "--replace",
        input_filepath
    ]
    return InvokeCommands(cmds)
    
def RunBin(bin_path: str)->Tuple[str, str]:
    try:
        result = subprocess.run(
            [bin_path],
            check=True,
            capture_output=True,
            text=True
        )
        stdout_content = result.stdout
        stderr_content = result.stderr
    except subprocess.CalledProcessError as e:
        stderr_content = e.stderr
    return stdout_content, stderr_content

def RunMiri(input_program_path: str, report_path: str, dependency_path: str, crates_lib_path:dict=dict()) -> bool:
    if not os.path.exists(input_program_path):
        print(f"The input program {input_program_path} does not exist!")
        return False
    if not input_program_path.endswith('.rs'):
        print(f"The input program {input_program_path} is not a rust file!")
        return False
    if not os.path.exists(os.path.dirname(report_path)):
        os.makedirs(os.path.dirname(report_path))
    env_dict = dict(os.environ)
    env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["MIRI_RUSTC_VERSION"]
    env_dict["PATH"] = CONFIG["ToolConfig"]["MIRI_PATH"]+":"+env_dict["PATH"]
    cmd = [
        "miri",
        "--sysroot",
        CONFIG["ToolConfig"]["MIRI_SYSROOT"],
        input_program_path
    ]   
    if not dependency_path=="":
        cmd.append("-L")
        cmd.append(dependency_path)
    for crate_name, crate_lib_path in crates_lib_path.items():
        cmd.append("--extern")
        cmd.append(f"{crate_name}={crate_lib_path}")
    if input_program_path.endswith("lib.rs"):
        cmd.append("--crate-type=lib")
    cmd.append("--edition=2021")
    with open(report_path, "w") as output_file:
        try:
            subprocess.run(
                cmd,
                cwd=os.path.dirname(input_program_path),
                env=env_dict,
                stdout=output_file,
                stderr=subprocess.STDOUT,
                timeout=60
            )
        except subprocess.CalledProcessError as e:
            cmd_str = " ".join(cmd)
            print(f"Error Command: {cmd_str}")
            print(e)
            return False
        except subprocess.TimeoutExpired:
            cmd_str = " ".join(cmd)
            print(f"Timeout: {cmd_str}")
            return False
    return True

def RunSeed(seed_path: str)->Tuple[int, str]:
    if not IsCargoProject(seed_path):
        print(f"Error: The seed {seed_path} is not a cargo project!")
        return None
    
    env_dict = dict(os.environ)
    if LOCKBUD:
        env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["LOCKBUD_RUSTC_VERSION"]
    elif MIRAI:
        env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["MIRAI_RUSTC_VERSION"]
    elif MIRCHECKER:
        env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["MIRCHECKER_RUSTC_VERSION"]
    elif PRUSTI:
        env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["PRUSTI_RUSTC_VERSION"]
    elif RUDRA:
        env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["RUDRA_RUSTC_VERSION"]

    env_dict["RUSTFLAGS"] = "-C inline-threshold=0"

    subprocess.run(["cargo", "clean"], cwd=seed_path, check=True, env=env_dict)
    stdout_content, stderr_content = None, None
    try:
        result = subprocess.run(
            ["cargo", "build"],
            cwd=seed_path,
            check=True,
            env=env_dict,
            capture_output=True,
            text=True
        )
        stdout_content = result.stdout
        stderr_content = result.stderr
    except subprocess.CalledProcessError as e:
        stderr_content = e.stderr
        return stdout_content, stderr_content

    bin_path = os.path.join(seed_path, "target", "debug", os.path.basename(seed_path))
    return_code = 0
    if os.path.exists(bin_path):
        try:
            subprocess.run(
                [bin_path],
                check=True,
                capture_output=True,
                text=True,
                timeout=30
            )
        except subprocess.CalledProcessError as e:
            if e.returncode == -11:
                return_code = -11
                print(f"Segmentation fault: {bin_path}")
            else:
                stderr_content = e.stderr
        except subprocess.TimeoutExpired:
            return_code = -12
            print(f"Timeout: {bin_path}")
    else:
        print(f"Error: Fail to build {seed_path}.")
    return return_code, bin_path

def RunMutant(mutant_path: str, dependency_path: str, crates_lib_path:dict=dict())->Tuple[str, str]:
    if not os.path.exists(mutant_path):
        print(f"The mutant {mutant_path} does not exist!")
        return
    if not mutant_path.endswith('.rs'):
        print(f"The input program {mutant_path} is not a rust file!")
        return
    env_dict = dict(os.environ)
    env_dict["RUSTFLAGS"] = "-C inline-threshold=0"

    cmd = [
        "rustc",
        mutant_path
    ]   

    cmd.append("-L")
    cmd.append(dependency_path)
    
    for crate_name, crate_lib_path in crates_lib_path.items():
        cmd.append("--extern")
        cmd.append(f"{crate_name}={crate_lib_path}")
    if mutant_path.endswith("lib.rs"):
        cmd.append("--crate-type=lib")
    cmd.append("--edition=2021")
    stdout_content, stderr_content = None, None
    try:
        result = subprocess.run(
            cmd,
            cwd=os.path.dirname(mutant_path), 
            check=True,
            env=env_dict,
            capture_output=True,
            text=True
        )
        stdout_content = result.stdout
        stderr_content = result.stderr
    except subprocess.CalledProcessError as e:
        stderr_content = e.stderr
        return stdout_content, stderr_content
    
    bin_path = mutant_path.replace(".rs", "")
    returncode = 0
    if os.path.exists(bin_path):
        try:
            result = subprocess.run(
            [bin_path],
            cwd=os.path.dirname(mutant_path), 
            check=True,
            env=env_dict,
            capture_output=True,
            text=True,
            timeout=15
            )
            stdout_content = result.stdout
            stderr_content = result.stderr
        except subprocess.CalledProcessError as e:
            if e.returncode == -11:
                returncode = -11
                stderr_content = "Segmentation fault(integer overflow or use after free or others)"
            elif e.returncode == -6:
                returncode = -6
                stderr_content = "Abort(double free or misaligned pointer dereference)"
        except subprocess.TimeoutExpired:
            returncode = -12
            stderr_content = "Timeout"

    lib_path = os.path.join(os.path.dirname(mutant_path), "liblib.rlib")
    if os.path.exists(lib_path):
        os.remove(lib_path)
    return returncode, bin_path

def CompileCheck(code_path: str) -> bool:
    try:
        result = subprocess.run(["rustc", code_path], check=True, capture_output=True, text=True)
        if result.returncode == 0:
            return True
        else:
            return False
    except Exception as e:
        if DEBUG:
            print(e)
        return False

def InvokeCargoMiri(input_program_path: str, env_dict:dict):
    print("Cargo Miri: " + input_program_path)
    env_dict["RUSTFLAGS"] = "-C llvm-args=--inline-threshold=0"
    env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["MIRI_RUSTC_VERSION"]
    try:
        res = subprocess.run(
            ["cargo", "miri", "run"], 
            cwd=input_program_path, 
            env=env_dict, 
            stdout=subprocess.DEVNULL, 
            stderr=subprocess.STDOUT,
            timeout=60)
    except subprocess.CalledProcessError as e:
        print(e)
    except subprocess.TimeoutExpired:
        print(f"Timeout")

def InvokeCargoBuild(input_program_path: str, env_dict:dict):
    print("Cargo: " + input_program_path)
    subprocess.run(["cargo", "clean"], cwd=input_program_path, check=True, env=env_dict, stdout=subprocess.DEVNULL, stderr=subprocess.STDOUT)
    env_dict["RUSTFLAGS"] = "-C inline-threshold=0"
    res = subprocess.run(["cargo", "build"], cwd=input_program_path, check=True, env=env_dict, stdout=subprocess.DEVNULL, stderr=subprocess.STDOUT)
    if res.returncode != 0:
        print(res.stderr)

def InvokePrusti(input_program_path: str, report_path: str, dependency_path: str, crates_lib_path:dict=dict()):
    if not os.path.exists(input_program_path):
        print(f"The input program {input_program_path} does not exist!")
        return
    if not input_program_path.endswith('.rs'):
        print(f"The input program {input_program_path} is not a rust file!")
        return
    if not os.path.exists(os.path.dirname(report_path)):
        os.makedirs(os.path.dirname(report_path))

    env_dict = dict(os.environ)     
    env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["PRUSTI_RUSTC_VERSION"]
    env_dict["PRUSTI_VERIFY_ERRORS_AS_WARNINGS"] = "true"
    env_dict["PRUSTI_INTERNAL_ERRORS_AS_WARNINGS"] = "true"
    env_dict["PRUSTI_SKIP_UNSUPPORTED_FEATURES"] = "true"

    cmd = [
        "prusti-rustc",
        input_program_path
    ]   
    
    cmd.append("-L")
    cmd.append(dependency_path)
    
    for crate_name, crate_lib_path in crates_lib_path.items():
        if crate_name=="prusti_contracts":
            continue
        cmd.append("--extern")
        cmd.append(f"{crate_name}={crate_lib_path}")
    if input_program_path.endswith("lib.rs"):
        cmd.append("--crate-type=lib")
    cmd.append("--edition=2021")
    with open(report_path, "w") as output_file:
        try:
            subprocess.run(
                cmd,
                cwd=os.path.dirname(input_program_path), 
                check=True, 
                env=env_dict, 
                stdout=output_file, 
                stderr=subprocess.STDOUT,
                timeout=TIMEOUT
            )
        except subprocess.TimeoutExpired as e:
            print(e)
            print(f"Timeout Command: {cmd}")
        except Exception as e:
            print(e)
    bin_path = input_program_path.replace(".rs", "")
    if os.path.exists(bin_path):
        os.remove(bin_path)
    lib_path = os.path.join(os.path.dirname(input_program_path), "liblib.rlib")
    if os.path.exists(lib_path):
        os.remove(lib_path)

def InvokeCargoPrusti(input_program_path: str, report_path: str):
    if not os.path.exists(input_program_path):
        print(f"The input program {input_program_path} does not exist!")
        return
    if not IsCargoProject(input_program_path):
        print(f"The input program {input_program_path} is not a cargo project!")
        return

    if not os.path.exists(os.path.dirname(report_path)):
        os.makedirs(os.path.dirname(report_path))   
        
    env_dict = dict(os.environ)     
    env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["PRUSTI_RUSTC_VERSION"]
    env_dict["PRUSTI_VERIFY_ERRORS_AS_WARNINGS"] = "true"
    env_dict["PRUSTI_INTERNAL_ERRORS_AS_WARNINGS"] = "true"
    env_dict["PRUSTI_SKIP_UNSUPPORTED_FEATURES"] = "true"
    
    subprocess.run(["cargo", "clean"], cwd=input_program_path, check=True, env=env_dict)
    with open(report_path, "w") as output_file:
        try:
            subprocess.run(
                ["cargo", "prusti"], 
                cwd=input_program_path, 
                check=True, 
                env=env_dict, 
                stdout=output_file, 
                stderr=subprocess.STDOUT,
                timeout=TIMEOUT
            )
        except Exception as e:
            print(e)

def InvokeMirai(input_program_path: str, report_path: str, dependency_path: str, crates_lib_path:dict=dict()) -> bool:
    if not os.path.exists(input_program_path):
        print(f"The input program {input_program_path} does not exist!")
        return
    if not input_program_path.endswith('.rs'):
        print(f"The input program {input_program_path} is not a rust file!")
        return
    if not os.path.exists(os.path.dirname(report_path)):
        os.makedirs(os.path.dirname(report_path))
    env_dict = dict(os.environ)     
    env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["MIRAI_RUSTC_VERSION"]
    env_dict["MIRAI_FLAGS"] = "--diag=library"
    cmd = [
        "mirai",
        input_program_path
    ]   
    cmd.append("-L")
    cmd.append(dependency_path)
    for crate_name, crate_lib_path in crates_lib_path.items():
        cmd.append("--extern")
        cmd.append(f"{crate_name}={crate_lib_path}")
    if input_program_path.endswith("lib.rs"):
        cmd.append("--crate-type=lib")
    cmd.append("--edition=2021")
    with open(report_path, "w") as output_file:
        try:
            subprocess.run(
                cmd,
                cwd=os.path.dirname(input_program_path), 
                check=True, 
                env=env_dict, 
                stdout=output_file, 
                stderr=subprocess.STDOUT,
                timeout=TIMEOUT
            )
        except subprocess.TimeoutExpired:
            print("Timeout Command: " + " ".join(cmd))
            return False
        except subprocess.CalledProcessError:
            print("Call Exception: " + " ".join(cmd))
            return False
    bin_path = input_program_path.replace(".rs", "")
    if os.path.exists(bin_path):
        os.remove(bin_path)
    lib_path = os.path.join(os.path.dirname(input_program_path), "liblib.rlib")
    if os.path.exists(lib_path):
        os.remove(lib_path)
    return True

def InvokeCargoMirai(input_program_path: str, report_path: str):
    if not os.path.exists(input_program_path):
        print(f"The input program {input_program_path} does not exist!")
        return
    if not IsCargoProject(input_program_path):
        print(f"The input program {input_program_path} is not a cargo project!")
        return

    if not os.path.exists(os.path.dirname(report_path)):
        os.makedirs(os.path.dirname(report_path))   

    env_dict = dict(os.environ)     
    env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["MIRAI_RUSTC_VERSION"]
    env_dict["MIRAI_FLAGS"] = "--diag=library"
    
    subprocess.run(["cargo", "clean"], cwd=input_program_path, check=True, env=env_dict)
    with open(report_path, "w") as output_file:
        try:
            subprocess.run(
                ["cargo", "mirai"], 
                cwd=input_program_path, 
                check=True, 
                env=env_dict, 
                stdout=output_file, 
                stderr=subprocess.STDOUT,
                timeout=TIMEOUT
            )
        except Exception as e:
            print(e)

def InvokeLockbud(input_program_path: str, report_path: str, dependency_path: str, crates_lib_path:dict=dict()) -> bool:
    if not os.path.exists(input_program_path):
        print(f"The input program {input_program_path} does not exist!")
        return
    if not input_program_path.endswith('.rs'):
        print(f"The input program {input_program_path} is not a rust file!")
        return

    if not os.path.exists(os.path.dirname(report_path)):
        os.makedirs(os.path.dirname(report_path))       

    env_dict = dict(os.environ)     
    # env_dict["RUSTFLAGS"] = "-A warnings"
    env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["LOCKBUD_RUSTC_VERSION"]
    env_dict["LOCKBUD_FLAGS"] = "-k all"
    env_dict["LOCKBUD_LOG"] = "info"

    cmd = [
        "lockbud",
        input_program_path
    ]
    cmd.append("-A")
    cmd.append("warnings")
    cmd.append("-L")
    cmd.append(dependency_path)

    for crate_name, crate_lib_path in crates_lib_path.items():
        cmd.append("--extern")
        cmd.append(f"{crate_name}={crate_lib_path}")

    cmd.append("--edition=2021")
    is_ok = False
    with open(report_path, "w") as output_file:
        try:
            res = subprocess.run(
                cmd,
                cwd=os.path.dirname(input_program_path),
                check=True,
                env=env_dict, 
                stdout=output_file, 
                stderr=subprocess.STDOUT,
                timeout=TIMEOUT
            )
            if res.returncode == 0:
                is_ok = True
        except Exception as e:
            pass
            # if DEBUG:
            #     print(e)
    bin_path = input_program_path.replace(".rs", "")
    if os.path.exists(bin_path):
        os.remove(bin_path)
    return is_ok

def InvokeCargoLockbud(input_program_path: str, report_path: str):
    if not os.path.exists(input_program_path):
        print(f"The input program {input_program_path} does not exist!")
        return
    if not IsCargoProject(input_program_path):
        print(f"The input program {input_program_path} is not a cargo project!")
        return
    if not os.path.exists(os.path.dirname(report_path)):
        os.makedirs(os.path.dirname(report_path))

    env_dict = dict(os.environ)     
    env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["LOCKBUD_RUSTC_VERSION"]
    env_dict["RUSTFLAGS"] = "-A warnings"
    subprocess.run(["cargo", "clean"], cwd=input_program_path, check=True, env=env_dict, stdout=subprocess.DEVNULL, stderr=subprocess.STDOUT)
    with open(report_path, "w") as output_file:
        try:
            subprocess.run(
                [    
                    "cargo",
                    "lockbud",
                    "-k",
                    "all",
                ], 
                cwd=input_program_path, 
                check=True,
                env=env_dict,
                stdout=output_file,
                stderr=subprocess.STDOUT,
                timeout=TIMEOUT
            )
        except Exception as e:
            print(e)

def InvokeMirChecker(input_program_path: str, report_path: str, dependency_path: str, crates_lib_path:dict=dict()) -> bool:
    if not os.path.exists(input_program_path):
        print(f"The input program {input_program_path} does not exist!")
        return False
    if not input_program_path.endswith('.rs'):
        print(f"The input program {input_program_path} is not a rust file!")
        return False
    if not os.path.exists(os.path.dirname(report_path)):
        os.makedirs(os.path.dirname(report_path))

    env_dict = dict(os.environ)     
    env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["MIRCHECKER_RUSTC_VERSION"]
    cmd = [
            "mir-checker",
            input_program_path
        ]   
    cmd.append("-L")
    cmd.append(dependency_path)
    
    for crate_name, crate_lib_path in crates_lib_path.items():
        cmd.append("--extern")
        cmd.append(f"{crate_name}={crate_lib_path}")

    cmd.append("--edition=2018")
    if input_program_path.endswith("lib.rs"):
        cmd.append("--crate-type=lib")
    with open(report_path, "w") as output_file:
        try:
            subprocess.run(
                cmd,
                cwd=os.path.dirname(input_program_path), 
                check=True, 
                env=env_dict, 
                stdout=output_file, 
                stderr=subprocess.STDOUT,
                timeout=TIMEOUT
            )
        except subprocess.CalledProcessError as e:
            # print("Failed Cmd in InvokeMirChecker: " + " ".join(cmd)) // 鸵鸟做法
            return True
        except subprocess.TimeoutExpired as e:
            print("Timeout Cmd in InvokeMirChecker: " + " ".join(cmd))
            return False
    return True

def InvokeCargoMirChecker(input_program_path: str, report_path: str) -> bool:
    if not os.path.exists(input_program_path):
        print(f"The input program {input_program_path} does not exist!")
        return
    if not IsCargoProject(input_program_path):
        print(f"The input program {input_program_path} is not a cargo project!")
        return
    
    if not os.path.exists(os.path.dirname(report_path)):
        os.makedirs(os.path.dirname(report_path))

    env_dict = dict(os.environ)     
    env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["MIRCHECKER_RUSTC_VERSION"]

    subprocess.run(["cargo", "clean"], cwd=input_program_path, check=True, env=env_dict)
    with open(report_path, "w") as output_file:
        try:
            subprocess.run(
                ["cargo", "mir-checker"], 
                cwd=input_program_path, 
                check=True, 
                env=env_dict, 
                stdout=output_file, 
                stderr=subprocess.STDOUT,
                timeout=TIMEOUT
            )
        except Exception as e:
            print("Failed Cmd in invokeCargoMirchecker: cargo mir-checker " + input_program_path)
            return False
    return True

def InvokeSemgrep(input_program_path: str, report_path: str) -> bool:
    if not os.path.exists(input_program_path):
        print(f"The input program {input_program_path} does not exist!")
        return
    if not input_program_path.endswith('.rs'):
        print(f"The input program {input_program_path} is not a rust file!")
        return
    if not os.path.exists(os.path.dirname(report_path)):
        os.makedirs(os.path.dirname(report_path))
    with open(report_path, "w") as output_file:
        try:
            res = subprocess.run(
                [
                    "semgrep", 
                    "scan",
                    "--config",
                    "p/rust",
                    "--pro",
                    "--json",
                    input_program_path,
                    "--json-output="+report_path
                ],
                cwd=os.path.dirname(input_program_path),
                stdout=subprocess.PIPE,
                stderr=subprocess.STDOUT,
                check=True,
                timeout=TIMEOUT
            )
            return True
        except Exception as e:
            print(e)
            return False

def InvokeProjectSemgrep(input_program_path: str, report_path: str):
    if not os.path.exists(input_program_path):
        print(f"The input program {input_program_path} does not exist!")
        return
    if not os.path.isdir(input_program_path):
        print(f"The input program {input_program_path} is not a directory!")
        return

    if not os.path.exists(os.path.dirname(report_path)):
        os.makedirs(os.path.dirname(report_path))

    with open(report_path, "w") as output_file:
        try:
            subprocess.run(
                [
                    "semgrep", 
                    "scan",
                    "--config",
                    "p/rust",
                    "--pro",
                    "--json",
                    "--json-output="+report_path
                ],
                cwd=os.path.dirname(input_program_path),
                stdout=subprocess.PIPE,
                stderr=subprocess.STDOUT,
                check=True,
                timeout=TIMEOUT
            )
        except Exception as e:
            print(e)

def InvokeRudra(input_program_path: str, report_path: str, dependency_path: str, crates_lib_path:dict=dict()) -> bool:
    if not os.path.exists(input_program_path):
        print(f"The input program {input_program_path} does not exist!")
        return
    if not input_program_path.endswith('.rs'):
        print(f"The input program {input_program_path} is not a rust file!")
        return
    if not os.path.exists(os.path.dirname(report_path)):
        os.makedirs(os.path.dirname(report_path))
    env_dict = dict(os.environ)     
    env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["RUDRA_RUSTC_VERSION"]
    cmd = [
        "rudra",
        "-Zrudra-enable-unsafe-destructor",
        "--crate-type",
        "lib",
        input_program_path
    ]
    cmd.append("-L")
    cmd.append(dependency_path)
    
    for crate_name, crate_lib_path in crates_lib_path.items():
        cmd.append("--extern")
        cmd.append(f"{crate_name}={crate_lib_path}")

    cmd.append("--edition=2018")
    if input_program_path.endswith("lib.rs"):
        cmd.append("--crate-type=lib")
    with tempfile.NamedTemporaryFile(prefix="rudra") as report_file:
        env_dict["RUDRA_REPORT_PATH"] = report_file.name
        try:
            subprocess.run(
                cmd,
                cwd=os.path.dirname(input_program_path),
                stdout=subprocess.PIPE,
                stderr=subprocess.STDOUT,
                env=env_dict,
                check=True,
                timeout=TIMEOUT
            )
        except subprocess.CalledProcessError as e:
            print("Failed Cmd: " + " ".join(cmd))
            return False
        except subprocess.TimeoutExpired as e:
            print("Timeout Cmd: " + " ".join(cmd))
            return False
        with open(report_file.name) as report_file_handle:
            content = report_file_handle.read()
            with open(report_path, 'w') as final_report_file:
                final_report_file.write(content)
    return True

def InvokeCargoRudra(input_program_path: str, report_path: str):
    if not os.path.exists(input_program_path):
        print(f"The input program {input_program_path} does not exist!")
        return
    if not IsCargoProject(input_program_path):
        print(f"The input program {input_program_path} is not a cargo project!")
        return

    if not os.path.exists(os.path.dirname(report_path)):
        os.makedirs(os.path.dirname(report_path))

    env_dict = dict(os.environ)     
    env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["RUDRA_RUSTC_VERSION"]

    subprocess.run(["cargo", "clean"], cwd=input_program_path, check=True, env=env_dict)
    with tempfile.TemporaryDirectory() as temp_dir:
        #the report name of cargo-rudra is like rudra_report-lib-xxx-xxx, different from rudra.
        env_dict["RUDRA_REPORT_PATH"] = os.path.join(temp_dir, "rudra_report")
        try:
            subprocess.run(
                [
                    "cargo",
                    "rudra",
                    "--",
                    "-Zrudra-enable-unsafe-destructor",
                    "--crate-type",
                    "lib"
                ],
                cwd=input_program_path,
                stdout=subprocess.PIPE,
                stderr=subprocess.STDOUT,
                env=env_dict,
                check=True,
                timeout=TIMEOUT
            )
        except Exception as e:
            print(e)

        files = os.listdir(temp_dir)
        with open(report_path, 'w') as final_report_file:
            if len(files)>0:
                with open(os.path.join(temp_dir, files[0])) as report_file_handle:
                    content = report_file_handle.read()
                    final_report_file.write(content)

def ReadDependencies(cargo_toml_path:str):
    try:
        with open(cargo_toml_path, "r") as f:
            cargo_toml = toml.load(f)
        return cargo_toml.get("dependencies", {})
    except FileNotFoundError:
        print(f"Cargo.toml Can not be found: {cargo_toml_path}")
        exit(1)
    except toml.TomlDecodeError as e:
        print(f"Cargo.toml is not a valid toml file: {e}")
        exit(1)    

def GetDependenciesPath(lib_dir:str, cargo_toml_path:str):
    dependencies = ReadDependencies(cargo_toml_path)
    dependencies_path = {}
    for crate_name in dependencies.keys():
        crate_name = crate_name.replace("-", "_")
        lib_files = [f for f in os.listdir(lib_dir) if f.startswith(f"lib{crate_name}-") and (f.endswith(".rlib") or f.endswith(".so"))]
        if len(lib_files) == 0:
            print(f"No lib file found for {crate_name}")
            exit(1)
        dependencies_path[crate_name] = os.path.join(lib_dir, lib_files[0])
    return dependencies_path

def BuildDependencyLibrary(tool_name: str, tool_seeds_path: str):
    if SEMGREP:
        return
    dependency_path = os.path.join(os.getcwd(), "dependency")
    if os.path.exists(dependency_path):
        shutil.rmtree(dependency_path)
    os.mkdir(dependency_path)
    env_dict = dict(os.environ)
    if LOCKBUD:
        env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["LOCKBUD_RUSTC_VERSION"]
    elif RUDRA:
        env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["RUDRA_RUSTC_VERSION"]
    elif MIRAI:
        env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["MIRAI_RUSTC_VERSION"]
    elif MIRCHECKER:
        env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["MIRCHECKER_RUSTC_VERSION"]
    elif PRUSTI:
        env_dict["RUSTUP_TOOLCHAIN"] = CONFIG["ToolConfig"]["PRUSTI_RUSTC_VERSION"]
    os.mkdir(os.path.join(dependency_path, tool_name))
    project2dependencies = {}
    project2crates = {}
    for rule_dir in os.listdir(tool_seeds_path):
        if os.path.isdir(os.path.join(tool_seeds_path, rule_dir)):
            project2dependencies[rule_dir] = {}
            project2crates[rule_dir] = {}
            for project_dir in os.listdir(os.path.join(tool_seeds_path, rule_dir)):
                project_path = os.path.join(tool_seeds_path, rule_dir, project_dir)
                if os.path.isdir(project_path):
                    InvokeCargoBuild(project_path, env_dict)
                    project_dependency_path = os.path.join(project_path, "target", "debug", "deps")
                    new_project_dependency_path = os.path.join(dependency_path, tool_name, rule_dir, project_dir)
                    if not os.path.exists(new_project_dependency_path):
                        shutil.copytree(project_dependency_path, new_project_dependency_path)
                    project2dependencies[rule_dir][project_dir] = {}
                    project2crates[rule_dir][project_dir] = {}
                    project2dependencies[rule_dir][project_dir] = new_project_dependency_path
                    project2crates[rule_dir][project_dir] = GetDependenciesPath(new_project_dependency_path, os.path.join(project_path, "Cargo.toml"))
    from utils.fileutils import WriteDictToJson
    WriteDictToJson(os.path.join(dependency_path, tool_name + "_project2dep.json"), project2dependencies)
    WriteDictToJson(os.path.join(dependency_path, tool_name + "_project2crate.json"), project2crates)
    
def InvokePMD(input_program_path: str, rule_name: str, report_path: str) -> bool:
    if not os.path.exists(input_program_path):
        print(f"The input program {input_program_path} does not exist!")
        return False
    if not input_program_path.endswith('.java'):
        print(f"The input program {input_program_path} is not a Java file!")
        return False
    if not os.path.exists(os.path.dirname(report_path)):
        os.makedirs(os.path.dirname(report_path))
    rule_tokens = rule_name.split("_")
    with open(report_path, "w") as output_file:
        cmds = [
            PMD_PATH, 
            "check",
            "-d",
            input_program_path,
            "-R",
            "category/java/" + rule_tokens[0] + ".xml/" + rule_tokens[1],
            "-f",
            "json",
            "-r",
            report_path,
            "--no-cache"
        ]
        InvokeCommandsWithContext(cmds, os.path.dirname(input_program_path))

def InvokeInfer(input_program_path: str, report_dir_path: str, class_dir_path: str) -> bool:
    if not os.path.exists(input_program_path):
        print(f"InvokeInfer: The input program {input_program_path} does not exist!")
        return False
    if not input_program_path.endswith('.java'):
        print(f"InvokeInfer: The input program {input_program_path} is not a Java file!")
        return False
    if not os.path.exists(os.path.dirname(report_dir_path)):
        os.makedirs(os.path.dirname(report_dir_path))
    cmds = [
        INFER_PATH, 
        "run",
        "-o",
        report_dir_path,
        "--annotation-reachability",
        "--bufferoverrun",
        "--cost",
        "--inefficient-keyset-iterator",
        "--litho-required-props",
        "--loop-hoisting",
        "--pulse",
        "--racerd",
        "--sil-validation",
        "--scope-leakage",
        "--starvation",
        "--",
        JAVAC_PATH,
        "-d",
        class_dir_path,
        "-cp",
        INFER_DEPENDENCY_PATH,
        input_program_path
    ]
    try:
        subprocess.run(
            cmds,
            cwd=os.path.dirname(input_program_path),
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            timeout=TIMEOUT
        )
        global succ_invocation
        global fail_invocation
        if os.path.exists(class_dir_path):
            from utils.fileutils import GetAllFilePaths
            class_file_list = GetAllFilePaths(class_dir_path)
            if len(class_file_list) > 0 and os.path.exists(report_dir_path):
                succ_invocation = succ_invocation + 1
                return True
            else:
                fail_invocation = fail_invocation + 1
                failed_commands.append(" ".join(cmds))
                return False
        else:
            fail_invocation = fail_invocation + 1
            failed_commands.append(" ".join(cmds))
            return False
    except subprocess.TimeoutExpired:
        timeout_commands.append(" ".join(cmds))
        fail_invocation = fail_invocation + 1
        return False

def InvokeSpotBugs(input_program_path: str, report_path: str) -> bool:
    # input_program_path should be the directory of class files compiled by seed files.
    if not os.path.exists(input_program_path):
        print(f"The input program {input_program_path} does not exist!")
        return False
    if not os.path.exists(os.path.dirname(report_path)):
        os.makedirs(os.path.dirname(report_path))
    with open(report_path, "w") as output_file:
        cmds = [
            SPOTBUGS_PATH,
            "-textui",
            "-xml:withMessages",
            "-output",
            report_path,
            input_program_path
        ]
        try:
            subprocess.run(
                cmds,
                cwd=os.path.dirname(input_program_path),
                stdout=subprocess.PIPE,
                stderr=subprocess.STDOUT,
                check=True,
                timeout=TIMEOUT
            )
            return True
        except subprocess.CalledProcessError:
            print("Fail to invoke SpotBugs: " + " ".join(cmds))
            return False
        except subprocess.TimeoutExpired:
            print("SpotBugs Timeout!")
            return False

def CreateSonarQubeProject(projectName) -> bool:
    curlPostCommands: List[str] = []
    curlPostCommands.append("curl")
    curlPostCommands.append("-u")
    curlPostCommands.append("admin:Admin123456=")
    curlPostCommands.append("-X")
    curlPostCommands.append("POST")
    curlPostCommands.append("http://localhost:9000/api/projects/create?name=" + projectName + "&project=" + projectName)
    return InvokeCommands(curlPostCommands)

def GenerateSonarQubeToken(projectName) -> str:
    curlPostCommands: List[str] = []
    curlPostCommands.append("curl")
    curlPostCommands.append("-u")
    curlPostCommands.append("admin:Admin123456=")
    curlPostCommands.append("-X")
    curlPostCommands.append("POST")
    curlPostCommands.append("http://localhost:9000/api/user_tokens/generate?name=AnalyzeTestOne&projectKey=" + projectName + "&type=PROJECT_ANALYSIS_TOKEN")
    output = InvokeCommandsWithOutput(curlPostCommands)
    json_data = json.loads(output)
    return json_data["token"]

def DeleteSonarQubeProject(project_name) -> bool:
    curlPostCommands: List[str] = []
    curlPostCommands.append("curl")
    curlPostCommands.append("-u")
    curlPostCommands.append("admin:Admin123456=")
    curlPostCommands.append("-X")
    curlPostCommands.append("POST")
    curlPostCommands.append("http://localhost:9000/api/projects/delete?name=" + project_name + "&project=" + project_name)
    return InvokeCommands(curlPostCommands)

def WaitTaskEnd() -> bool:
    start = False
    import time
    startTime = int(time.time())
    while True:
        commands: List[str] = []
        commands.append("curl")
        commands.append("-u")
        commands.append("admin:Admin123456=")
        commands.append("http://localhost:9000/api/ce/activity_status?component=" + SONARQUBE_PROJECT_KEY)
        output = InvokeCommandsWithOutput(commands)
        root = json.loads(output)
        pending = root["pending"]
        failing = root["failing"]
        inProgress = root["inProgress"]
        if pending > 0 or inProgress > 0:
            start = True
        if start and pending == 0 and inProgress == 0 and failing == 0:
            return True
        if failing > 0:
            return False
        duration = int(time.time()) - startTime
        if duration > 1000 * 10:
            return True

def InvokeSonarQube(input_program_path: str, report_path: str) -> bool:
    DeleteSonarQubeProject(SONARQUBE_PROJECT_KEY)
    CreateSonarQubeProject(SONARQUBE_PROJECT_KEY)
    sq_token = GenerateSonarQubeToken(SONARQUBE_PROJECT_KEY)
    invokeCommands: List[str] = []
    from utils.OSUtils import IsWindows
    if IsWindows():
        invokeCommands.append("cmd.exe")
        invokeCommands.append("/c")
    else:
        invokeCommands.append("/bin/bash")
        invokeCommands.append("-c")
    if "mutants" in input_program_path:
        base_dir = MUTANT_DIR_PATH
    else:
        base_dir = os.getcwd()
    invokeCommands.append(SONAR_SCANNER_PATH
                    + " -Dsonar.projectKey=" + SONARQUBE_PROJECT_KEY
                    + " -Dsonar.projectBaseDir=" + base_dir
                    + " -Dsonar.sources=" + input_program_path
                    + " -Dsonar.host.url=http://localhost:9000 -Dsonar.login=" + sq_token)
    if(InvokeCommands(invokeCommands)):
        WaitTaskEnd()
    else:
        return False
    curlCommands: List[str] = []
    curlCommands.append("curl")
    curlCommands.append("-u")
    curlCommands.append("admin:Admin123456=")
    curlCommands.append("http://localhost:9000/api/issues/search?p=1&ps=500&componentKeys=" + SONARQUBE_PROJECT_KEY)
    output = InvokeCommandsWithOutput(curlCommands)
    WriteStrToFile(report_path, output)
    return True