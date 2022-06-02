import os
import re

if __name__ == "__main__":
    search_string = ""
    users_dir = os.getcwd()+f"/submitted-graded"
    users = [item for item in os.listdir(users_dir) if os.path.isdir(os.path.join(users_dir, item))]
    
    found = False
    for user in users:    
        if found:
            break
        for root, dirs, files in os.walk(users_dir + f"/{user}"):
            if found:
                break
            for file in files:
                current_file = open(os.path.join(root, file), "r")
                try:
                    file_str = current_file.read()
                except UnicodeDecodeError:
                    continue

                if re.search(search_string, file_str, re.IGNORECASE):
                    print(f"{user} found in {file}")
                    for line in file_str.splitlines():
                        if re.search(search_string, line, re.IGNORECASE):
                            print('\t' + line)

                    found = False
                current_file.close()                
                if found:
                    break
    if not found:
        print("Not found")