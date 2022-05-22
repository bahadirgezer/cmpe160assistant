import random
import math
import numpy as np
import matplotlib.pyplot as plt


class Passenger:
    def __init__(self, ID, type, weight, baggage_count, destinations):
        self.type = type
        self.ID = ID
        self.weight = weight
        self.baggage_count = baggage_count
        self.destinations = destinations
    
class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y

class Airport:
    def __init__(self, type, ID, x, y, fuel_cost, operation_fee, aircraft_capacity):
        self.type = type
        self.ID = ID
        self.x = x
        self.y = y
        self.clusterIDs = set()
        self.fuel_cost = fuel_cost
        self.operation_fee = operation_fee
        self.aircraft_capacity = aircraft_capacity
    
    def __str__(self):
        return "Airport: " + self.type + " " + self.ID + " " + str(self.x) + " " + str(self.y) + " "

def generate_hub_points(count, x = [], y = []):
    d = random.randint(10000, 14000)
    alpha_prev = random.randint(0, 360)
    x_prev = d * math.cos(alpha_prev)
    y_prev = d * math.sin(alpha_prev)
    x.append(x_prev)
    y.append(y_prev)

    for i in range(count-1):
        while(True):
            alpha = alpha_prev + random.randint(-15, 15)
            d = random.randint(10000, 14000)
            x_curr = x_prev + d * math.cos(alpha)
            y_curr = y_prev + d * math.sin(alpha)

            connected = False
            point_ok = True
            for i in range(len(x)):
                euc_distance = ((x[i] - x_curr)**2 + (y[i] - y_curr)**2) ** 0.5
                if euc_distance < 10000:
                    point_ok = False
                    break
                if euc_distance < 14000 and not connected:
                    connected = True

            if point_ok and connected:
                x.append(x_curr)
                y.append(y_curr)
                x_prev = x_curr
                y_prev = y_curr
                alpha_prev = alpha
                break
    return

def generate_hub_and_major_points(count, x_hub = [], y_hub = [], x_major = [], y_major = []):
    d = random.randint(10000, 14000)
    alpha_prev = random.randint(0, 360)
    x_prev = d * math.cos(alpha_prev)
    y_prev = d * math.sin(alpha_prev)
    x_hub.append(x_prev)
    y_hub.append(y_prev)

    for i in range(count-1):
        if i % 5 == 0:
            print("Generating hub point: " + str(i))

        try_count_1 = 0

        if np.random.choice([True, False], p=[0.3, 0.7,]):
            random_index = random.randint(0, len(x_hub)-1)
            x_prev = x_hub[random_index]
            y_prev = y_hub[random_index]
            alpha_prev = random.randint(0, 360)

        while(True):
            try_count_1 += 1
            if try_count_1%5000==0:
                x_prev = x_hub[try_count_1//5000]
                y_prev = y_hub[try_count_1//5000]
                alpha = random.randint(0, 360)
                
            alpha = alpha_prev + random.randint(-15, 15)
            d = random.randint(10000, 14000)
            x_curr = x_prev + d * math.cos(alpha)
            y_curr = y_prev + d * math.sin(alpha)

            connected = False
            point_ok = True
            for ij in range(len(x_hub)):
                euc_distance = ((x_hub[ij] - x_curr)**2 + (y_hub[ij] - y_curr)**2) ** 0.5
                if euc_distance < 10000:
                    point_ok = False
                    break
                if euc_distance < 14000 and not connected:
                    connected = True

            if (point_ok and connected) or len(x_hub) == 0:
                alpha_prev_prev = alpha_prev
                x_hub.append(x_curr)
                y_hub.append(y_curr)
                x_prev = x_curr
                y_prev = y_curr
                alpha_prev = alpha

                for ijk in range(random.randint(0, 3)):
                    try_count = 0
                    while(True):
                        try_count += 1
                        if try_count > 5000:
                            break

                        if (random.randint(0, 1) == 0):
                            alpha_major = (alpha_prev_prev) + random.randint(60 ,120)
                        else:
                            alpha_major = (alpha_prev_prev) - random.randint(60 ,120)

                        d = random.randint(5000, 10000)
                        x_major_curr = x_curr + d * math.cos(alpha_major)
                        y_major_curr = y_curr + d * math.sin(alpha_major)

                        connected = False
                        point_ok = True
                        for ijkl in range(len(x_hub)):
                            euc_distance = ((x_hub[ijkl] - x_major_curr)**2 + (y_hub[ijkl] - y_major_curr)**2) ** 0.5
                            if euc_distance < 5000:
                                point_ok = False
                                break
                            if euc_distance < 14000 and not connected:
                                connected = True

                        if point_ok and connected:
                            connected = False
                            point_ok = True

                            for ijklm in range(len(x_major)):
                                euc_distance = ((x_major[ijklm] - x_major_curr)**2 + (y_major[ijklm] - y_major_curr)**2) ** 0.5
                                if euc_distance < 5000:
                                    point_ok = False
                                    break
                                if euc_distance < 14000 and not connected:
                                    connected = True  

                            if (point_ok and connected) or len(x_major) == 0: 
                                x_major.append(x_major_curr)
                                y_major.append(y_major_curr)
                                break
                break
    return

        
def generate_regional_airports(airports, x_regional, y_regional, M):
    apa = M // (len(airports) * 3) + 1
    type = 0

    airport_IDs = set(airports.keys())
    airport_objects = list(airports.values())
    for  airport in airport_objects:
        for i in range(random.randint(1, 5)):
            alpha = random.randint(0, 360)
            d = random.randint(400, 4000)
            x_curr = airport.x + d * math.cos(alpha)
            y_curr = airport.y + d * math.sin(alpha)
            
            connected = True
            point_ok = True
            for ij in range(len(x_regional)):
                euc_distance = ((x_regional[ij] - x_curr)**2 + (y_regional[ij] - y_curr)**2) ** 0.5
                if euc_distance < 1000:
                    point_ok = False
                    break

            if (point_ok and connected) or (len(x_regional) == 0):
                x_regional.append(x_curr)
                y_regional.append(y_curr)

                aircraft_capacity = random.randint(apa, apa * 2)
                operation_fee = (random.randint(500, 1000)) * 200
                fuel_cost = random.randint(600, 1000) / 100 * 11
                regional_airportID = random.randint(100000, 999999)
                while regional_airportID in airport_IDs:
                    regional_airportID = random.randint(100000, 999999)
                airport_IDs.add(regional_airportID)
                airports.update({regional_airportID : Airport(type, regional_airportID, x_curr, y_curr, fuel_cost, operation_fee, aircraft_capacity)})
                airport.clusterIDs.add(regional_airportID)

                continue                
    return
    
def assign_hub_and_major_airports(x_hub, y_hub, x_major, y_major, M):
    airports = dict()
    airport_IDs = set()
    apa = M // len(x_hub + x_major) + 1

    type = 2
    for i in range(len(x_hub)): #type 2
        aircraft_capacity = random.randint(apa * 2, apa * 5)
        operation_fee = (random.randint(500, 1000)) * 300
        fuel_cost = random.randint(300, 600) / 100 * 11
        airportID = random.randint(100000, 999999)
        while airportID in airport_IDs:
            airportID = random.randint(100000, 999999)
        airport_IDs.add(airportID)
        airports.update({airportID : Airport(type, airportID, x_hub[i], y_hub[i], fuel_cost, operation_fee, aircraft_capacity)})

    type = 1
    for i in range(len(x_major)):
        aircraft_capacity = random.randint(apa * 1.5 // 1, apa * 3)
        operation_fee = (random.randint(500, 1000)) * 250
        fuel_cost = random.randint(400, 700) / 100 * 11
        airportID = random.randint(100000, 999999)
        while airportID in airport_IDs:
            airportID = random.randint(100000, 999999)
        airport_IDs.add(airportID)
        airports.update({airportID : Airport(type, airportID, x_major[i], y_major[i], fuel_cost, operation_fee, aircraft_capacity)})
        
    return airports

def generate_destinations_hub2hub(airports, destination_count):
    destinations = []
    selected_indices = set()
    airport_keys = list(airports.keys())
    for i in range(destination_count):
        index = random.randint(0, len(airport_keys) - 1)
        while index in selected_indices or airports.get(airport_keys[index]).type != 2: 
            index = random.randint(0, len(airport_keys) -1)
        selected_indices.add(index)
        destinations.append(airport_keys[index])
    return destinations

def generate_destinations_not_regional(airports, destination_count):
    destinations = []
    selected_indices = set()
    airport_keys = list(airports.keys())
    for i in range(destination_count):
        index = random.randint(0, len(airport_keys) - 1)
        while index in selected_indices or airports.get(airport_keys[index]).type == 0:
            index = random.randint(0, len(airport_keys) -1)
        selected_indices.add(index)
        destinations.append(airport_keys[index])
    return destinations

def find_last_hub_key(airports):
    index = len(airports) - 1
    airport_keys = list(airports.keys())
    found = False
    while not found:
        if airports.get(airport_keys[index]).type == 2:
            found = True
            break
        index -= 1
    return airport_keys[index]

def find_first_hub_key(airports):
    index = 0
    airport_keys = list(airports.keys())
    found = False
    while not found:
        if airports.get(airport_keys[index]).type == 2:
            found = True
            break
        index += 1
    return airport_keys[index]


def generate_cluster_destinations(airports, destination_count):
    destinations = []
    selected_indices = set()
    airport_keys = list(airports.keys())

    index = random.randint(0, len(airport_keys) - 1)
    first_airport_list = list(airports.get(airport_keys[index]).clusterIDs)
    while index in selected_indices or airports.get(airport_keys[index]).type == 0 or len(first_airport_list) == 0:
        index = random.randint(0, len(airport_keys) -1)
        first_airport_list = list(airports.get(airport_keys[index]).clusterIDs)
    selected_indices.add(index)    
    first_regional_key = first_airport_list[0]

    destinations.append(first_regional_key)
    destinations.append(airport_keys[index])

    for i in range(destination_count - 4):
        index = random.randint(0, len(airport_keys) - 1)
        while index in selected_indices or airports.get(airport_keys[index]).type == 0:
            index = random.randint(0, len(airport_keys) -1)
        selected_indices.add(index)
        destinations.append(airport_keys[index])


    index = random.randint(0, len(airport_keys) - 1)
    first_airport_list = list(airports.get(airport_keys[index]).clusterIDs)
    while index in selected_indices or airports.get(airport_keys[index]).type == 0 or len(first_airport_list) == 0:
        index = random.randint(0, len(airport_keys) -1)
        first_airport_list = list(airports.get(airport_keys[index]).clusterIDs)
    selected_indices.add(index)    
    first_regional_key = first_airport_list[0]

    destinations.append(airport_keys[index])
    destinations.append(first_regional_key)

    return destinations

def generate_passengers_special(count, airports):
    passengers = []
    passenger_IDs = set()

    index_1 = find_first_hub_key(airports)
    index_2 = find_last_hub_key(airports)

    for i in range(count):
        type = np.random.choice([0, 1, 2, 3], p=[0.7, 0.29, 0.009, 0.001])

        if type == 0:
            pass
        elif type == 1:
            pass
        elif type == 2:
            pass
        elif type == 3:
            pass

        passenger_ID = random.randint(math.pow(10, 9), math.pow(10, 10)-1)
        while passenger_ID in passenger_IDs:
            passenger_ID = random.randint(math.pow(10, 9), math.pow(10, 10)-1)
        passenger_IDs.add(passenger_ID)
        weight = random.randint(40, 200)
        baggage_count = np.random.choice([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10], p=[0.15, 0.3, 0.25, 0.1, 0.05, 0.04, 0.03, 0.02, 0.02, 0.02, 0.02])
        destinations = [index_1, index_2]
        passengers.append(Passenger(passenger_ID, type, weight, baggage_count, destinations))
    return passengers 

def generate_passengers_cluster(count, airports):
    passengers = []
    passenger_IDs = set()

    for i in range(count):
        type = np.random.choice([0, 1, 2, 3], p=[0.7, 0.29, 0.009, 0.001])

        if type == 0:
            pass
        elif type == 1:
            pass
        elif type == 2:
            pass
        elif type == 3:
            pass

        passenger_ID = random.randint(math.pow(10, 9), math.pow(10, 10)-1)
        while passenger_ID in passenger_IDs:
            passenger_ID = random.randint(math.pow(10, 9), math.pow(10, 10)-1)
        passenger_IDs.add(passenger_ID)
        weight = random.randint(40, 200)
        baggage_count = np.random.choice([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10], p=[0.15, 0.3, 0.25, 0.1, 0.05, 0.04, 0.03, 0.02, 0.02, 0.02, 0.02])
        destinations = generate_cluster_destinations(airports, random.randint(4, 30))
        passengers.append(Passenger(passenger_ID, type, weight, baggage_count, destinations))
    return passengers 


def generate_passengers_not_regional(count, airports):
    passengers = []
    passenger_IDs = set()
    for i in range(count):
        type = np.random.choice([0, 1, 2, 3], p=[0.7, 0.29, 0.009, 0.001])

        if type == 0:
            pass
        elif type == 1:
            pass
        elif type == 2:
            pass
        elif type == 3:
            pass

        passenger_ID = random.randint(math.pow(10, 9), math.pow(10, 10)-1)
        while passenger_ID in passenger_IDs:
            passenger_ID = random.randint(math.pow(10, 9), math.pow(10, 10)-1)
        passenger_IDs.add(passenger_ID)
        weight = random.randint(40, 200)
        baggage_count = np.random.choice([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10], p=[0.15, 0.3, 0.25, 0.1, 0.05, 0.04, 0.03, 0.02, 0.02, 0.02, 0.02])
        destinations = generate_destinations_not_regional(airports, random.randint(2, 20))
        passengers.append(Passenger(passenger_ID, type, weight, baggage_count, destinations))
    return passengers  

def generate_passengers_hub2hub(count, airports):
    passengers = []
    passenger_IDs = set()
    for i in range(count):
        type = np.random.choice([0, 1, 2, 3], p=[0.7, 0.29, 0.009, 0.001])

        if type == 0:
            pass
        elif type == 1:
            pass
        elif type == 2:
            pass
        elif type == 3:
            pass

        passenger_ID = random.randint(math.pow(10, 9), math.pow(10, 10)-1)
        while passenger_ID in passenger_IDs:
            passenger_ID = random.randint(math.pow(10, 9), math.pow(10, 10)-1)
        passenger_IDs.add(passenger_ID)
        weight = random.randint(40, 200)
        baggage_count = np.random.choice([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10], p=[0.15, 0.3, 0.25, 0.1, 0.05, 0.04, 0.03, 0.02, 0.02, 0.02, 0.02])
        destinations = generate_destinations_hub2hub(airports, random.randint(2, 20))
        passengers.append(Passenger(passenger_ID, type, weight, baggage_count, destinations))
    return passengers

def write_passengers(passengers, file):
    for passenger in passengers:
        if passenger.type == 0:
            file.write("economy : " + str(passenger.ID) + ", " + str(passenger.weight) + ", " + str(passenger.baggage_count) + ", " + str(passenger.destinations) + "\n")
        elif passenger.type == 1:
            file.write("business : " + str(passenger.ID) + ", " + str(passenger.weight) + ", " + str(passenger.baggage_count) + ", " + str(passenger.destinations) + "\n")
        elif passenger.type == 2:
            file.write("first : " + str(passenger.ID) + ", " + str(passenger.weight) + ", " + str(passenger.baggage_count) + ", " + str(passenger.destinations) + "\n")
        elif passenger.type == 3:
            file.write("luxury : " + str(passenger.ID) + ", " + str(passenger.weight) + ", " + str(passenger.baggage_count) + ", " + str(passenger.destinations) + "\n")

def write_airports(airports, file):
    for airport in airports.values():
        if airport.type == 0:
            file.write("regional : " + str(airport.ID) + ", " + str(round(airport.x)) + ", " + str(round(airport.y)) + ", " + str(round(airport.fuel_cost, 3)) + ", " + str(round(airport.operation_fee, 3)) + ", " + str(airport.aircraft_capacity) + "\n")
        elif airport.type == 1:
            file.write("major : " + str(airport.ID) + ", " + str(round(airport.x)) + ", " + str(round(airport.y)) + ", " + str(round(airport.fuel_cost, 3)) + ", " + str(round(airport.operation_fee, 3)) + ", " + str(airport.aircraft_capacity) + "\n")
        elif airport.type == 2:
            file.write("hub : " + str(airport.ID) + ", " + str(round(airport.x)) + ", " + str(round(airport.y)) + ", " + str(round(airport.fuel_cost, 3)) + ", " + str(round(airport.operation_fee, 3)) + ", " + str(airport.aircraft_capacity) + "\n")


def draw_connectivity(x, y, rang, plt):
    for i in range(len(x)):
        circ = plt.Circle((x[i], y[i]), rang, color='g', fill=True)
        plt.gca().add_artist(circ)
        
    plt.scatter(x, y, c='black')
    plt.axis("equal")
    plt.show()
    return

def draw_connectivity(x_hub, y_hub, x_major, y_major, plt):
    for i in range(len(x_hub)):
        circ = plt.Circle((x_hub[i], y_hub[i]), 7000, color='g', fill=True)
        plt.gca().add_artist(circ)
        
    for i in range(len(x_major)):
        circ = plt.Circle((x_major[i], y_major[i]), 5000, color='b', fill=True)
        plt.gca().add_artist(circ)

    plt.scatter(x_hub, y_hub, c='black')
    plt.scatter(x_major, y_major, c='black')

    plt.axis("equal")
    plt.show()
    return

def draw_connectivity(x_hub, y_hub, x_major, y_major, x_regional, y_regional, plt):
    for i in range(len(x_hub)):
        circ = plt.Circle((x_hub[i], y_hub[i]), 7000, color='g', fill=True)
        plt.gca().add_artist(circ)
        
    for i in range(len(x_major)):
        circ = plt.Circle((x_major[i], y_major[i]), 5000, color='b', fill=True)
        plt.gca().add_artist(circ)
    
    for i in range(len(x_regional)):
        circ = plt.Circle((x_regional[i], y_regional[i]), 1500, color='r', fill=True)
        plt.gca().add_artist(circ)
        

    plt.scatter(x_hub, y_hub, c='black')
    plt.scatter(x_major, y_major, c='black')
    #plt.scatter(x_regional, y_regional, c='black')

    plt.axis("equal")
    plt.show()
    return


def plot(x, y, plt):
    plt.scatter(x, y)
    plt.axis("equal")
    plt.show()

for testcase in range(0, 10):
    M = random.randint(1, 5)
    A = random.randint(100, 150) #random.randint(40, 60) #initial hub count
    P = random.randint(10000, 20000)

    prop = (random.randint(500, 700)) * 2 * 9
    widebody = (random.randint(500, 700)) * 1 * 9
    rapid = (random.randint(500, 700)) * 2 * 9
    jet = (random.randint(500, 700)) * 20 * 9
    operational_cost = (random.randint(500, 700)) * 8

    x_hub = []
    y_hub = []
    # generate_hub_points(A, x_hub, y_hub)
    x_major = []
    y_major = []
    print("1")
    generate_hub_and_major_points(A, x_hub, y_hub, x_major, y_major)
    #draw_connectivity(x_hub, y_hub, x_major, y_major, plt)
    print("2")
    airports = assign_hub_and_major_airports(x_hub, y_hub, x_major, y_major, M)
    x_regional = []
    y_regional = []
    print("3")
    generate_regional_airports(airports, x_regional, y_regional, M)
    draw_connectivity(x_hub, y_hub, x_major, y_major, x_regional, y_regional, plt)
    print("4", str(A))
    A = len(airports)

    print("5", str(P))
    #passengers = generate_passengers_not_regional(P, airports)
    #passengers = generate_passengers_special(P, airports)
    passengers = generate_passengers_cluster(P, airports)

    print("6")
    file_name = "testcases/input{}.txt".format(testcase)
    file = open(file_name, "w")
    file.write("{} {} {}\n".format(M, A, P))
    file.write("{} {} {} {} {}\n".format(round(prop, 3), round(widebody, 3), round(rapid, 3), round(jet, 3), round(operational_cost, 3)))
    write_airports(airports, file)
    print("7")
    write_passengers(passengers, file)
    print("8")

    file.close()
