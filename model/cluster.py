import os, subprocess
from utils.config import GUMTREE_PATH, DIFF_DIR_PATH
from utils.fileutils import GetAllFilePaths

import torch
from transformers import AutoTokenizer, AutoModel

def InvokeGumTree4DiffAnalysis(seed_path, mutant_dir_path):
    mutant_paths = GetAllFilePaths(mutant_dir_path)
    for mutant_path in mutant_paths:
        tokens = mutant_path.split(os.sep)
        result_path = os.path.join(DIFF_DIR_PATH, tokens[-3], tokens[-2])
        cmd = [GUMTREE_PATH, seed_path, mutant_path, "-f", "JSON", "-o", result_path]
        try:
            subprocess.run(
                cmd,
                check=True,
                capture_output=True,
                text=True
            )
        except subprocess.CalledProcessError:
            print("Failed GumTree Invocation: " + " ".join(cmd))

def CodeBertToGenerateVector():
    tokenizer = AutoTokenizer.from_pretrained("microsoft/codebert-base")
    model = AutoModel.from_pretrained("microsoft/codebert-base")

    input_text = "Update SimpleName VAR"
    inputs = tokenizer(input_text, return_tensors="pt")

    # 通过模型前向传播，得到隐藏状态（即向量表示）
    with torch.no_grad():
        outputs = model(**inputs)
        # 取最后一层隐藏状态作为token的向量表示
        last_hidden_states = outputs.last_hidden_state

    # 我们通常取 [CLS] token 的向量作为整个句子的表示
    # 或者对所有token的向量求平均
    sentence_embedding = last_hidden_states.mean(dim=1).squeeze().numpy()

    print(sentence_embedding.shape) # (768,)
    # print(sentence_embedding) # feature vector
         
            