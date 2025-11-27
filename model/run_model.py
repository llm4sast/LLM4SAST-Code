import sys
import torch

from transformers import AutoTokenizer, AutoModel
from sklearn.metrics.pairwise import cosine_similarity
from sentence_transformers import SentenceTransformer, util

def calculate_similarity(text1, text2):
    tokenizer = AutoTokenizer.from_pretrained("microsoft/codebert-base")
    model = AutoModel.from_pretrained("microsoft/codebert-base")

    def get_embedding(text):
        inputs = tokenizer(text, return_tensors="pt", truncation=True, max_length=512)
        with torch.no_grad():
            outputs = model(**inputs)
        embedding = outputs.last_hidden_state.mean(dim=1).squeeze().numpy()
        return embedding

    embeddings = []
    embeddins1 = get_embedding(text1)
    embeddings.append(embeddins1)
    embeddins2 = get_embedding(text2)
    embeddings.append(embeddins2)
    print(embeddins1)
    print(embeddins2)
    similarity = cosine_similarity([embeddings[0]], [embeddings[1]])[0][0]
    print(similarity)

    return similarity

def runTFIDF():
    from sklearn.feature_extraction.text import TfidfVectorizer
    # args = sys.argv
    # print("---args1---")
    # print(args[1])
    # print(args[1].split(" "))
    # print("---args2---")
    # print(args[2])
    # print(args[2].split(" "))
    # documents = [args[1], args[2]]
    documents = ["", "", ""]

    # calculdate TF-IDF vector
    vectorizer = TfidfVectorizer()
    tfidf_matrix = vectorizer.fit_transform(documents)

    similarity_matrix = cosine_similarity(tfidf_matrix)
    print(similarity_matrix[0][1])

def runSentenceBert():
    args = sys.argv
    print("---args1---")
    print(args[1])
    print(args[1].split(" "))
    print("---args2---")
    print(args[2])
    print(args[2].split(" "))
    documents = [args[1], args[2]]
    # documents = ["This is a document", "This is the second document", "Another document", "SPAM", "Email", "Google", "OpenAI"]
    model = SentenceTransformer("all-MiniLM-L6-v2")
    embeddings = model.encode(documents)
    cosine_scores = util.cos_sim(embeddings, embeddings)
    # print(cosine_scores)
    print(cosine_scores[0][1].item())

def runQwen3():
    model = SentenceTransformer("Qwen/Qwen3-Embedding-0.6B")

    args = sys.argv
    print("---args1---")
    print(args[1])
    print(args[1].split(" "))
    print("---args2---")
    print(args[2])
    print(args[2].split(" "))
    documents = [args[1], args[2]]
    document_embeddings = model.encode(documents)
    similarity = model.similarity(document_embeddings, document_embeddings)
    print(similarity[0][1].item())

def main():
    runQwen3()

main()
