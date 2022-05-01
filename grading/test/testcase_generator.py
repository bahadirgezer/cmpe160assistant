from locale import DAY_1
from operator import truediv
import random
from re import L
import numpy as np
import matplotlib.pyplot as plt
import math
import sys


class Passenger:
    def __init__(self, x, y):
        self.x = x
        self.y = y
class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y

class Airport:
    def __init__(self, type, ID, x, y, clusterIDs):
        self.type = type
        self.ID = ID
        self.x = x
        self.y = y
        self.clusterIDs = clusterIDs


def cleanup(xk, yk, distance):
    x_clean = []
    y_clean = []
    bad_indices = []
    list_lenght = len(xk)
    #double for loop to iterate over xk and yk
    for i in range(list_lenght):
        if (i % 50 == 0):
            print(f"cleanup: {i}")
        for j in range(list_lenght):
            #if the distance between two points is less than range, add the index of the point to bad_indices
            if (i == j):
                continue
            if ((xk[i] - xk[j])**2 + (yk[i] - yk[j])**2) < distance**2:
                bad_indices.append(i)
    #remove the bad indices from xk and yk
    for i in range(list_lenght):
        if i not in bad_indices:
            x_clean.append(xk[i])
            y_clean.append(yk[i])
    return  x_clean, y_clean

def get_hub_points_linear_loop(count, x=[], y=[]):
    point1 = Point(0,0)
    for i in range(count):
        if (i % 50 == 0):
            print(f"hub_points: {i}")

        alpha1 = random.randint(-5, 5)
        d1 = random.randint(10000, 14000)
        x1 = point1.x + d1 * math.cos(alpha1)
        y1 = point1.y + d1 * math.sin(alpha1)
        x.append(x1)
        y.append(y1)

        point1 = Point(x1, y1)
    return x, y

def get_hub_points_linear(count, point1, x=[], y=[]):
    if count == 0:
        return 
    #plot points x2, y2 that is d away from and shifted alpha degrees from x1, y1
    alpha1 = random.randint(-5, 5)
    d1 = random.randint(10000, 14000)
    x1 = point1.x + d1 * math.cos(alpha1)
    y1 = point1.y + d1 * math.sin(alpha1)
    x.append(x1)
    y.append(y1)

    # alpha2 = alpha1 + random.randint(-5, 5)
    # d2 = random.randint(10000, 14000)
    # x2 = x1 + d2 * math.cos(alpha2)
    # y2 = y1 + d2 * math.sin(alpha2)
    # x.append(x2)
    # y.append(y2)

    get_hub_points_linear(count-1, Point(x1, y1), x, y)
    return

def get_hub_points_original(count, point1, x=[], y=[]):
    if count == 0:
        return 
    #plot points x2, y2 that is d away from and shifted alpha degrees from x1, y1
    alpha1 = random.randint(0, 90)
    d1 = random.randint(10000, 14000)
    x1 = point1.x + d1 * math.cos(alpha1)
    y1 = point1.y + d1 * math.sin(alpha1)
    x.append(x1)
    y.append(y1)

    alpha2 = alpha1 + random.randint(-60, 60)
    d2 = random.randint(10000, 14000)
    x2 = x1 + d2 * math.cos(alpha2)
    y2 = y1 + d2 * math.sin(alpha2)
    x.append(x2)
    y.append(y2)

    get_hub_points_original(count-1, Point(x2, y2), x, y)
    return

def get_regional_points(x_hub, y_hub, x=[], y=[]):
    for i in range(len(x_hub)):
        if (i % 50 == 0):
            print(f"regional_points: {i}")

        for j in range(random.randint(2, 3)):
            alpha = random.randint(0 + 120 * j, 120 + 120 * j)
            d = random.randint(400, 5000)
            y1 = y_hub[i] + d * math.sin(alpha)
            x1 = x_hub[i] + d * math.cos(alpha)
            x.append(x1)
            y.append(y1)
    return 

def get_major_points_linear(x_hub, y_hub, x_major = [], y_major = []):
    for i in range(len(x_hub)):
        if (i % 50 == 0):
            print(f"major_points: {i}")
        
        for j in range(random.randint(2,4)):
            alpha = random.randint(0 + 90 * j, 90 + 90 * j)
            d = random.randint(5000, 10000)
            y1 = y_hub[i] + d * math.sin(alpha)
            x1 = x_hub[i] + d * math.cos(alpha)
            is_ok = True
            for i in range(len(x_hub)):
                x_hub_elem = x_hub[i]
                y_hub_elem = y_hub[i]
                #if distance between the point and the hub is less than the distance between the hub and the point, add the point to the list
                if (x1 - x_hub_elem)**2 + (y1 - y_hub_elem)**2 < 3000**2:
                    is_ok = False
                    break
            if is_ok:
                x_major.append(x1)
                y_major.append(y1)

    return

def get_major_points_original(x_hub, y_hub, x_major = [], y_major = []):
    for i in range(len(x_hub)):
        for j in range(i, len(x_hub)):
            if i == j:
                continue
            #get the angle between the two points
            alpha = math.atan2(y_hub[i] - y_hub[j], x_hub[i] - x_hub[j])
            #get the distance between the two points
            d = math.sqrt((y_hub[i] - y_hub[j])**2 + (x_hub[i] - x_hub[j])**2)
            alpha = alpha + alpha * random.randint(-10, 10) / 100
            d = d * random.randint(400, 600) / 1000

            x1 = x_hub[j] + d * math.cos(alpha)
            y1 = y_hub[j] + d * math.sin(alpha)

            is_ok = True
            for i in range(len(x_hub)):
                x_hub_elem = x_hub[i]
                y_hub_elem = y_hub[i]
                #if distance between the point and the hub is less than the distance between the hub and the point, add the point to the list
                if (x1 - x_hub_elem)**2 + (y1 - y_hub_elem)**2 < 2500**2:
                    is_ok = False
                    break
            if is_ok:
                x_major.append(x1)
                y_major.append(y1)
    return


def convert_to_airports(type, list_x, list_y, airportIDs, airports = [], regionals = []):
    for i in range(len(list_x)):
        clusterIDs = find_cluster_IDs(list_x[i], list_y[i], regionals)
        airportID = random.randint(100000, 999999)
        while airportID in airportIDs:
            airportID = random.randint(100000, 999999)
        airportIDs.add(airportID)
        airports.append(Airport(type, airportID, list_x[i], list_y[i], clusterIDs))
    return airports

def find_cluster_IDs(x, y, regionals):
    clusterIDs = []
    for i in range(len(regionals)):
        if (x - regionals[i].x)**2 + (y - regionals[i].y)**2 < 2500**2:
            clusterIDs.append(regionals[i].ID)
    return clusterIDs

def draw_ranges(x, y, rang_low, rang_high, plt):
    for i in range(len(x)):
        circ = plt.Circle((x[i], y[i]), rang_low, color='r', fill=True, alpha = .05)
        plt.gca().add_artist(circ)
        circ = plt.Circle((x[i], y[i]), rang_high, color='g', fill=True, alpha = .3)
        plt.gca().add_artist(circ)
        
    plt.scatter(x, y, c='black')
    plt.axis("equal")
    plt.show()
    return

def draw_connectivity(x, y, rang, plt):
    for i in range(len(x)):
        circ = plt.Circle((x[i], y[i]), rang, color='g', fill=True)
        plt.gca().add_artist(circ)
        
    plt.scatter(x, y, c='black')
    plt.axis("equal")
    plt.show()
    return

#sys.setrecursionlimit(10**6)

for testcase in range(1, 2):
    print(f"generating airports for testcase: {testcase}")

    hub_count = 10000

    x_hub = []
    y_hub = []
    #get_hub_points_linear(hub_count, Point(0, 0), x_hub, y_hub)
    x_hub, y_hub = get_hub_points_linear_loop(hub_count, x_hub, y_hub)
    #x_hub, y_hub = cleanup(x_hub, y_hub, 4500)

    x_regional = []
    y_regional = []
    get_regional_points(x_hub, y_hub, x_regional, y_regional)
    #x_regional, y_regional = cleanup(x_regional, y_regional, 1000)

    x_major = []
    y_major = []
    get_major_points_linear(x_hub, y_hub, x_major, y_major)
    #x_major, y_major = cleanup(x_major, y_major, 3500)

    x_major_regional = []
    y_major_regional = []
    get_regional_points(x_major, y_major, x_major_regional, y_major_regional)
    #x_major_regional, y_major_regional = cleanup(x_major_regional, y_major_regional, 1000)

    x_regional = x_regional + x_major_regional
    y_regional = y_regional + y_major_regional
    

    # #plt.scatter(x_regional, y_regional, c='blue')
    # plt.scatter(x_hub, y_hub, c='red')
    # #plt.scatter(x_major, y_major, c='green')
    # plt.show()

    #plt.scatter(x_regional, y_regional, c='blue')
    
    #plt.scatter(x_hub, y_hub, c='red')
    #plt.scatter(x_major, y_major, c='green')
    #plt.axis("equal")
    #plt.show()

    # #plt.scatter(x_regional, y_regional, c='blue')
    # plt.scatter(x_hub, y_hub, c='red')
    # plt.scatter(x_major, y_major, c='green')
    # plt.axis("equal")
    # plt.show()

    #plt.scatter(x_regional, y_regional, c='blue')
    # plt.scatter(x_hub, y_hub, c='red')
    # plt.scatter(x_major, y_major, c='green')
    # plt.axis("equal")
    # plt.show()

    #draw_ranges(x_hub, y_hub, 10000, 14000, plt)
    #draw_ranges(x_hub + x_major, y_hub + y_major, 5000, 14000, plt)
    
    #draw_connectivity(x_hub + x_major + x_regional, y_hub + y_major + y_regional, 7000, plt)


    file_name = "testcases/input_{}.txt".format(testcase)
    current_file = open(file_name, "w")
    
    airport_IDs = set()
    regional = convert_to_airports(0, x_regional, y_regional, airport_IDs, [])
    hub = convert_to_airports(2, x_hub, y_hub, airport_IDs, regional)
    major = convert_to_airports(1, x_major, y_major, airport_IDs, regional)
    all = hub + major + regional
    random.shuffle(all)
    
    print(f"writin airports for testcase: {testcase}")
    #enumarate all airports with the index


    for i, airport in enumerate(all):
       if (i % 1000 == 0):
           print(f"airport {i}")
       current_file.write(str(airport.type) + " : " + str(airport.ID) + ", " + str(round(airport.x)) + ", " + str(round(airport.y)) + "\n")
    
    print(f"writin passengers for testcase: {testcase}")    
    for i in range(1000000000):
        if (i % 1000) == 0:
            print("passenger : ", i)
        current_file.write(str(i) + ", " + str(round(random.uniform(0, 1000))) + ", " + str(round(random.uniform(0, 1000))) + "\n")

    current_file.close()

