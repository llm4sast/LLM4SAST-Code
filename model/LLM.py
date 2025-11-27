import os

from langchain_openai import ChatOpenAI
from langchain_core.messages import HumanMessage, SystemMessage
from langchain.memory import ConversationBufferMemory

# Set OpenAI API key and base URL
api_base = ""
api_key = ""
my_api_key = ""

os.environ["OPENAI_API_KEY"] = api_key
os.environ["OPENAI_API_BASE"] = api_base

class LLMModel:
    def __init__(self, model_name: str, system_message: str):
        self.model = ChatOpenAI(model=model_name, request_timeout=120)
        self.system_message = system_message
        self.memory = ConversationBufferMemory(memory_key="chat_history", return_messages=True)

    def query_model(self, user_message: str, retain_memory: bool = False, json_flag: bool = False) -> str:
        if retain_memory and self.memory.chat_memory.messages:
            messages = self.memory.chat_memory.messages.copy()
        else:
            messages = [SystemMessage(content=self.system_message)]
        messages.append(HumanMessage(content=user_message))
        query_result = self.model.invoke(messages)
        response = query_result.content
        return response

def query(llm_query: LLMModel, input_prompt):
    response = llm_query.query_model(input_prompt, retain_memory=False, json_flag=False)
    return response