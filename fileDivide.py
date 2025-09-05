import fitz
import re
import os

def extract_and_clean_text(pdf_path):
    doc = fitz.open(pdf_path)
    text = ""
    for page in doc:
        text += page.get_text()
    doc.close()
    cleaned_text = re.sub(r'[^A-Za-z0-9 ]+', '', text)
    words = cleaned_text.split()
    return words

def write_words_to_files(words, chunk_size=150):
    os.makedirs("output2", exist_ok=True)
    for i in range(0, len(words), chunk_size):
        chunk = words[i:i+chunk_size]
        file_name = f"output2/file_{i//chunk_size + 1}.txt"
        with open(file_name, "w") as f:
            f.write(" ".join(chunk))

pdf_file = "./Book1.pdf"  
words = extract_and_clean_text(pdf_file)
write_words_to_files(words)