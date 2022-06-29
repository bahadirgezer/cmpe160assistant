# Airline Profit Maximization 
**CMPE160 Object Oriented Programming - Spring 2022 - Student Assistant**
#### CmpE160 Course
* **Instructor:** Tuna Tugcu
* **Teaching Assistant:** Yigit Yildirim

This repository has every file I used during my assistant work for the CMPE160 course at Bogazici University. 

I was tasked with creating the first project for this course. 
This project was designed to introduce students with the object oriented programming paradigm. 
It also had to have some kind of competition aspect to encourage good projects.

## Overview of the Project
Given an input of airports and passengers you must design an airline which maximizes profit.
There are some basic operations for aircrafts and passengers. You can build upon these basic operations.

The project materials which were accessible by students are given in [this](./shared) folder. 
To get started you should first check out the [general description](./shared/project_description.pdf) and [input description](./shared/inputs.pdf) files.
To test your outputs use the correct [validity checker](./shared/shared_to_google_drive/validity%20checker) version for your OS.
To grade your project, you can put your project folder into the [submitted](./grading/grader/submissions/submitted) 
folder then run the [grader](./grading/grader/submissions/grader.py) python code.

### **Fundamental Operations:**
These operations must be logged to the output file. 
* **For Aircrafts:**
	* Create Aircraft
	* Configure Seats
	* Load Fuel to Aircraft
	* Fly Aircraft
* **For Passengers:**
	* Load Passenger
	* Unload Passenger
	* Transfer Passenger
	
### **Types of Aircraft:**
There are 4 types of aircraft each with its own use case. You need to choose the aircrafts you want to use.
* **Widebody:** Long range, high capacity widebody passenger aircraft
* **Rapid:** Medium to long range, medium capacity fast passenger aircraft
* **Jet:** Short range, low capacity luxury passenger jet
* **Prop:** Short range, medium capacity turboprop aircraft

### **Types of Passenger:**
There are 4 types of passenger each with different properties and needs.
* **Economy Passenger** 
* **Business Passenger**
* **First Class Passenger**
* **Luxury Passenger**

### **Types of Airport:**
Different types of airports serve different purposes. 
* **Hub Airport**
* **Major Airport**
* **Regional Airport**

## Important Notes
The fuel consumption function is based off of real aircrafts. It resembles a bathtub curve. 
At short distances the average fuel consumption is high because of the take off fuel consumption. 
There is a sweet spot for fuel consumption in medium ranges.
At distances close to the aircrafts range the average fuel consumption jumps again. There is a sweet spot for fuel consumption. 

Different airport types have their own use case. 
For example, a passenger who wants to go to a regional destination ideally should be transferred to a smaller aircraft at the nearest hub airport. 
I incentivised this behaviour by giving passenger destination lists that follow the hierarcy of the airports.

## Grading
The projects are graded automatically. 
I check the validity of the output log by running the entire simulation by reading the output line by line and doing the operations as I go. 
This way I can check if there is an invalid operation logged to the output. 

The projects are graded based on the validity of the output. 
So, technically an empty output file is valid. To prevent this it is required to do at least one valid unloading operation.

Bonus points are given to airlines with high profits, flight count and unload count.
