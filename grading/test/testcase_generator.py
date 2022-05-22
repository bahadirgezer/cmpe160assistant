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
        self.clusterIDs = {}
        self.fuel_cost = fuel_cost
        self.operation_fee = operation_fee
        self.aircraft_capacity = aircraft_capacity
    
    def __str__(self):
        return "Airport: " + self.type + " " + self.ID + " " + str(self.x) + " " + str(self.y) + " "

def generate_airport_points(count, x = [], y = []):
    for i in range(count):
        while(True):
            alpha = random.randint(0, 360)
            r = random.randint(50, 3500)
            x_curr = r * math.cos(alpha)
            y_curr = r * math.sin(alpha)
            point_ok = True
            for i in range(len(x)):
                if (x[i] - x_curr)**2 + (y[i] - y_curr)**2 < 400**2:
                    point_ok = False
                    break
            if point_ok:
                x.append(x_curr)
                y.append(y_curr)
                break
    return

def assign_random_airports(x, y, M):
    airports = []
    airport_IDs = set()

    for i in range(len(x)):
        type = np.random.choice([0, 1, 2], p=[0.5, 0.3, 0.2])
        
        #aircraft per airport
        apa = M // len(x) + 1 
        if (type == 0):
            aircraft_capacity = random.randint(apa, apa * 2)
            operation_fee = (random.randint(500, 1000)) * 200
            fuel_cost = random.randint(600, 1000) / 100 * 11
        elif (type == 1):
            aircraft_capacity = random.randint(apa * 1.5 // 1, apa * 3)
            operation_fee = (random.randint(500, 1000)) * 250
            fuel_cost = random.randint(400, 700) / 100 * 11
        elif (type == 2):
            aircraft_capacity = random.randint(apa * 2, apa * 5)
            operation_fee = (random.randint(500, 1000)) * 300
            fuel_cost = random.randint(300, 600) / 100 * 11

        airportID = random.randint(100000, 999999)
        while airportID in airport_IDs:
            airportID = random.randint(100000, 999999)
        
        airport_IDs.add(airportID)
        #cluster IDs are an empty set
        airports.append(Airport(type, airportID, x[i], y[i], fuel_cost, operation_fee, aircraft_capacity))
    return airports


def generate_destinations(airports):
    destinations = []
    selected_indices = set()
    #for i in range(random.randint(2, 4)): #number of airports must not be smaller than 4
    for i in range(2):
        index = random.randint(0, len(airports) - 1)
        while index in selected_indices:
            index = random.randint(0, len(airports) - 1)
        selected_indices.add(index)
        destinations.append(airports[index].ID)
    return destinations

def generate_passengers(count, airports):
    passengers = []
    passenger_IDs = set()
    for i in range(count):
        type = np.random.choice([0, 1, 2, 3], p=[0.7, 0.2, 0.07, 0.03])

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
        destinations = generate_destinations(airports)
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
    for airport in airports:
        if airport.type == 0:
            file.write("regional : " + str(airport.ID) + ", " + str(round(airport.x)) + ", " + str(round(airport.y)) + ", " + str(round(airport.fuel_cost, 3)) + ", " + str(round(airport.operation_fee, 3)) + ", " + str(airport.aircraft_capacity) + "\n")
        elif airport.type == 1:
            file.write("major : " + str(airport.ID) + ", " + str(round(airport.x)) + ", " + str(round(airport.y)) + ", " + str(round(airport.fuel_cost, 3)) + ", " + str(round(airport.operation_fee, 3)) + ", " + str(airport.aircraft_capacity) + "\n")
        elif airport.type == 2:
            file.write("hub : " + str(airport.ID) + ", " + str(round(airport.x)) + ", " + str(round(airport.y)) + ", " + str(round(airport.fuel_cost, 3)) + ", " + str(round(airport.operation_fee, 3)) + ", " + str(airport.aircraft_capacity) + "\n")

for testcase in range(0, 10):
    M = random.randint(1, 20)
    A = 10 #random.randint(25, 35)
    P = random.randint(700, 3000)

    prop = (random.randint(500, 700)) * 2 * 9
    widebody = (random.randint(500, 700)) * 2.5 * 9
    rapid = (random.randint(500, 700)) * 3 * 9
    jet = (random.randint(500, 700)) * 4 * 9

    x = []
    y = []
    generate_airport_points(A, x, y)
    airports = assign_random_airports(x, y, M)
    passengers = generate_passengers(P, airports)

    #"""
    plt.scatter(x, y)
    plt.axis("equal")
    plt.show()
    #"""

    file_name = "testcases/input{}.txt".format(testcase)
    file = open(file_name, "w")
    file.write("{} {} {}\n".format(M, A, P))
    file.write("{} {} {} {}\n".format(round(prop, 3), round(widebody, 3), round(rapid, 3), round(jet, 3)))
    write_airports(airports, file)
    write_passengers(passengers, file)

    file.close()
