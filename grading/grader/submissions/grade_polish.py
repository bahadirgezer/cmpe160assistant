import os
import re
import sys


class Comparable(object):
    def _compare(self, other, method):
        try:
            return method(self._cmpkey(), other._cmpkey())
        except (AttributeError, TypeError):
            # _cmpkey not implemented, or return different type,
            # so I can't compare with "other".
            return NotImplemented

    def __lt__(self, other):
        return self._compare(other, lambda s, o: s < o)

    def __le__(self, other):
        return self._compare(other, lambda s, o: s <= o)

    def __eq__(self, other):
        return self._compare(other, lambda s, o: s == o)

    def __ge__(self, other):
        return self._compare(other, lambda s, o: s >= o)

    def __gt__(self, other):
        return self._compare(other, lambda s, o: s > o)

    def __ne__(self, other):
        return self._compare(other, lambda s, o: s != o)

class Input(Comparable):
    def __init__(self):
        self.users = set()
        self.grades = dict()
        self.profits = dict()
        self.flights = dict()
        self.unloads = dict()
        self.comment = dict()
        self.bonus =  dict()

    def distribute_bonus(self):
        profits_list = list(self.profits.values())
        flights_list = list(self.flights.values())
        unloads_list = list(self.unloads.values())

        #6p profits, 2p flights, 2p unloads
        for user in sorted(list(self.users)):
            if self.grades[user] != 100:
                continue
            
            #divide 6 points to the values between max profit and min profit
            try:
                bonus = 6 * (self.profits[user] - min(profits_list)) / (max(profits_list) - min(profits_list))
            except ZeroDivisionError:
                bonus = 0
            try:
                bonus += 2 * (self.flights[user] - min(flights_list)) / (max(flights_list) - min(flights_list))
            except ZeroDivisionError:
                bonus += 0
            try:
                bonus += 2 * (self.unloads[user] - min(unloads_list)) / (max(unloads_list) - min(unloads_list))
            except ZeroDivisionError:
                bonus += 0
            
            #if bonus > 9:
                #print(user, end=" ")

            self.bonus[user] = bonus
            #print(round(bonus, 2))

    def add_user(self, user):
        self.users.add(user)
    
    def add_grade(self, user, grade):
        self.grades.update({user: grade})
    
    def add_comment(self, user, comment):
        self.comment.update({user: comment})

    def add_passed_info(self, user, profit, flights, unloads):
        self.profits.update({user: profit})
        self.flights.update({user: flights})
        self.unloads.update({user: unloads})
    
    def get_grade_average(self):
        grades = list(self.grades.values())
        total, count = 0, 0
        for grade in grades:
            if grade == 0:
                continue
            total += grade
            count += 1
        return (total/count)
    
    def get_grades(self):
        grades_str = ""
        for user in self.users:
            grades_str += "\t" + user + ": " + str(self.grades[user]) + "\n"
        return grades_str
    
    def get_grade(self, user):
        return self.grades.get(user, 0)
    
    def get_bonus(self, user):
        return self.bonus.get(user, 0)

    def _cmpkey(self):
        return (self.get_grade_average())

def print_averages(inputs):
    for input_num, input_obj in enumerate(sorted(inputs, reverse=True)):
        print(f"input{str(input_num).rjust(3, ' ')} average grade: " + str(format(round(input_obj.get_grade_average(), 2), '.2f')).rjust(5, ' '))


if __name__ == "__main__":
    num_of_inputs = int(sys.argv[1])
    grades_dir = os.getcwd()+f"/grades"
    polished_dir = grades_dir+f"/polished"
    grades = [item for item in os.listdir(grades_dir) if os.path.isfile(os.path.join(grades_dir, item))]
    all_users = set()
    if not os.path.exists(polished_dir):
        os.mkdir(polished_dir)

    inputs = [Input() for i in range(num_of_inputs)]
    for grade in grades:
        if not grade.endswith("grade.txt"):
            continue
        
        username = grade.split("_")[0]
        with open(f"{grades_dir}/{grade}", "r") as f:
            lines = f.readlines()
            if len(lines) == 0:
                print(username)
                continue
    
            all_users.add(username)
            if len(lines) == 1:
                for input_num in range(num_of_inputs):
                    input_obj = inputs[input_num]
                    input_obj.add_user(username)
                    input_obj.add_grade(username, 0)
                    input_obj.add_comment(username, "compile error")
                continue

            for input_num, line in enumerate(lines):
                match = re.findall(r"input.+?: (\d+), (.+?)[,|.|\n]", line)
                if match:
                    input_obj = inputs[input_num]
                    input_obj.add_user(username)
                    input_obj.add_grade(username, int(match[0][0]))
                    input_obj.add_comment(username, match[0][1])
                    if match[0][1] == "passed":
                        info = re.findall(r"profit: ([-]?\d+), flight count: (\d+), unload count: (\d+)", line)
                        input_obj.add_passed_info(username, int(info[0][0]), int(info[0][1]), int(info[0][2]))
                    elif re.search("invalid output", match[0][1]):
                        grade = input_obj.get_grade(username)
                        grade = grade/2
                        input_obj.add_grade(username, grade)
            
    for input_num, input_obj in enumerate(inputs):
        input_obj.distribute_bonus()

    print_averages(inputs)

    every_grade = []
    total_average_count = 0
    total_average_grade = 0
    #discarded = [49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35]
    discarded = []
    for user in all_users:
        polished_output = open(f"{polished_dir}/{user}.txt", "w")
        total_grade = 0

        accepted_input_num = 0
        for input_num, input_obj in enumerate(inputs):
            if input_num in discarded:
                continue
            
            grade = input_obj.get_grade(user)
            bonus = 0
            if grade == 100:
                bonus = input_obj.get_bonus(user)
            comment = input_obj.comment[user]
            polished_output.write(f"input{str(accepted_input_num).rjust(3, ' ')}, {str(format(round(grade+bonus, 2), '.2f')).rjust(6, ' ')}, {format(round(bonus, 2), '.2f').rjust(5, ' ')}, {comment}\n")
            total_grade += grade + bonus
            accepted_input_num += 1
        
        average_grade = total_grade/(num_of_inputs-len(discarded))
        every_grade.append(round(average_grade, 2))
        if average_grade != 0:
            total_average_grade += average_grade
            total_average_count += 1
        polished_output.write(f"total test case grade, {str(format(round(average_grade, 2), '.2f')).rjust(6, ' ')}\n")
        polished_output.write(f"{str(format(round(average_grade * 0.7, 2), '.2f')).rjust(6, ' ')}\n")
        polished_output.close()
    print(sorted(every_grade))
    print(round(total_average_grade/total_average_count, 1))