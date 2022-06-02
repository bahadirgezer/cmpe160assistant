import os
import re

if __name__ == "__main__":
    users_dir = os.getcwd()+f"/submitted-graded"
    users = [item for item in os.listdir(users_dir) if os.path.isdir(os.path.join(users_dir, item))]

    totals = dict()
    #javadoc
    for user in users:
        javadoc_path = os.getcwd()+f"/all-grades/javadoc-grades/{user}_javadoc.txt"
        compile_path = os.getcwd()+f"/all-grades/compile-grades/{user}_compile.txt"
        class_structure_path = os.getcwd()+f"/all-grades/class-structure-grades/{user}_structure.txt"
        polished_path = os.getcwd()+f"/all-grades/polished/{user}.txt"
        combined_path = os.getcwd()+f"/all-grades/combined/{user}.txt"

        with open(javadoc_path, "r") as javadoc_file:
            javadoc_grade = float(javadoc_file.readline())
            javadoc_comment = javadoc_file.readline()
            javadoc_rubric = javadoc_file.readline()

        with open(compile_path, "r") as compile_file:
            compile_grade = float(compile_file.readline())
            compile_comment = compile_file.readline()
            compile_rubric = compile_file.readline()

        with open(class_structure_path, "r") as class_structure_file:
            class_structure_grade = float(class_structure_file.readline())
            class_structure_comment = class_structure_file.readline()
            class_structure_rubric = class_structure_file.readline()

        with open(polished_path, "r") as polished_file:
            inputs = []
            for i in range(35):
                inputs.append(polished_file.readline())
            test_cases_total = polished_file.readline()
            polished_grade = float(polished_file.readline())

        totals.update({user:round(polished_grade + class_structure_grade + compile_grade + javadoc_grade, 2)})

        '''
        combined_file = open(combined_path, "w")
        for i in range(35):
            combined_file.write(inputs[i] + '\n')
        combined_file.write(test_cases_total + '\n')
        combined_file.write("javadoc grade: " + str(javadoc_grade) + "/10" + '\n')
        combined_file.write("compile grade: " + str(compile_grade) + "/10" + '\n')
        combined_file.write("correct class structure grade: " + str(class_structure_grade) + "/10" + '\n')
        combined_file.write('\n')
        combined_file.write("javadoc comment: " + javadoc_comment + '\n')
        combined_file.write("compile comment: " + compile_comment + '\n')
        combined_file.write("class structure comment: " + class_structure_comment + '\n')
        combined_file.write(javadoc_rubric + '\n')
        combined_file.write(compile_rubric+ '\n')
        combined_file.write(class_structure_rubric + '\n')
        combined_file.write("total grade: " + str(round(polished_grade + class_structure_grade + compile_grade + javadoc_grade, 2))  + "/100" + '\n')
        '''
    
    for user, grade in sorted(totals.items(), key=lambda x:x[1], reverse=True): print(f"{user}:".rjust(20, ' ') + f"{str(format(round(grade, 2), '.2f')).rjust(8, ' ')}")
