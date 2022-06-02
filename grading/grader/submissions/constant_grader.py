import os
import re

from matplotlib.pyplot import contour

def check_javadoc(airline_file):
    # Check if the file has a javadoc comment
    javadoc = False # 4p
    param = False # 3p
    returns = False # 2p
    author = False # 1p

    with open(airline_file, 'r') as f:
        try:
            lines = f.readlines()
        except UnicodeDecodeError:
            print(airline_file)
            return 0, "File is not UTF-8 encoded"
        for line in lines:
            if re.search(r"/\*\*", line):
                javadoc = True
            if re.search("@param", line):
                param = True
            if re.search("@returns", line) or re.search("@return", line):
                returns = True
            if re.search("@author", line):
                author = True

    return (javadoc * 4) + (param * 3) + (returns * 2) + (author * 1), f"javadoc: {javadoc}, param keyword: {param}, returns keyword: {returns}, author: {author}"

def find_airline_file(files_list):
    # Find the airline file
    for file in files_list:
        if re.search('airline', file, re.IGNORECASE) and re.search('java', file):
            return file
    return None

if __name__ == "__main__":
    users_dir = os.getcwd()+f"/submitted-graded"
    users = [item for item in os.listdir(users_dir) if os.path.isdir(os.path.join(users_dir, item))]

    #javadoc
    grades_dir = os.getcwd()+f"/grades/constant_grades/javadoc-grades"
    if not os.path.exists(grades_dir):
        os.mkdir(grades_dir)
    javadoc_grades = []
    for user in users:
        found = False
        for root, dirs, files in os.walk(users_dir + f"/{user}"):
            airline_path = find_airline_file(files)
            if airline_path:
                airline_path = os.path.join(root, airline_path)
                grade_file = open(f"{grades_dir}/{user}_javadoc.txt", "w")
                grade, comment = check_javadoc(airline_path)
                javadoc_grades.append(grade)
                grade_file.write(str(grade) + "\n")
                grade_file.write(comment+"\n")
                grade_file.write("javadoc: 5p, param keyword: 3p, returns keyword: 2p, author: 1p")
                grade_file.write(f"\n")
                grade_file.close()
                found = True
                break

        if not found:
            grade_file = open(f"{grades_dir}/{user}_javadoc.txt", "w")
            grade_file.write("0\n")
            grade_file.write("No airline file found\n")
            grade_file.write("javadoc: 5p, param keyword: 3p, returns keyword: 2p, author: 1p")
            javadoc_grades.append(0)
            grade_file.close()

    '''
    src: project:
    airline container: Airline.java
    aircraft container: Aircraft.java
    Abstract class, implements AircraftInterface PassengerAircraft.java
    Abstract class, extends Aircraft implements PassengerInterface concrete container:
        airport container: Airport.java
    JetPassengerAircraft.java Extends PassengerAircraft
    RapidPassengerAircraft.java Extends PassengerAircraft
    WidebodyPassengerAircraft.java Extends PassengerAircraft
    PropPassengerAircraft.java Extends PassengerAircraft
    Abstract class HubAirport.java
    Extends Airport MajorAirport.java
    Extends Airport RegionalAirport.java
    Extends Airport passenger container:
    2
    Passenger.java Abstract class
    EconomyPassenger.java Extends Passenger
    BusinessPassenger.java Extends Passenger
    FirstClassPassenger.java Extends Passenger
    LuxuryPassenger.java Extends Passenger
    interfaces container: AircraftInterface.java
    PassengerInterface.java executable container:
    Main.java
    '''

    #class structure
    grades_dir = os.getcwd()+f"/grades/constant_grades/class-structure-grades"
    if not os.path.exists(grades_dir):
        os.mkdir(grades_dir)
    class_structure_grades = []
    for user in users:
        airline = False
        aircraft = False
        passenger_aircraft = False
        jet = False
        rapid = False
        widebody = False
        prop = False
        airport = False
        hub = False
        major = False
        regional = False
        passenger = False
        economy = False
        business = False
        first = False
        luxury = False
        aircraft_interface = False
        passenger_interface = False
        main = False

        for root, dirs, files in os.walk(users_dir + f"/{user}"):
            for file in files:
                if re.search('airline', file, re.IGNORECASE) and re.search('java', file) and not airline and re.search('airline', root, re.IGNORECASE):
                    airline = True
                    continue
                if re.search('aircraft', file, re.IGNORECASE) and re.search('java', file) and not re.search("passenger", file, re.IGNORECASE) and not re.search("interface", file, re.IGNORECASE) and not aircraft and re.search('airline', root, re.IGNORECASE) and re.search('aircraft', root, re.IGNORECASE):
                    aircraft = True
                    continue
                if re.search('aircraft', file, re.IGNORECASE) and re.search('java', file) and re.search("passenger", file, re.IGNORECASE) and not re.search("interface", file, re.IGNORECASE) and not passenger_aircraft and re.search('airline', root, re.IGNORECASE) and re.search('aircraft', root, re.IGNORECASE):
                    passenger_aircraft = True
                    continue
                if re.search('jet', file, re.IGNORECASE) and re.search('java', file) and not jet and re.search('airline', root, re.IGNORECASE) and re.search('aircraft', root, re.IGNORECASE) and re.search('concrete', root, re.IGNORECASE):
                    jet = True
                    continue
                if re.search('rapid', file, re.IGNORECASE) and re.search('java', file) and not rapid and re.search('airline', root, re.IGNORECASE) and re.search('aircraft', root, re.IGNORECASE) and re.search('concrete', root, re.IGNORECASE):
                    rapid = True
                    continue
                if re.search('widebody', file, re.IGNORECASE) and re.search('java', file) and not widebody and re.search('airline', root, re.IGNORECASE) and re.search('aircraft', root, re.IGNORECASE) and re.search('concrete', root, re.IGNORECASE):
                    widebody = True
                    continue
                if re.search('prop', file, re.IGNORECASE) and re.search('java', file) and not prop and re.search('airline', root, re.IGNORECASE) and re.search('aircraft', root, re.IGNORECASE) and re.search('concrete', root, re.IGNORECASE):
                    prop = True
                    continue
                if re.search('airport', file, re.IGNORECASE) and re.search('java', file) and not re.search("hub", file, re.IGNORECASE) and not re.search("major", file, re.IGNORECASE) and not re.search("regional", file, re.IGNORECASE) and not airport and re.search('airport', root, re.IGNORECASE):
                    airport = True
                    continue
                if re.search('hub', file, re.IGNORECASE) and re.search('java', file) and not hub and re.search('airport', root, re.IGNORECASE):
                    hub = True
                    continue
                if re.search('major', file, re.IGNORECASE) and re.search('java', file) and not major and re.search('airport', root, re.IGNORECASE):
                    major = True
                    continue
                if re.search('regional', file, re.IGNORECASE) and re.search('java', file) and not regional and re.search('airport', root, re.IGNORECASE):
                    regional = True
                    continue
                if re.search('passenger', file, re.IGNORECASE) and re.search('java', file) and not re.search("interface", file, re.IGNORECASE) and not re.search("aircraft", file, re.IGNORECASE) and not passenger and re.search('passenger', root, re.IGNORECASE):
                    passenger = True
                    continue
                if re.search('economy', file, re.IGNORECASE) and re.search('java', file) and not economy and re.search('passenger', root, re.IGNORECASE):
                    economy = True
                    continue    
                if re.search('business', file, re.IGNORECASE) and re.search('java', file) and not business and re.search('passenger', root, re.IGNORECASE):
                    business = True
                    continue
                if re.search('first', file, re.IGNORECASE) and re.search('java', file) and not first and re.search('passenger', root, re.IGNORECASE):
                    first = True
                    continue
                if re.search('luxury', file, re.IGNORECASE) and re.search('java', file) and not luxury and re.search('passenger', root, re.IGNORECASE):
                    luxury = True
                    continue
                if re.search('aircraft', file, re.IGNORECASE) and re.search('interface', file, re.IGNORECASE) and re.search('java', file) and not aircraft_interface and re.search('interface', root, re.IGNORECASE):
                    aircraft_interface = True
                    continue
                if re.search('passenger', file, re.IGNORECASE) and re.search('interface', file, re.IGNORECASE) and re.search('java', file) and not passenger_interface and re.search('interface', root, re.IGNORECASE):
                    passenger_interface = True
                    continue
                if re.search('main', file, re.IGNORECASE) and re.search('java', file) and not main and re.search('executable', root, re.IGNORECASE):
                    main = True
                    continue

        grade_file = open(f"{grades_dir}/{user}_structure.txt", "w")
        grade = (airline) + (aircraft) + (passenger_aircraft) + (jet) + (rapid) + (widebody) + (prop) + (airport) + (hub) + (major) + (regional) + (passenger) + (economy) + (business) + (first) + (luxury) + (aircraft_interface) + (passenger_interface) + (main) + 1
        grade = grade / 2
        if grade == 9.5:
            grade += 0.5
            business = True
        class_structure_grades.append(grade)

        grade_file.write(str(grade) + "\n")
        grade_file.write(f"airline: {airline}, passenger_aircraft: {passenger_aircraft}, jet: {jet}, rapid: {rapid}, widebody: {widebody}, prop: {prop}, airport: {airport}, hub: {hub}, major: {major}, regional: {regional}, passenger: {passenger}, economy: {economy}, business: {business}, first: {first}, luxury: {luxury}, aircraft_interface: {aircraft_interface}, passenger_interface: {passenger_interface}, main: {main}\n")
        grade_file.write("class structure: airline, aircraft, passenger_aircraft, jet, rapid, widebody, prop, airport, hub, major, regional, passenger, economy, business, first, luxury, aircraft_interface, passenger_interface, main plus a bonus. Each is worth 0.5p\n")
        grade_file.write(f"\n")
        grade_file.close()
        

    #compilation without errors
    grades_dir = os.getcwd()+f"/grades/constant_grades/compile-grades"
    if not os.path.exists(grades_dir):
        os.mkdir(grades_dir)
    compile_grades = []
    for user in users:
        airline = False
        aircraft = False
        passenger_aircraft = False
        jet = False
        rapid = False
        widebody = False
        prop = False
        airport = False
        hub = False
        major = False
        regional = False
        passenger = False
        economy = False
        business = False
        first = False
        luxury = False
        aircraft_interface = False
        passenger_interface = False
        main = False

        for root, dirs, files in os.walk(users_dir + f"/{user}"):
            for file in files:
                if re.search('airline', file, re.IGNORECASE) and re.search('class', file) and not airline:
                    airline = True
                    continue
                if re.search('aircraft', file, re.IGNORECASE) and re.search('class', file) and not re.search("passenger", file, re.IGNORECASE) and not re.search("interface", file, re.IGNORECASE) and not aircraft:
                    aircraft = True
                    continue
                if re.search('aircraft', file, re.IGNORECASE) and re.search('class', file) and re.search("passenger", file, re.IGNORECASE) and not re.search("interface", file, re.IGNORECASE) and not passenger_aircraft:
                    passenger_aircraft = True
                    continue
                if re.search('jet', file, re.IGNORECASE) and re.search('class', file) and not jet:
                    jet = True
                    continue
                if re.search('rapid', file, re.IGNORECASE) and re.search('class', file) and not rapid:
                    rapid = True
                    continue
                if re.search('widebody', file, re.IGNORECASE) and re.search('class', file) and not widebody:
                    widebody = True
                    continue
                if re.search('prop', file, re.IGNORECASE) and re.search('class', file) and not prop:
                    prop = True
                    continue
                if re.search('airport', file, re.IGNORECASE) and re.search('class', file) and not re.search("hub", file, re.IGNORECASE) and not re.search("major", file, re.IGNORECASE) and not re.search("regional", file, re.IGNORECASE) and not airport:
                    airport = True
                    continue
                if re.search('hub', file, re.IGNORECASE) and re.search('class', file) and not hub:
                    hub = True
                    continue
                if re.search('major', file, re.IGNORECASE) and re.search('class', file) and not major:
                    major = True
                    continue
                if re.search('regional', file, re.IGNORECASE) and re.search('class', file) and not regional:
                    regional = True
                    continue
                if re.search('passenger', file, re.IGNORECASE) and re.search('class', file) and not re.search("interface", file, re.IGNORECASE) and not re.search("aircraft", file, re.IGNORECASE) and not passenger:
                    passenger = True
                    continue
                if re.search('economy', file, re.IGNORECASE) and re.search('class', file) and not economy:
                    economy = True
                    continue    
                if re.search('business', file, re.IGNORECASE) and re.search('class', file) and not business:        
                    business = True
                    continue
                if re.search('first', file, re.IGNORECASE) and re.search('class', file) and not first:
                    first = True
                    continue
                if re.search('luxury', file, re.IGNORECASE) and re.search('class', file) and not luxury:
                    luxury = True
                    continue
                if re.search('aircraft', file, re.IGNORECASE) and re.search('interface', file, re.IGNORECASE) and re.search('class', file) and not aircraft_interface:
                    aircraft_interface = True
                    continue
                if re.search('passenger', file, re.IGNORECASE) and re.search('interface', file, re.IGNORECASE) and re.search('class', file) and not passenger_interface:
                    passenger_interface = True
                    continue
                if re.search('main', file, re.IGNORECASE) and re.search('class', file) and not main:
                    main = True
                    continue

        grade_file = open(f"{grades_dir}/{user}_compile.txt", "w")
        grade = (airline) + (aircraft) + (passenger_aircraft) + (jet) + (rapid) + (widebody) + (prop) + (airport) + (hub) + (major) + (regional) + (passenger) + (economy) + (business) + (first) + (luxury) + (aircraft_interface) + (passenger_interface) + (main) + 1
        grade = grade / 2
        if grade == 9.5:
            grade += 0.5
            business = True
        compile_grades.append(grade)

        grade_file.write(str(grade) + "\n")
        grade_file.write(f"airline: {airline}, passenger_aircraft: {passenger_aircraft}, jet: {jet}, rapid: {rapid}, widebody: {widebody}, prop: {prop}, airport: {airport}, hub: {hub}, major: {major}, regional: {regional}, passenger: {passenger}, economy: {economy}, business: {business}, first: {first}, luxury: {luxury}, aircraft_interface: {aircraft_interface}, passenger_interface: {passenger_interface}, main: {main}\n")
        grade_file.write("compile: airline, aircraft, passenger_aircraft, jet, rapid, widebody, prop, airport, hub, major, regional, passenger, economy, business, first, luxury, aircraft_interface, passenger_interface, main plus a bonus. Each is worth 0.5p\n")
        grade_file.write(f"\n")
        grade_file.close()
        
    
    print("javadoc grades: " + str(sorted(javadoc_grades)) + "\n")
    print("class structure grades: " + str(sorted(class_structure_grades)) + "\n")
    print("class structure grades: " + str(sorted(compile_grades)) + "\n")

