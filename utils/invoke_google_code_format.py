import subprocess
import shutil
import os

#if input_filepath == output_filepath, modify the origin file
def format_java_file_to_new(input_filepath, output_filepath):
    if not input_filepath == output_filepath:
        try:
            shutil.copy2(input_filepath, output_filepath)
        except FileNotFoundError:
            return False
        except Exception as e:
            return False


    # invokeCommands = ["java", "-jar", GOOGLE_FORMAT_PATH, "--replace", this.filePath];
    command = [
        "java",
        "-jar",
        GOOGLE_FORMATTER_JAR,
        "--replace",
        output_filepath
    ]

    try:
        result = subprocess.run(
            command,
            check=True,
            capture_output=True,
            text=True,
            encoding='utf-8'
        )
        return True

    except FileNotFoundError:
        print(f"can not find file")
        return False
    except subprocess.CalledProcessError as e:
        print(f"Called Process Error")
        return False
    except Exception as e:
        print(f"fail")
        return False


#example
if __name__ == "__main__":
    GOOGLE_FORMATTER_JAR = "/home/zhe/workspace_DISK/google-java-format-1.27.0-all-deps.jar"
    input_file_path = r"/home/zhe/workspace_DISK/test_java/Main.java"
    output_file_path = r"/home/zhe/workspace_DISK/test_java/Main_formatted.java"
    format_java_file_to_new(input_file_path, output_file_path)