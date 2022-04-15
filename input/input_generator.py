import random

input_file = open("input.txt", 'w')

aircraft_count = random.randint(1, 6)
input_file.write(aircraft_count + '\n')

