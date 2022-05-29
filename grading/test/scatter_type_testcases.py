from curses.ascii import SO
import random
import math
import numpy as np
import matplotlib.pyplot as plt
from sortedcontainers import SortedList


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

class Point():
    def __init__(self, x, y):
        self.x = x
        self.y = y
class SortedByXPoint(Comparable, Point):
    def __init__(self, x, y):
        super().__init__(x, y)

    def _cmpkey(self):
        return (self.x, self.y)

class SortedByYPoint(Comparable, Point):
    def __init__(self, x, y):
        super().__init__(x, y)

    def _cmpkey(self):
        return (self.y, self.x)

class Passenger:
    def __init__(self, ID, type, weight, baggage_count, destinations):
        self.type = type
        self.ID = ID
        self.weight = weight
        self.baggage_count = baggage_count
        self.destinations = destinations

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

def mirror_by_x(y_hub, y_major):
    mirrored_y_hub = []
    mirrored_y_major = []
    for i in range(len(y_hub)):
        mirrored_y_hub.append(y_hub[i] * -1)
    for i in range(len(y_major)):
        mirrored_y_major.append(y_major[i] * -1)
    return mirrored_y_hub, mirrored_y_major

def mirror_by_y(x_hub, x_major):
    mirrored_x_hub = []
    mirrored_x_major = []
    for i in range(len(x_hub)):
        mirrored_x_hub.append(x_hub[i] * -1)
    for i in range(len(x_major)):
        mirrored_x_major.append(x_major[i] * -1)
    return mirrored_x_hub, mirrored_x_major

def mirror_by_x_y(x_hub, y_hub, x_major, y_major):
    mirrored_y_hub, mirrored_y_major = mirror_by_x(y_hub, y_major)
    mirrored_x_hub, mirrored_x_major = mirror_by_y(x_hub, x_major)
    return mirrored_x_hub, mirrored_y_hub, mirrored_x_major, mirrored_y_major

def invert_x_y(x_hub, y_hub, x_major, y_major):
    x_hub_inverted = []
    y_hub_inverted = []
    x_major_inverted = []
    y_major_inverted = []
    for i in range(len(x_hub)):
        x_hub_inverted.append(y_hub[i])
        y_hub_inverted.append(x_hub[i])
    for i in range(len(x_major)):
        x_major_inverted.append(y_major[i])
        y_major_inverted.append(x_major[i])

    return x_hub_inverted, y_hub_inverted, x_major_inverted, y_major_inverted

def stitch_points_on_y(x_hub, y_hub, x_major, y_major, x_hub_other, y_hub_other, x_major_other, y_major_other):
    stitched_x_hub = []
    stitched_y_hub = []
    stitched_x_major = []
    stitched_y_major = []

    y_airport_sorted = SortedList()
    for i in range(len(x_hub)):
        y_airport_sorted.add(SortedByYPoint(x_hub[i], y_hub[i]))
    for i in range(len(x_major)):
        y_airport_sorted.add(SortedByYPoint(x_major[i], y_major[i]))

    y_airport_other_sorted = SortedList()
    for i in range(len(x_hub_other)):
        y_airport_other_sorted.add(SortedByYPoint(x_hub_other[i], y_hub_other[i]))
    for i in range(len(x_major_other)):
        y_airport_other_sorted.add(SortedByYPoint(x_major_other[i], y_major_other[i]))
    
    x_diff = y_airport_sorted[0].x - y_airport_other_sorted[-1].x
    y_diff = y_airport_sorted[0].y - y_airport_other_sorted[-1].y - 7000

    stitched_x_hub = x_hub.copy()
    stitched_y_hub = y_hub.copy()
    for i in range(len(x_hub_other)):
        stitched_x_hub.append(x_hub_other[i] + x_diff)
        stitched_y_hub.append(y_hub_other[i] + y_diff)
    stitched_x_major = x_major.copy()
    stitched_y_major = y_major.copy()
    for i in range(len(x_major_other)):
        stitched_x_major.append(x_major_other[i] + x_diff)
        stitched_y_major.append(y_major_other[i] + y_diff)

    recenter(stitched_x_hub, stitched_y_hub, stitched_x_major, stitched_y_major)
    return stitched_x_hub, stitched_y_hub, stitched_x_major, stitched_y_major

def stitch_points_on_x(x_hub, y_hub, x_major, y_major, x_hub_other, y_hub_other, x_major_other, y_major_other):
    stitched_x_hub = []
    stitched_y_hub = []
    stitched_x_major = []
    stitched_y_major = []

    x_airport_sorted = SortedList()
    for i in range(len(x_hub)):
        x_airport_sorted.add(SortedByXPoint(x_hub[i], y_hub[i]))
    for i in range(len(x_major)):
        x_airport_sorted.add(SortedByXPoint(x_major[i], y_major[i]))

    x_airport_other_sorted = SortedList()
    for i in range(len(x_hub_other)):
        x_airport_other_sorted.add(SortedByXPoint(x_hub_other[i], y_hub_other[i]))
    for i in range(len(x_major_other)):
        x_airport_other_sorted.add(SortedByXPoint(x_major_other[i], y_major_other[i]))
    
    x_diff = x_airport_sorted[-1].x - x_airport_other_sorted[0].x + 7000
    y_diff = x_airport_sorted[-1].y - x_airport_other_sorted[0].y

    stitched_x_hub = x_hub.copy()
    stitched_y_hub = y_hub.copy()
    for i in range(len(x_hub_other)):
        stitched_x_hub.append(x_hub_other[i] + x_diff)
        stitched_y_hub.append(y_hub_other[i] + y_diff)
    stitched_x_major = x_major.copy()
    stitched_y_major = y_major.copy()
    for i in range(len(x_major_other)):
        stitched_x_major.append(x_major_other[i] + x_diff)
        stitched_y_major.append(y_major_other[i] + y_diff)

    recenter(stitched_x_hub, stitched_y_hub, stitched_x_major, stitched_y_major)
    return stitched_x_hub, stitched_y_hub, stitched_x_major, stitched_y_major

def recenter(x_hub, y_hub, x_major, y_major):
    x_airport_sorted = SortedList()
    y_airport_sorted = SortedList()
    for i in range(len(x_hub)):
        x_airport_sorted.add(SortedByXPoint(x_hub[i], y_hub[i]))
        y_airport_sorted.add(SortedByYPoint(x_hub[i], y_hub[i]))
    for i in range(len(x_major)):
        x_airport_sorted.add(SortedByXPoint(x_major[i], y_major[i]))
        y_airport_sorted.add(SortedByYPoint(x_major[i], y_major[i]))
    
    lowest_y_point = y_airport_sorted[0]
    highest_y_point = y_airport_sorted[-1]
    lowest_x_point = x_airport_sorted[0]
    highest_x_point = x_airport_sorted[-1]
    y_diff = highest_y_point.y - lowest_y_point.y
    x_diff = highest_x_point.x - lowest_x_point.x
    
    x_shift = -(x_diff / 2) - lowest_x_point.x
    y_shift = -(y_diff / 2) - lowest_y_point.y
    
    for i in range(len(x_hub)):
        x_hub[i] += x_shift
        y_hub[i] += y_shift
    for i in range(len(x_major)):
        x_major[i] += x_shift
        y_major[i] += y_shift
    return x_hub, y_hub, x_major, y_major

def copy_on_y(x_hub, y_hub, x_major, y_major):
    x_airport_sorted = SortedList()
    y_airport_sorted = SortedList()
    for i in range(len(x_hub)):
        x_airport_sorted.add(SortedByXPoint(x_hub[i], y_hub[i]))
        y_airport_sorted.add(SortedByYPoint(x_hub[i], y_hub[i]))
    for i in range(len(x_major)):
        x_airport_sorted.add(SortedByXPoint(x_major[i], y_major[i]))
        y_airport_sorted.add(SortedByYPoint(x_major[i], y_major[i]))
    
    lowest_y_point = y_airport_sorted[0]
    highest_y_point = y_airport_sorted[-1]
    shift_y = lowest_y_point.y - highest_y_point.y - 7000
    shift_x = lowest_y_point.x - highest_y_point.x

    x_hub_shifted = []
    y_hub_shifted = []
    for i in range(len(x_hub)):
        x_hub_shifted.append(x_hub[i] + shift_x)
        y_hub_shifted.append(y_hub[i] + shift_y)
    x_major_shifted = []
    y_major_shifted = []
    for i in range(len(x_major)):
        x_major_shifted.append(x_major[i] + shift_x)
        y_major_shifted.append(y_major[i] + shift_y)

    x_hub = x_hub_shifted + x_hub
    y_hub = y_hub_shifted + y_hub
    x_major = x_major_shifted + x_major
    y_major = y_major_shifted + y_major
    return x_hub, y_hub, x_major, y_major

def generate_hub_and_major_points_by_point_manupulation(x_hub, y_hub, x_major, y_major, cycle, original_airport_count, plt):
    generate_hub_and_major_points_efficient(original_airport_count, x_hub, y_hub, x_major, y_major)
    for i in range(cycle):
        which_new_manupualtor = np.random.choice([0,1,2,3,4,5], p=[0.15,0.15,0.15,0.15,0.15,0.25])
        if which_new_manupualtor == 0:
            x_hub_new, y_hub_new, x_major_new, y_major_new = x_hub.copy(), y_hub.copy(), x_major.copy(), y_major.copy()
        elif which_new_manupualtor == 1:
            x_hub_new, y_hub_new, x_major_new, y_major_new = invert_x_y(x_hub, y_hub, x_major, y_major)
        elif which_new_manupualtor == 2:
            x_hub_new, y_hub_new, x_major_new, y_major_new = mirror_by_x_y(x_hub, y_hub, x_major, y_major)
        elif which_new_manupualtor == 3:
            x_hub_new, x_major_new = mirror_by_y(x_hub, x_major)
            y_hub_new, y_major_new = y_hub.copy(), y_major.copy()
        elif which_new_manupualtor == 4:
            y_hub_new, y_major_new = mirror_by_x(y_hub, y_major)
            x_hub_new, x_major_new = x_hub.copy(), x_major.copy()
        elif which_new_manupualtor == 5:
            x_hub_new, y_hub_new, x_major_new, y_major_new = [], [], [], []
            generate_hub_and_major_points_efficient(original_airport_count, x_hub_new, y_hub_new, x_major_new, y_major_new)
        if (max(x_hub_new) > max(y_hub_new)):
            x_hub, y_hub, x_major, y_major = stitch_points_on_y(x_hub, y_hub, x_major, y_major, x_hub_new, y_hub_new, x_major_new, y_major_new)
        else:
            x_hub, y_hub, x_major, y_major = stitch_points_on_x(x_hub, y_hub, x_major, y_major, x_hub_new, y_hub_new, x_major_new, y_major_new)        
        draw_connectivity_not_regional(x_hub, y_hub, x_major, y_major, plt)
    return x_hub, y_hub, x_major, y_major

def generate_hub_and_major_points(count, x_hub=[], y_hub=[], x_major=[], y_major=[]):
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

        # reduces linearity of hub generation
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
                                y_major.add(y_major_curr)
                                break
                break
    return

def generate_hub_and_major_points_efficient(count, x_hub = [], y_hub = [], x_major = [], y_major = []):
    x_hub_sorted = SortedList()
    y_hub_sorted = SortedList()
    x_major_sorted = SortedList()
    y_major_sorted = SortedList()
    d = random.randint(10000, 14000)
    alpha_prev = random.randint(0, 360)
    x_prev = d * math.cos(alpha_prev)
    y_prev = d * math.sin(alpha_prev)
    x_hub.append(x_prev)
    y_hub.append(y_prev)
    x_hub_sorted.add(SortedByXPoint(x_prev, y_prev))
    y_hub_sorted.add(SortedByYPoint(x_prev, y_prev))

    for i in range(count-1):
        if i % 5 == 0:
            print("Generating hub point: " + str(i))

        try_count_1 = 0

        # reduces linearity of hub generation
        if np.random.choice([True, False], p=[0.6, 0.4]):
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
            for x_sorted in x_hub_sorted.irange(SortedByXPoint(x_curr - 14000, y_curr), SortedByXPoint(x_curr + 14000, y_curr)):
                euc_distance = ((x_sorted.x - x_curr)**2 + (x_sorted.y - y_curr)**2) ** 0.5
                if euc_distance < 10000:
                    point_ok = False
                    break
                if euc_distance < 14000 and not connected:
                    connected = True
            if point_ok:
                for y_sorted in y_hub_sorted.irange(SortedByYPoint(x_curr, y_curr - 14000), SortedByYPoint(x_curr, y_curr + 14000)):
                    euc_distance = ((y_sorted.x - x_curr)**2 + (y_sorted.y - y_curr)**2) ** 0.5
                    if euc_distance < 10000:
                        point_ok = False
                        break
                    if euc_distance < 14000 and not connected:
                        connected = True

            if (point_ok and connected) or len(x_hub) == 0:
                alpha_prev_prev = alpha_prev
                x_hub.append(x_curr)
                y_hub.append(y_curr)
                x_hub_sorted.add(SortedByXPoint(x_curr, y_curr))
                y_hub_sorted.add(SortedByYPoint(x_curr, y_curr))
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
                        for x_sorted in x_hub_sorted.irange(SortedByXPoint(x_curr - 14000, y_curr), SortedByXPoint(x_curr + 14000, y_curr)):
                            euc_distance = ((x_sorted.x - x_curr)**2 + (x_sorted.y - y_curr)**2) ** 0.5
                            if euc_distance < 10000:
                                point_ok = False
                                break
                            if euc_distance < 14000 and not connected:
                                connected = True
                        if point_ok:
                            for y_sorted in y_hub_sorted.irange(SortedByYPoint(x_curr, y_curr - 14000), SortedByYPoint(x_curr, y_curr + 14000)):
                                euc_distance = ((y_sorted.x - x_curr)**2 + (y_sorted.y - y_curr)**2) ** 0.5
                                if euc_distance < 10000:
                                    point_ok = False
                                    break
                                if euc_distance < 14000 and not connected:
                                    connected = True

                        if point_ok and connected:
                            connected = False
                            point_ok = True
                            for x_sorted in x_major_sorted.irange(SortedByXPoint(x_curr - 14000, y_curr), SortedByXPoint(x_curr + 14000, y_curr)):
                                euc_distance = ((x_sorted.x - x_curr)**2 + (x_sorted.y - y_curr)**2) ** 0.5
                                if euc_distance < 10000:
                                    point_ok = False
                                    break
                                if euc_distance < 14000 and not connected:
                                    connected = True
                            if point_ok:
                                for y_sorted in y_major_sorted.irange(SortedByYPoint(x_curr, y_curr - 14000), SortedByYPoint(x_curr, y_curr + 14000)):
                                    euc_distance = ((y_sorted.x - x_curr)**2 + (y_sorted.y - y_curr)**2) ** 0.5
                                    if euc_distance < 10000:
                                        point_ok = False
                                        break
                                    if euc_distance < 14000 and not connected:
                                        connected = True

                            if (point_ok and connected) or len(x_major) == 0: 
                                x_major_sorted.add(x_major_curr)
                                y_major_sorted.add(y_major_curr)
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
    count = 0
    for  airport in airport_objects:
        count += 1
        if count % 100 == 0:
            print(f"generating regionals for {count}th airport ")
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

def draw_connectivity_not_regional(x_hub, y_hub, x_major, y_major, plt):
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
    A = 200 #random.randint(200, 300) #random.randint(40, 60) #initial hub count
    P = random.randint(20000, 100000)

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

    x_hub, y_hub, x_major, y_major = generate_hub_and_major_points_by_point_manupulation(x_hub, y_hub, x_major, y_major, 8, A, plt)    
    
    print("2")
    airports = assign_hub_and_major_airports(x_hub, y_hub, x_major, y_major, M)
    print("number of hub and major airports: " + str(len(airports)))
    x_regional = []
    y_regional = []
    print("3")
    generate_regional_airports(airports, x_regional, y_regional, M)
    print("4", str(A))
    A = len(airports)

    print("5", str(P))
    passengers = generate_passengers_cluster(P, airports)
    #passengers = generate_passengers_not_regional(P, airports)
    #passengers = generate_passengers_special(P, airports)
    #passengers = generate_passengers_special(P, airports)

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
