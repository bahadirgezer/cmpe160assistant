import re
import sys
import math

class Aircraft:
    def __init__(self, initial_airport, operation_fee, ID):
        self.ID = ID
        self.current_airport = initial_airport
        self.operation_fee = operation_fee 
        self.fuel = 0.0
        self.fuel_weight = 0.7
        self.passengers = dict()

        self.economy_seats = 0
        self.business_seats = 0
        self.first_seats = 0
        self.economy_occupied = 0
        self.business_occupied = 0
        self.first_occupied = 0

class PropAircraft(Aircraft):
    def __init__(self, initial_airport, operation_fee, ID):
        super().__init__(initial_airport, operation_fee, ID)
        self.max_weight = 23000
        self.weight = 14000
        self.floor_area = 60
        self.fuel_capacity = 6000
        self.fuel_consumption = 0.6
        self.aircraft_multiplier = 0.9

    def get_fuel_consumption(self, distance):
        # print("weight: " + str(self.weight))
        takeoff_fuel = self.weight * 0.08 / self.fuel_weight
        distance_ratio = distance / 2000
        bath_tub_coefficient = (25.9324 * (distance_ratio ** 4)) + (-50.5633 * (distance_ratio ** 3)) + (35.0554 * (distance_ratio ** 2)) + (-9.90346 * distance_ratio) + (1.97413)
        average_fuel_consumption = self.fuel_consumption * bath_tub_coefficient * distance
        average_fuel_consumption += takeoff_fuel
        # print("average fuel consumption: " + str(average_fuel_consumption))
        return average_fuel_consumption
    
    def __str__(self):
        return "prop aircraft: " + self.current_airport + " " + self.fuel + " " + self.passengers

class WideAircraft(Aircraft):
    def __init__(self, initial_airport, operation_fee, ID):
        super().__init__(initial_airport, operation_fee, ID)
        self.max_weight = 250000
        self.weight = 135000
        self.floor_area = 450
        self.fuel_capacity = 140000
        self.fuel_consumption = 3.0
        self.aircraft_multiplier = 0.7
    
    def get_fuel_consumption(self, distance):
        takeoff_fuel = self.weight * 0.1 / self.fuel_weight
        distance_ratio = distance / 14000
        bath_tub_coefficient = (25.9324 * (distance_ratio ** 4)) + (-50.5633 * (distance_ratio ** 3)) + (35.0554 * (distance_ratio ** 2)) + (-9.90346 * distance_ratio) + (1.97413)
        average_fuel_consumption = self.fuel_consumption * bath_tub_coefficient * distance
        average_fuel_consumption += takeoff_fuel
        return average_fuel_consumption

    def __str__(self):
        return "widebody aircraft: " + self.current_airport + " " + self.fuel + " " + self.passengers

class RapidAircraft(Aircraft):
    def __init__(self, initial_airport, operation_fee, ID):
        super().__init__(initial_airport, operation_fee, ID)
        self.max_weight = 185000
        self.weight = 80000
        self.floor_area = 120
        self.fuel_capacity = 120000
        self.fuel_consumption = 5.3
        self.aircraft_multiplier = 1.9
        
    def get_fuel_consumption(self, distance):
        takeoff_fuel = self.weight * 0.1 / self.fuel_weight
        distance_ratio = distance / 7000
        bath_tub_coefficient = (25.9324 * (distance_ratio ** 4)) + (-50.5633 * (distance_ratio ** 3)) + (35.0554 * (distance_ratio ** 2)) + (-9.90346 * distance_ratio) + (1.97413)
        average_fuel_consumption = self.fuel_consumption * bath_tub_coefficient * distance
        average_fuel_consumption += takeoff_fuel
        return average_fuel_consumption

    def __str__(self):
        return "rapid aircraft: " + self.current_airport + " " + self.fuel + " " + self.passengers

class JetAircraft(Aircraft):
    def __init__(self, initial_airport, operation_fee, ID):
        super().__init__(initial_airport, operation_fee, ID)
        self.max_weight = 18000
        self.weight = 10000
        self.floor_area = 30
        self.fuel_capacity = 10000
        self.fuel_consumption = 0.7
        self.aircraft_multiplier = 5

    def get_fuel_consumption(self, distance):
        takeoff_fuel = self.weight * 0.1 / self.fuel_weight
        distance_ratio = distance / 5000
        bath_tub_coefficient = (25.9324 * (distance_ratio ** 4)) + (-50.5633 * (distance_ratio ** 3)) + (35.0554 * (distance_ratio ** 2)) + (-9.90346 * distance_ratio) + (1.97413)
        average_fuel_consumption = self.fuel_consumption * bath_tub_coefficient * distance
        average_fuel_consumption += takeoff_fuel
        return average_fuel_consumption

    def __str__(self):
        return "jet aircraft: " + self.current_airport + " " + self.fuel + " " + self.passengers

class Passenger:
    def __init__(self, ID, weight, baggage_count, destinations):
        self.ID = ID
        self.weight = weight
        self.baggage_count = baggage_count
        self.destinations = destinations
        self.previous_destination = destinations[0]
        self.seat_assigned = -1
        self.connection_multiplier = 1.0
        self.seat_multiplier = 1.0

    def __str__(self):
        return self.ID

class EconomyPassenger(Passenger):
    def __init__(self, ID, weight, baggage_count, destinations):
        super().__init__(ID, weight, baggage_count, destinations)
    
    def __str__(self):
        return "economy passenger: " + self.ID + " " + self.destinations

class BusinessPassenger(Passenger):
    def __init__(self, ID, weight, baggage_count, destinations):
        super().__init__(ID, weight, baggage_count, destinations)

    def __str__(self):
        return "business passenger: " + self.ID + " " + self.destinations

class FirstClassPassenger(Passenger):
    def __init__(self, ID, weight, baggage_count, destinations):
        super().__init__(ID, weight, baggage_count, destinations)

    def __str__(self):
        return "first class passenger: " + self.ID + " " + self.destinations

class LuxuryPassenger(Passenger):
    def __init__(self, ID, weight, baggage_count, destinations):
        super().__init__(ID, weight, baggage_count, destinations)

    def __str__(self):
        return "luxury passenger: " + self.ID + " " + self.destinations

class Airport:
    def __init__(self, ID, x, y, fuel_cost, op_fee, capacity):
        self.ID = ID
        self.x = x
        self.y = y
        self.fuel_cost = fuel_cost
        self.op_fee = op_fee
        self.capacity = capacity
        self.current = 0
        self.passengers = dict()

    def distance(self, other):
        return ((self.x - other.x)**2 + (self.y - other.y)**2)**0.5

    def __str__(self):
        return self.ID + " " + self.passengers

class RegionalAirport(Airport):
    def __init__(self, ID, x, y, fuel_cost, op_fee, capacity):
        super().__init__(ID, x, y, fuel_cost, op_fee, capacity)

    def depart_aircraft(self, weight_ratio):
        fulness_coefficient = 0.6 * math.e ** (float(self.current / self.capacity)) # USE THE VALUES BEFORE THE ACTUAL OPERATION
        return self.op_fee * weight_ratio * 1.2 * fulness_coefficient

    def land_aircraft(self, weight_ratio):
        fulness_coefficient = 0.6 * math.e ** (float(self.current / self.capacity)) # USE THE VALUES BEFORE THE ACTUAL OPERATION
        return self.op_fee * weight_ratio * 1.3 * fulness_coefficient

    def __str__(self):
        return "regional airport: " +str(self.ID) + " " + str(self.x) + " " + str(self.y) + " " + str(self.current) + " " + str(self.capacity)

class MajorAirport(Airport):
    def __init__(self, ID, x, y, fuel_cost, op_fee, capacity):
        super().__init__(ID, x, y, fuel_cost, op_fee, capacity)

    def depart_aircraft(self, weight_ratio):
        fulness_coefficient = 0.6 * math.exp(float(self.current / self.capacity)) # USE THE VALUES BEFORE THE ACTUAL OPERATION
        return self.op_fee * weight_ratio * 0.9 * fulness_coefficient

    def land_aircraft(self, weight_ratio):
        fulness_coefficient = 0.6 * math.exp(float(self.current / self.capacity)) # USE THE VALUES BEFORE THE ACTUAL OPERATION
        return self.op_fee * weight_ratio * 1.00 * fulness_coefficient

    def __str__(self):
        return "major airport: " + self.ID + " " + self.x + " " + self.y + " " + self.passengers

class HubAirport(Airport):
    def __init__(self, ID, x, y, fuel_cost, op_fee, capacity):
        super().__init__(ID, x, y, fuel_cost, op_fee, capacity)

    def depart_aircraft(self, weight_ratio):
        fulness_coefficient = 0.6 * math.exp(float(self.current / self.capacity)) # USE THE VALUES BEFORE THE ACTUAL OPERATION
        return self.op_fee * weight_ratio * 0.7 * fulness_coefficient

    def land_aircraft(self, weight_ratio):
        fulness_coefficient = 0.6 * math.exp(float(self.current / self.capacity)) # USE THE VALUES BEFORE THE ACTUAL OPERATION
        return self.op_fee * weight_ratio * 0.8 * fulness_coefficient

    def __str__(self):
        return "hub airport: " + self.ID + " " + self.x + " " + self.y + " " + self.passengers

class testing:
    def __init__(self, input_file, output_file, testingmode):
        self.testingmode = testingmode
        self.input_file = input_file
        self.output_file = output_file
        self.airports = dict()
        self.aircrafts = dict()
        self.profit = 0.0
    
    def run(self):
        print(self.input_file, self.output_file)
        self.read_input()
        self.read_output()
    #                                       ARRANGE THESE TWO METHODS SO THAT THE CODE RUNS WELL
    def error(self, error_id):
        #print("Error{}".format(error_id))
        return
        
    def read_input(self):
        with open(self.input_file, 'r') as f:
            first_line = f.readline().split()
            self.max_aircrafts = int(first_line[0])
            num_airports = int(first_line[1])
            num_passengers = int(first_line[2])
            second_line = f.readline().split()
            self.prop_op_fee = float(second_line[0])
            self.wide_op_fee = float(second_line[1])
            self.rapid_op_fee = float(second_line[2])
            self.jet_op_fee = float(second_line[3])
            self.op_cost = float(second_line[4])

            for i in range(num_airports):
                line = f.readline()
                #                                           1       2           3           4               5          6
                regional_match = re.search(r"^regional : (\d+), ([-]?\d+), ([-]?\d+), (\d+[\.]?\d+), (\d+[\.]?\d+), (\d+)", line)
                major_match = re.search(r"^major : (\d+), ([-]?\d+), ([-]?\d+), (\d+[\.]?\d+), (\d+[\.]?\d+), (\d+)", line)
                hub_match = re.search(r"^hub : (\d+), ([-]?\d+), ([-]?\d+), (\d+[\.]?\d+), (\d+[\.]?\d+), (\d+)", line)
                if regional_match:
                    airport = RegionalAirport(int(regional_match.group(1)), int(regional_match.group(2)), int(regional_match.group(3)), float(regional_match.group(4)), float(regional_match.group(5)), int(regional_match.group(6)))
                elif major_match:
                    airport = MajorAirport(int(major_match.group(1)), int(major_match.group(2)), int(major_match.group(3)), float(major_match.group(4)), float(major_match.group(5)), int(major_match.group(6)))
                elif hub_match:
                    airport = HubAirport(int(hub_match.group(1)), int(hub_match.group(2)), int(hub_match.group(3)), float(hub_match.group(4)), float(hub_match.group(5)), int(hub_match.group(6)))
                else:
                    print("AIRPORT INPUT NOT MATCHED")
                self.airports.update({airport.ID: airport})
                

            for i in range(num_passengers):
                line = f.readline()
                
                economy_match = re.search(r"economy : (\d+), (\d+), (\d+), \[(.+)\]", line)
                business_match = re.search(r"business : (\d+), (\d+), (\d+), \[(.+)\]", line)
                first_match = re.search(r"first : (\d+), (\d+), (\d+), \[(.+)\]", line)
                luxury_match = re.search(r"luxury : (\d+), (\d+), (\d+), \[(.+)\]", line)
                if economy_match:
                    passenger = EconomyPassenger(int(economy_match.group(1)), int(economy_match.group(2)), int(economy_match.group(3)), [int(i) for i in economy_match.group(4).split(', ')])
                elif business_match:
                    passenger = BusinessPassenger(int(business_match.group(1)), int(business_match.group(2)), int(business_match.group(3)), [int(i) for i in business_match.group(4).split(', ')])
                elif first_match:
                    passenger = FirstClassPassenger(int(first_match.group(1)), int(first_match.group(2)), int(first_match.group(3)), [int(i) for i in first_match.group(4).split(', ')])
                elif luxury_match:
                    passenger = LuxuryPassenger(int(luxury_match.group(1)), int(luxury_match.group(2)), int(luxury_match.group(3)), [int(i) for i in luxury_match.group(4).split(', ')])
                else:
                    print("PASSENGER INPUT NOT MATHCED")

                airport = self.airports.get(passenger.destinations[0]) #check syntax
                airport.passengers.update({passenger.ID: passenger})

    def read_output(self):
        with open(self.output_file, 'r') as f:
            lines = f.readlines()
            
            # if len(lines) > 100:
            #     print("output file must not exceed 100 lines.") #       LINE HARD LIMIT
            #     return False
            
            line_number = 0
            last_line = len(lines)
            #profit_line = False
            self.line_profit = 0.0
            
            for line in lines:
                if line_number > 50:
                    print("output log exceeded 50 lines.") 
                    return False
                tokens = line.split()
                line_number += 1

                if line_number == last_line:
                    if round(self.profit) != round(float(tokens[0])):
                        print("===total profit is not correct===")
                        print("expected profit, outputted profit: " + str(round(self.profit)) + ", " + str(round(float(tokens[0]))))
                        return False
                    print("===total profit is correct===")

                    return True

                if tokens[0] == '0':
                    airport = self.airports.get(int(tokens[1]))
                    if not airport:
                        print("aircraft creation error: airport not found")
                        self.error(101)
                        return False
                    if not self.aircraft_creation(airport, int(tokens[2])):
                        print("line number: "+ str(line_number))
                        print("line: " + line)
                        self.error(1)
                        return  False
                    profit_index = 3

                elif tokens[0] == '1':
                    to_airport = self.airports.get(int(tokens[1]))
                    aircraft = self.aircrafts.get(int(tokens[2]))
                    if not to_airport:
                        print("flight operation error: airport not found")
                        self.error(2)
                        return False
                    if not aircraft:
                        print("flight operation error: aircraft not found")
                        self.error(3)
                        return False
                    
                    self.flight_operation(to_airport, aircraft)
                    profit_index = 3

                elif tokens[0] == '2':
                    if not self.seat_assignment(self.aircrafts.get(int(tokens[1])), int(tokens[2]), int(tokens[3]), int(tokens[4])):
                        self.error(4)
                        return False
                    profit_index = 5

                elif tokens[0] == '3':
                    if not self.fuel_loading(self.aircrafts.get(int(tokens[1])), float(tokens[2])):
                        self.error(5)
                        return False
                    profit_index = 3
                    
                elif tokens[0] == '4':
                    airport = self.airports.get(int(tokens[3]))
                    aircraft = self.aircrafts.get(int(tokens[2]))
                    if not airport:
                        print("passenger loading error: airport not found")
                        self.error(6)
                        return False
                    if not aircraft:
                        print("passenger loading error: aircraft not found")
                        self.error(7)
                        return False

                    passenger = airport.passengers.get(int(tokens[1]))
                    if not passenger:
                        print("passenger loading error: passenger not at airport") ##TODO: object NONE ERRORS WILL BE ADDED LATER
                        self.error(8)
                        return False
                    self.passenger_loading(passenger, aircraft, airport)
                    profit_index = 4

                elif tokens[0] == '5':
                    airport = self.airports.get(int(tokens[3]))
                    aircraft = self.aircrafts.get(int(tokens[2]))
                    if not airport:
                        print("passenger unloading error: airport not found")
                        self.error(9)
                        return False
                    if not aircraft:
                        print("passenger unloading error: aircraft not found")
                        self.error(10)
                        return False

                    passenger = aircraft.passengers.get(int(tokens[1]))
                    if not passenger:
                        print("passenger unloading error: passenger not at aircraft") ##TODO: object NONE ERRORS WILL BE ADDED LATER
                        self.error(11)
                        return False
                    self.passenger_unloading(passenger, aircraft, airport)
                    profit_index = 4

                elif tokens[0] == '6':
                    airport = self.airports.get(int(tokens[4]))
                    from_aircraft = self.aircrafts.get(int(tokens[2]))
                    to_aircraft = self.aircrafts.get(int(tokens[3]))
                    if not airport:
                        print("passenger transfer error: airport not found")
                        self.error(12)
                        return False
                    if not from_aircraft:
                        print("passenger transfer error: from aircraft not found")
                        self.error(13)
                        return False
                    if not to_aircraft:
                        print("passenger transfer error: to aircraft not found")
                        self.error(14)
                        return False

                    passenger = from_aircraft.passengers.get(int(tokens[1]))
                    if not passenger:
                        print("passenger transfer error: passenger not at from aircraft")
                        self.error(15)
                        return False
                    self.passenger_transfer(passenger, from_aircraft, to_aircraft, airport)
                    profit_index = 5

                else:
                    print("OUTPUT LINE NOT MATCHED")
                    return False
                
                profit_index += 1
                line_profit = float(tokens[profit_index])
                self.line_profit = self.profit - self.line_profit
                if round(line_profit) != round(self.line_profit):
                    print()
                    print("===incorrect profit===")
                    print("line " + str(line_number) + ": \"" + line.rstrip() + "\"")
                    print("expected profit, outputed profit: " + str(self.line_profit) + ", " + str(line_profit))
                    print()
                    if self.testingmode == 0:
                        return False
                elif self.testingmode == 0:
                    print()
                    print("===correct profit===")
                    print("line " + str(line_number) + ": \"" + line.rstrip() + "\"")
                    print()
                self.line_profit = self.profit
                

    def flight_operation(self, to_airport, aircraft): #fly output log should be called whenever a fly() function is called
        self.profit -= self.op_cost * len(self.aircrafts)
        if to_airport.ID == aircraft.current_airport.ID:
            #print("flight operation error: to airport is the same as the current airport")
            return False
        if to_airport.capacity < to_airport.current + 1:
            #print("flight operation error: to airport is full")
            return False
        
        flight_distance = to_airport.distance(aircraft.current_airport)
        consumed = aircraft.get_fuel_consumption(flight_distance)
        if aircraft.fuel < consumed:
            #print("flight operation error: not enough fuel")
            return False

        fullness = (aircraft.economy_occupied + aircraft.business_occupied + aircraft.first_occupied) / (aircraft.economy_seats + aircraft.business_seats + aircraft.first_seats) 
        if isinstance(aircraft, PropAircraft):
            aircraft_constant = 0.1
        elif isinstance(aircraft, WideAircraft):
            aircraft_constant = 0.15
        elif isinstance(aircraft, RapidAircraft):
            aircraft_constant = 0.2
        elif isinstance(aircraft, JetAircraft):
            aircraft_constant = 0.08

        cost = flight_distance * fullness * aircraft_constant
        # print("fullness, aircraft constant, distance: " + str(cost))
        
        departure_cost = aircraft.current_airport.depart_aircraft(float(aircraft.weight/aircraft.max_weight))
        landing_cost = to_airport.land_aircraft(float(aircraft.weight/aircraft.max_weight))
        cost += (departure_cost + landing_cost)
        # print("departure_cost: " + str(departure_cost))
        # print("landing_cost: " + str(landing_cost))

        self.profit -= cost

        to_airport.current += 1
        aircraft.current_airport.current -= 1

        aircraft.current_airport = to_airport
        aircraft.weight -= consumed * aircraft.fuel_weight
        aircraft.fuel -= consumed

        return True

    def passenger_transfer(self, passenger, from_aircraft, to_aircraft, airport):
        if (from_aircraft.current_airport.ID != airport.ID):
            #print("passenger transfer error: from aircraft is not at the correct airport.")
            self.profit -= from_aircraft.operation_fee
            return False
        if (to_aircraft.current_airport.ID != airport.ID):
            #print("passenger transfer error: to aircraft is not at the correct airport.")
            self.profit -= from_aircraft.operation_fee # FROM OPERATION FEE SHOULD BE RETURNED
            return False
        
        if to_aircraft.weight + passenger.weight > to_aircraft.max_weight:
            #print("passenger transfer error: to aircraft weight exceeded.")
            self.profit -= from_aircraft.operation_fee
            return False

        if passenger.seat_assigned == 1:
            from_aircraft.economy_occupied -= 1
        elif passenger.seat_assigned == 2:
            from_aircraft.business_occupied -= 1
        elif passenger.seat_assigned == 3:
            from_aircraft.first_occupied -= 1
        else:
            #print("passenger transfer error: passenger seat type is invalid.")
            self.profit -= from_aircraft.operation_fee
            return False                                                #TODO: MIGHT BE REDUNDANT

        if isinstance(passenger, LuxuryPassenger) or isinstance(passenger, FirstClassPassenger):
            if to_aircraft.first_seats > to_aircraft.first_occupied:
                passenger.seat_multiplier *= 3.2
                passenger.seat_assigned = 3
                to_aircraft.first_occupied += 1
                fee = to_aircraft.operation_fee * to_aircraft.aircraft_multiplier * 2.5
            elif to_aircraft.business_seats > to_aircraft.business_occupied:
                passenger.seat_multiplier *= 1.2
                passenger.seat_assigned = 2
                to_aircraft.business_occupied += 1
                fee = to_aircraft.operation_fee * to_aircraft.aircraft_multiplier * 1.5
            elif to_aircraft.economy_seats > to_aircraft.economy_occupied:
                passenger.seat_multiplier *= 0.6
                passenger.seat_assigned = 1
                to_aircraft.economy_occupied += 1
                fee = to_aircraft.operation_fee * to_aircraft.aircraft_multiplier * 1.2
            else:
                #print("passenger loading error: no seats available.")
                self.profit -= from_aircraft.operation_fee #                         FROM OPERATION FEE SHOULD BE RETURNED
                return False
                
        elif isinstance(passenger, BusinessPassenger):
            if to_aircraft.business_seats > to_aircraft.business_occupied:
                passenger.seat_multiplier *= 1.2
                passenger.seat_assigned = 2
                to_aircraft.business_occupied += 1
                fee = to_aircraft.operation_fee * to_aircraft.aircraft_multiplier * 1.5
            elif to_aircraft.economy_seats > to_aircraft.economy_occupied:
                passenger.seat_multiplier *= 0.6
                passenger.seat_assigned = 1
                to_aircraft.economy_occupied += 1
                fee = to_aircraft.operation_fee * to_aircraft.aircraft_multiplier * 1.2
            else:
                #print("passenger loading error: no seats available.")
                self.profit -= from_aircraft.operation_fee
                return False

        elif isinstance(passenger, EconomyPassenger):
            if to_aircraft.economy_seats > to_aircraft.economy_occupied:
                passenger.seat_multiplier *= 0.6
                passenger.seat_assigned = 1
                to_aircraft.economy_occupied += 1
                fee = to_aircraft.operation_fee * to_aircraft.aircraft_multiplier * 1.2
            else:
                #print("passenger loading error: no seats available.")
                self.profit -= from_aircraft.operation_fee
                return False

        passenger.connection_multiplier *= 0.8
        to_aircraft.weight -= passenger.weight
        from_aircraft.weight += passenger.weight

        del from_aircraft.passengers[passenger.ID]
        to_aircraft.passengers.update({passenger.ID: passenger})

        return True

    def passenger_loading(self, passenger, aircraft, airport):
        if (aircraft.current_airport.ID != airport.ID):
            #print("passenger loading error: aircraft is not at the correct airport") #TODO: MIGHT BE ERROR
            self.profit -= aircraft.operation_fee
            return False

        if aircraft.weight + passenger.weight > aircraft.max_weight:
            #print("passenger loading error: aircraft weight exceeded.")
            self.profit -= aircraft.operation_fee
            return False
        
        if passenger.seat_assigned != -1:
            #print("passenger loading error: passenger was still assigned to a seat") #TODO: MAYBE REDUNDANT
            self.profit -= aircraft.operation_fee
            return False

        if isinstance(passenger, LuxuryPassenger) or isinstance(passenger, FirstClassPassenger):
            if aircraft.first_seats > aircraft.first_occupied:
                passenger.seat_multiplier = 3.2
                passenger.seat_assigned = 3
                aircraft.first_occupied += 1
                fee = aircraft.operation_fee * aircraft.aircraft_multiplier * 2.5
            elif aircraft.business_seats > aircraft.business_occupied:
                passenger.seat_multiplier = 1.2
                passenger.seat_assigned = 2
                aircraft.business_occupied += 1
                fee = aircraft.operation_fee * aircraft.aircraft_multiplier * 1.5
            elif aircraft.economy_seats > aircraft.economy_occupied:
                passenger.seat_multiplier = 0.6
                passenger.seat_assigned = 1
                aircraft.economy_occupied += 1
                fee = aircraft.operation_fee * aircraft.aircraft_multiplier * 1.2
            else:
                #print("passenger loading error: no seats available.")
                self.profit -= aircraft.operation_fee
                return False
                
        elif isinstance(passenger, BusinessPassenger):
            if aircraft.business_seats > aircraft.business_occupied:
                passenger.seat_multiplier = 1.2
                passenger.seat_assigned = 2
                aircraft.business_occupied += 1
                fee = aircraft.operation_fee * aircraft.aircraft_multiplier * 1.5
            elif aircraft.economy_seats > aircraft.economy_occupied:
                passenger.seat_multiplier = 0.6
                passenger.seat_assigned = 1
                aircraft.economy_occupied += 1
                fee = aircraft.operation_fee * aircraft.aircraft_multiplier * 1.2
            else:
                #print("passenger loading error: no seats available.")
                self.profit -= aircraft.operation_fee
                return False

        elif isinstance(passenger, EconomyPassenger):
            if aircraft.economy_seats > aircraft.economy_occupied:
                passenger.seat_multiplier = 0.6
                passenger.seat_assigned = 1
                aircraft.economy_occupied += 1
                fee = aircraft.operation_fee * aircraft.aircraft_multiplier * 1.2
            else:
                #print("passenger loading error: no seats available.")
                self.profit -= aircraft.operation_fee
                return False

        self.profit -= fee
        del airport.passengers[passenger.ID]
        aircraft.passengers.update({passenger.ID: passenger})
        aircraft.weight += passenger.weight
        
        return True

    def passenger_unloading(self, passenger, aircraft, airport):
        if (aircraft.current_airport.ID != airport.ID):
            #print("passenger unloading error: aircraft is not at the correct airport") #TODO: MIGHT BE ERROR
            self.profit -= aircraft.operation_fee
            return False 
        
        if airport.ID not in passenger.destinations:
            #print("passenger unloading error: passenger is not at one of the destinations") #TODO: MIGHT BE ERROR
            self.profit -= aircraft.operation_fee
            return False

        if passenger.seat_assigned == 1:
            aircraft.economy_occupied -= 1
            ticket_price = 1.0
        elif passenger.seat_assigned == 2:
            aircraft.business_occupied -= 1
            ticket_price = 2.8
        elif passenger.seat_assigned == 3:
            aircraft.first_occupied -= 1
            ticket_price = 7.5
        else:
            self.profit -= aircraft.operation_fee
            return False

        previous_destionation_object = self.airports.get(passenger.previous_destination)
        if isinstance(previous_destionation_object, HubAirport):
            if isinstance(airport, HubAirport):
                ticket_price *= 0.5
            elif isinstance(airport, MajorAirport):
                ticket_price *= 0.7
            elif isinstance(airport, RegionalAirport):
                ticket_price *= 1.0
        elif isinstance(previous_destionation_object, MajorAirport):
            if isinstance(airport, HubAirport):
                ticket_price *= 0.6
            elif isinstance(airport, MajorAirport):
                ticket_price *= 0.8
            elif isinstance(airport, RegionalAirport):
                ticket_price *= 1.8
        elif isinstance(previous_destionation_object, RegionalAirport):
            if isinstance(airport, HubAirport):
                ticket_price *= 0.9
            elif isinstance(airport, MajorAirport):
                ticket_price *= 1.6
            elif isinstance(airport, RegionalAirport):
                ticket_price *= 3.0

        
        if isinstance(passenger, EconomyPassenger):
            ticket_price *= 0.6
        elif isinstance(passenger, BusinessPassenger):
            ticket_price *= 1.2
        elif isinstance(passenger, FirstClassPassenger):
            ticket_price *= 3.2
        elif isinstance(passenger, LuxuryPassenger):
            ticket_price *= 15.0
        else:
            #print("passenger unloading error: passenger is not a passenger") #TODO MIGHT BE REDUNTANT
            self.profit -= aircraft.operation_fee
            return False

        ticket_price *= self.airports.get(passenger.previous_destination).distance(airport) * aircraft.aircraft_multiplier * passenger.connection_multiplier * passenger.seat_multiplier
        ticket_price *= (1 + (passenger.baggage_count * 0.05))

        destination_index = passenger.destinations.index(airport.ID)
        passenger.destinations = passenger.destinations[destination_index:]
        passenger.previous_destination = airport
        passenger.seat_assigned = -1 # might need to reset some multipliers and whatnot

        self.profit += ticket_price
        del aircraft.passengers[passenger.ID]
        airport.passengers.update({passenger.ID: passenger})
        aircraft.weight -= passenger.weight
    
        return True


    def seat_assignment(self, aircraft, economy, business, first):
        if aircraft.economy_occupied > economy:
            print("seat assignment error: occupied economy seats violated.")
            return False
        if aircraft.business_occupied > business:
            print("seat assignment error: occupied business seats violated.")
            return False
        if aircraft.first_occupied > first:
            print("seat assignment error: occupied first seats violated.")
            return False
    
        if economy * 1 + business * 3 + first * 8 > aircraft.floor_area:
            print("seat assignment error: aircraft floor area exceeded.")
            return False
        
        aircraft.economy_seats = economy
        aircraft.business_seats = business
        aircraft.first_seats = first
        return True


    def fuel_loading(self, aircraft, fuel_amount):
        if aircraft.fuel_capacity < aircraft.fuel + fuel_amount < 0:
            print("fuel loading error: fuel capacity exceeded.")
            return False
        if aircraft.weight + fuel_amount * aircraft.fuel_weight > aircraft.max_weight:
            print("fuel loading error: aircraft weight exceeded.")
            return False

        aircraft.fuel += fuel_amount
        aircraft.weight += fuel_amount * aircraft.fuel_weight
        
        fuel_cost = 0.0
        if (fuel_amount > 0):
            fuel_cost = fuel_amount * aircraft.current_airport.fuel_cost
        self.profit -= fuel_cost

        return True


    def aircraft_creation(self, airport, aircraft_type):
        if aircraft_type == 0:
            new_aircraft = PropAircraft(airport, self.prop_op_fee, len(self.aircrafts))
        elif aircraft_type == 1:
            new_aircraft = WideAircraft(airport, self.wide_op_fee, len(self.aircrafts))
        elif aircraft_type == 2:
            new_aircraft = RapidAircraft(airport, self.rapid_op_fee, len(self.aircrafts))
        elif aircraft_type == 3:
            new_aircraft = JetAircraft(airport, self.jet_op_fee, len(self.aircrafts))

        if (len(self.aircrafts) + 1 > self.max_aircrafts):
            print("aircraft creation error: airline aircraft limit reached.")
            return False

        if (airport.current + 1 > airport.capacity):
            print("aircraft creation error: airport capacity exceeded.")
            return False
        
        airport.current += 1
        self.aircrafts.update({new_aircraft.ID: new_aircraft})
        return True

if __name__ == "__main__":
    runmode = 0
    if len(sys.argv) == 4:
        runmode = int(sys.argv[3])

    test = testing(sys.argv[1], sys.argv[2], runmode)
    test.run()
