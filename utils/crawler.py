import os
import shutil
import requests
import zipfile
import glob
from utils.ReorgSeed import *
import tempfile
import io

def download_and_extract_repository(zip_url, destination_folder):
    if not os.path.exists(destination_folder):
        try:
            response = requests.get(zip_url)
            if response.status_code == 200:
                zip_file_path = "temp_repo.zip"
                with open(zip_file_path, 'wb') as f:
                    f.write(response.content)
                with zipfile.ZipFile(zip_file_path, 'r') as zip_ref:
                    top_folder = zip_ref.namelist()[0].split('/')[0]
                    zip_ref.extractall()
                shutil.move(top_folder, destination_folder)
                os.remove(zip_file_path)
                print(f"Repo download and extract to  {destination_folder} Successfully")
            else:
                print(f"Repo download error: {response.status_code}")
        except Exception as e:
            print(f"Repo download and extract error {e}")
    else:
        print(f"Target {destination_folder} exists. Skip download")

def copy_test_cases(source_folder, destination_folder):
    if not os.path.exists(destination_folder):
        os.makedirs(destination_folder)
    for root, dirs, files in os.walk(source_folder):
        relative_path = os.path.relpath(root, source_folder)
        destination_subfolder = os.path.join(destination_folder, relative_path)
        if not os.path.exists(destination_subfolder):
            os.makedirs(destination_subfolder)
        for file in files:
            source_file_path = os.path.join(root, file)
            destination_file_path = os.path.join(destination_subfolder, file)
            shutil.copy2(source_file_path, destination_file_path)
    

def get_test_cases(zip_url,clone_destination,source_folder,copy_destination):
    download_and_extract_repository(zip_url, clone_destination)
    copy_test_cases(source_folder, copy_destination)


def get_lockbud(repo_path, testcase_path):
    # repo_url = "https://github.com/BurtonQin/lockbud.git"
    zip_url = "https://github.com/BurtonQin/lockbud/archive/refs/heads/all.zip"
    clone_destination = os.path.join(repo_path, "Lockbud")
    source_folder = os.path.join(clone_destination, "toys")
    copy_destination = os.path.join(testcase_path, "lockbud")
    get_test_cases(zip_url,clone_destination,source_folder,copy_destination)

def get_mirai(repo_path, testcase_path):
    # repo_url = "https://github.com/endorlabs/MIRAI.git"
    zip_url = "https://github.com/endorlabs/MIRAI/archive/refs/heads/main.zip"
    clone_destination = os.path.join(repo_path, "MIRAI")
    source_folder = os.path.join(clone_destination, "checker", "tests")
    copy_destination = os.path.join(testcase_path, "mirai")
    get_test_cases(zip_url,clone_destination,source_folder,copy_destination)


def get_mirchecker(repo_path, testcase_path):
    # repo_url = "https://github.com/lizhuohua/rust-mir-checker.git"
    zip_url = "https://github.com/lizhuohua/rust-mir-checker/archive/refs/heads/master.zip"
    clone_destination = os.path.join(repo_path, "MirChecker")
    source_folder = os.path.join(clone_destination, "tests")
    copy_destination = os.path.join(testcase_path, "mirchecker")
    get_test_cases(zip_url,clone_destination,source_folder,copy_destination)
    #mirchecker testcases need macro dependency
    source_folder = os.path.join(clone_destination, "macros")
    copy_destination = os.path.join(testcase_path, "macros")
    get_test_cases(zip_url,clone_destination,source_folder,copy_destination)


def get_prusti(repo_path, testcase_path):
    # repo_url = "https://github.com/viperproject/prusti-dev.git"
    zip_url = "https://github.com/viperproject/prusti-dev/archive/refs/heads/master.zip"
    clone_destination = os.path.join(repo_path, "Prusti")
    source_folder = os.path.join(clone_destination, "prusti-tests")
    copy_destination = os.path.join(testcase_path, "prusti")
    get_test_cases(zip_url,clone_destination,source_folder,copy_destination)

    source_folder = os.path.join(clone_destination, "prusti-contracts")
    copy_destination = os.path.join(testcase_path, "prusti", "prusti-contracts")
    get_test_cases(zip_url,clone_destination,source_folder,copy_destination)

def get_rudra(repo_path, testcase_path):
    # repo_url = "https://github.com/sslab-gatech/Rudra.git"
    zip_url = "https://github.com/sslab-gatech/Rudra/archive/refs/heads/master.zip"
    clone_destination = os.path.join(repo_path, "Rudra")
    source_folder = os.path.join(clone_destination, "tests")
    copy_destination = os.path.join(testcase_path, "rudra")
    get_test_cases(zip_url,clone_destination,source_folder,copy_destination)

def get_semgrep(repo_path, testcase_path):
    # repo_url = "https://github.com/semgrep/semgrep-rules.git"
    zip_url = "https://zenodo.org/records/15016771/files/semgrep_rules.zip"
    copy_destination = os.path.join(testcase_path, "semgrep")
    response = requests.get(zip_url, stream=True)
    response.raise_for_status()  
    with io.BytesIO(response.content) as zip_file:
        with zipfile.ZipFile(zip_file, 'r') as zip_ref:
            zip_ref.extractall(copy_destination)



def crawl():
    if not os.path.exists(SEED_PATH):
        os.mkdir(SEED_PATH)
    with tempfile.TemporaryDirectory() as temp_dir:
        repo_path = os.path.join(temp_dir, "repo")
        get_rudra(repo_path, RUDRA_SEED_PATH)
        reorg_rudra(RUDRA_SEED_PATH)
        get_lockbud(repo_path, LOCKBUD_SEED_PATH)
        reorg_lockbud(LOCKBUD_SEED_PATH)
    # get_semgrep()
    # reorg_semgrep()
    # get_mirchecker()
    # reorg_mirchecker()
    # get_mirai()
    # reorg_mirai()

    # get_prusti()
    # reorg_prusti()
    shutil.rmtree(REPO_PATH) 





if __name__ == "__main__":
    crawl()

