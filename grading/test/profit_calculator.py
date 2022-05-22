import random
import math
import numpy as np
import matplotlib.pyplot as plt

def from_to(from_type, to_type):
    if from_type == 0:
        if to_type == 0:
            return 3.0
        elif to_type == 1:
            return 1.6
        elif to_type == 2:
            return 0.9
    elif from_type == 1:
        if to_type == 0:
            return 1.8
        elif to_type == 1:
            return 0.8
        elif to_type == 2:
            return 0.6
    elif from_type == 2:
        if to_type == 0:
            return 1
        elif to_type == 1:
            return 0.7
        elif to_type == 2:
            return 0.5

def load_cost(aircraft_type_multiplier, economy_passengers, business_passengers, first_passengers, luxury_passengers, type):
    #aircraft type
    if type == 0:
        operation_fee = (random.randint(500, 700)) * 2 * 9
    elif type == 1:
        operation_fee = (random.randint(500, 700)) * 2.5 * 9
    elif type == 2:
        operation_fee = (random.randint(500, 700)) * 3 * 9
    elif type == 3:
        operation_fee = (random.randint(500, 700)) * 4 * 9

    economy_cost = economy_passengers * aircraft_type_multiplier * operation_fee * 1.2
    business_cost = business_passengers * aircraft_type_multiplier * operation_fee * 1.5
    first_cost = first_passengers * aircraft_type_multiplier * operation_fee * 2.5
    luxury_cost = luxury_passengers * aircraft_type_multiplier * operation_fee * 2.5
    total_cost = economy_cost + business_cost + first_cost + luxury_cost

    return total_cost


def flight_cost(distance, aircraft_weight_ratio, from_type, to_type, type, from_operation_fee, to_operation_fee):
    ratio = random.randint(0, 1000) / 1000
    fullness = 0.6 * math.pow(math.e, ratio)
    cost = distance * fullness
    if type == 0:
        cost *= 0.1
    elif type == 1:
        cost *= 0.15
    elif type == 2:
        cost *= 0.2
    elif type == 3:
        cost *= 0.08
    
    if from_type == 0:
        cost += from_operation_fee * aircraft_weight_ratio * fullness * 1.2
    elif from_type == 1:
        cost += from_operation_fee * aircraft_weight_ratio * fullness * 0.9
    elif from_type == 2:
        cost += from_operation_fee * aircraft_weight_ratio * fullness * 0.7

    if to_type == 0:
        cost += to_operation_fee * aircraft_weight_ratio * fullness * 1.3 
    elif to_type == 1:
        cost += to_operation_fee * aircraft_weight_ratio * fullness * 1.0
    elif to_type == 2:
        cost += to_operation_fee * aircraft_weight_ratio * fullness * 0.8
    
    return cost



def fuel_consumption(distance, weight, type):
    if type == 0:
        takeoff = weight * 0.08 / 0.7
        distance_ratio = distance / 2000
        consumption = distance * 0.6
    elif type == 1:
        takeoff = weight * 0.1 / 0.7
        distance_ratio = distance / 14000
        consumption = distance * 3.0
    elif type == 2:
        takeoff = weight * 0.1 / 0.7
        distance_ratio = distance / 7000
        consumption = distance * 5.3
    elif type == 3:
        takeoff = weight * 0.1 / 0.7
        distance_ratio = distance / 5000
        consumption = distance * 0.7
    bathtub = (25.9324 * math.pow(distance_ratio, 4)) + (-50.5633 * math.pow(distance_ratio, 3)) + (35.0554 * math.pow(distance_ratio, 2)) + (-9.90346 * distance_ratio) + (1.97413);
    consumption *= bathtub
    consumption += takeoff
    return consumption


def calculate_flight_revenue(aircraft_type, fullness, economy, business, first, luxury, from_type, to_type, distance, fuel_cost, load, from_operation_fee, to_operation_fee):
    #0: prop, 1: wide, 2: rapid, 3: jet
    if aircraft_type == 0:
        aircraft_multiplier = 0.9
        space = 60 * fullness
        weight = 23000 * load

    elif aircraft_type == 1:
        aircraft_multiplier = 0.7
        space = 450 * fullness
        weight = 250000 * load
    
    elif aircraft_type == 2:
        aircraft_multiplier = 1.9
        space = 120 * fullness
        weight = 185000 * load

    elif aircraft_type == 3:
        aircraft_multiplier = 5
        space = 30 * fullness
        weight = 18000 * load

    economy_passengers = space * economy // 1
    business_passengers = space * business // 1
    first_passengers = space * first // 1
    luxury_passengers = space * luxury // 1

    airport_multiplier = from_to(from_type, to_type)
    base_revenue = distance * airport_multiplier * aircraft_multiplier
    
    baggage_count = np.random.choice([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10], p=[0.15, 0.3, 0.25, 0.1, 0.05, 0.04, 0.03, 0.02, 0.02, 0.02, 0.02])
    economy_revenue = economy_passengers * base_revenue * 0.6
    economy_revenue += (economy_revenue * 0.05 * baggage_count)

    baggage_count = np.random.choice([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10], p=[0.15, 0.3, 0.25, 0.1, 0.05, 0.04, 0.03, 0.02, 0.02, 0.02, 0.02])
    business_revenue = business_passengers * base_revenue * 1.2 * baggage_count
    business_revenue += (business_revenue * 0.05 * baggage_count)

    baggage_count = np.random.choice([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10], p=[0.15, 0.3, 0.25, 0.1, 0.05, 0.04, 0.03, 0.02, 0.02, 0.02, 0.02])
    first_revenue = first_passengers * base_revenue * 3.2 * baggage_count
    first_revenue += (first_revenue * 0.05 * baggage_count)

    baggage_count = np.random.choice([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10], p=[0.15, 0.3, 0.25, 0.1, 0.05, 0.04, 0.03, 0.02, 0.02, 0.02, 0.02])
    luxury_revenue = luxury_passengers * base_revenue * 15 * baggage_count
    luxury_revenue += (luxury_revenue * 0.05 * baggage_count)

    #0: economy, 1: business, 2: first, 3: luxury
    # if passenger_type == 0:
    #     passenger_multiplier = 0.6
    # elif passenger_type == 1:
    #     passenger_multiplier = 1.2
    # elif passenger_type == 2:
    #     passenger_multiplier = 3.2
    # elif passenger_type == 3:
    #     passenger_multiplier = 15
    
    #connection_multiplier = 1 * pow(0.8, connection)
    # ticket_price = distance * airport_multiplier * aircraft_multiplier * passenger_multiplier
    # ticket_price += (ticket_price * 0.05 * baggage_count)

    revenue = economy_revenue + business_revenue + first_revenue + luxury_revenue

    ###EXPENSE###
    operational_cost = (random.randint(500, 700)) * 25
    fuel = fuel_consumption(distance, weight, aircraft_type)
    fuel_expense = fuel * fuel_cost
    flight_expense = flight_cost(distance, load, from_type, to_type, aircraft_type, from_operation_fee, to_operation_fee)
    load_expense = load_cost(aircraft_multiplier, economy_passengers, business_passengers, first_passengers, luxury_passengers, aircraft_type)
    expense = fuel_expense + flight_expense + load_expense

    profit = revenue - expense
    # if (revenue > expense):
    #     print("==PROFIT==")
    # else :
    #     print("==LOSS==")
    # print("\trevenue: " + str(revenue))
    # print("\texpense: " + str(expense))
    
    return profit

range_test = 1000
test = 1000
total_profit = 0

for j in range(range_test):
    profit_flights = 0
    loss_flights = 0
    profits = 0
    

    for i in range(test):
        from_type = random.randint(0, 2)
        to_type = random.randint(0, 2)
        aircraft_type = random.randint(0, 2)
        #aircraft_type = 3


        # luxury_passengers = random.randint(0, 6) / 100 
        # first_passengers = random.randint(2, 13) / 100
        # business_passengers = random.randint(15, 25) / 100
        
        other_than_economy = 2
        luxury_passengers = random.randint(0, 5) / 100 * other_than_economy
        first_passengers = random.randint(2, 13) / 100 * other_than_economy
        business_passengers = random.randint(15, 25) / 100 * other_than_economy
        economy_passengers = 1 - (luxury_passengers + first_passengers + business_passengers)
        #print(economy_passengers)

        # economy_passengers = 0.8
        # business_passengers = 0.2
        # first_passengers = 0
        # luxury_passengers = 0

        if aircraft_type == 0:
            weight = 14000

            distance = random.randint(200, 2000)
            distance_ratio = distance / 2000
            weight += (2500 * distance_ratio) + 700
            
            fullness = random.randint(700, 1000) / 1000
            weight += 5800 * fullness

            load = weight / 23000

        elif aircraft_type == 1:
            weight = 135000

            distance = random.randint(5000, 14000)
            distance_ratio = distance / 14000
            weight += (100000 * distance_ratio) + 12000
            
            fullness = random.randint(600, 1000) / 1000
            weight += 45000 * fullness

            load = weight / 250000

        elif aircraft_type == 2:
            weight = 80000

            distance = random.randint(2000, 7000)
            distance_ratio = distance / 7000
            weight += (88000 * distance_ratio) + 7000
            
            fullness = random.randint(700, 1000) / 1000
            weight += 10000 * fullness

            load = weight / 185000

        elif aircraft_type == 3:
            weight = 10000

            distance = random.randint(500, 5000)
            distance_ratio = distance / 5000
            weight += (1000 * distance_ratio) + 200
            
            fullness = random.randint(300, 1000) / 1000
            weight += 1500 * fullness

            load = weight / 18000
            luxury_passengers = 0.25
            first_passengers = 0.75
            business_passengers = 0
            economy_passengers = 0

        if (from_type == 0):
            from_operation_fee = (random.randint(500, 1000)) * 200
            fuel_cost = random.randint(600, 1000) / 100 * 11
        elif (from_type == 1):
            from_operation_fee = (random.randint(500, 1000)) * 250
            fuel_cost = random.randint(400, 700) / 100 * 11
        elif (from_type == 2):
            from_operation_fee = (random.randint(500, 1000)) * 300
            fuel_cost = random.randint(300, 600) / 100 * 11

        if (to_type == 0):
            to_operation_fee = (random.randint(500, 1000)) * 200
        elif (to_type == 1):
            to_operation_fee = (random.randint(500, 1000)) * 250
        elif (to_type == 2):
            to_operation_fee = (random.randint(500, 1000)) * 300

        profit = calculate_flight_revenue(aircraft_type, fullness, economy_passengers, business_passengers, first_passengers, luxury_passengers, from_type, to_type, distance, fuel_cost, load, from_operation_fee, to_operation_fee)
        if (profit > 0):
            profit_flights += 1
        else :
            loss_flights -= 1
        profits += profit

    average_profit = profits / test
    print("profitable flights: "+ str(profit_flights))
    print("loss flights: "+ str(loss_flights))
    print("profit per flight: {:,}".format(round(average_profit)))
    #print("total profit {:,}".format(round(profits)))
    print()
    total_profit += profits 

print("summary: ")
print("average profit per flight: " + str("{:,}".format(round(total_profit / (range_test * test)))))