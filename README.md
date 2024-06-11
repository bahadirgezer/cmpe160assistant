# Airline Profit Maximization

**CMPE160 - Object Oriented Programming - Spring 2022 - Assistant Work**

### CmpE160 Course
- **Instructor:** Tuna Tugcu
- **Teaching Assistant:** Yigit Yildirim

This repository contains all the files I used during my assistant work for the CMPE160 course at Bogazici University.

I was tasked with creating the first project for this course, designed to introduce students to the object-oriented programming paradigm with a competitive aspect to encourage quality projects.

## Overview of the Project
The goal is to design an airline that maximizes profit given an input of airports and passengers. Students can build upon basic operations for aircraft and passengers.

### Getting Started
- Project materials accessible by students are in the [shared](./shared) folder.
- Check out the [general description](./shared/project_description.pdf) and [input description](./shared/inputs.pdf) files first.
- To test your outputs, use the appropriate [validity checker](./shared/shared_to_google_drive/validity%20checker) version for your OS.
- To grade your project, place your project folder into the [submitted](./grading/grader/submissions/submitted) folder and run the [grader](./grading/grader/submissions/grader.py) Python code.

### Fundamental Operations
These operations must be logged to the output file.

#### For Aircrafts:
- Create Aircraft
- Configure Seats
- Load Fuel to Aircraft
- Fly Aircraft

#### For Passengers:
- Load Passenger
- Unload Passenger
- Transfer Passenger

### Types of Aircraft
There are 4 types of aircraft, each with its own use case. Choose the aircrafts you want to use.

- **Widebody:** Long range, high capacity widebody passenger aircraft
- **Rapid:** Medium to long range, medium capacity fast passenger aircraft
- **Jet:** Short range, low capacity luxury passenger jet
- **Prop:** Short range, medium capacity turboprop aircraft

### Types of Passenger
There are 4 types of passengers, each with different properties and needs.

- **Economy Passenger**
- **Business Passenger**
- **First Class Passenger**
- **Luxury Passenger**

### Types of Airport
Different types of airports serve different purposes.

- **Hub Airport**
- **Major Airport**
- **Regional Airport**

## Important Notes
- The fuel consumption function is based on real aircraft. It resembles a bathtub curve: high consumption at short distances due to takeoff, a sweet spot in medium ranges, and increased consumption at distances close to the aircraft's range.
- Different airport types have their use cases. For instance, passengers heading to regional destinations should ideally be transferred to smaller aircraft at the nearest hub airport. This behavior is incentivized through the passenger destination lists that follow airport hierarchy.

## Grading
Projects are graded automatically. The validity of the output log is checked by running the entire simulation, reading the output line by line, and performing the operations.

- Projects are graded based on the validity of the output. An empty output file is technically valid, but to prevent this, at least one valid unloading operation is required.
- Bonus points are awarded to airlines with high profits, flight count, and unload count.

## Directory Structure

```
.
├── grading
│   ├── grader
│   │   ├── grading_inputs
│   │   │   ├── new_circle
│   │   │   ├── new_scatter
│   │   │   └── other
│   │   ├── out
│   │   ├── submissions
│   │   │   ├── all-grades
│   │   │   │   ├── class-structure-grades
│   │   │   │   ├── combined
│   │   │   │   ├── compile-grades
│   │   │   │   ├── javadoc-grades
│   │   │   │   └── polished
│   │   │   ├── all-grades-before-objection
│   │   │   │   ├── class-structure-grades
│   │   │   │   ├── combined
│   │   │   │   ├── compile-grades
│   │   │   │   ├── javadoc-grades
│   │   │   │   ├── polished
│   │   │   │   └── terminal-logs
│   │   │   ├── debug-inputs
│   │   │   ├── discarded-inputs
│   │   │   ├── grades
│   │   │   │   └── terminal_logs
│   │   │   ├── inputs
│   │   │   ├── submitted
│   │   │   ├── submitted-graded
│   │   │   └── submitted-objection
│   │   └── validity
│   │       ├── test-log
│   │       │   ├── test_inputs
│   │       │   └── test_outputs
│   │       └── validity checker versions
│   │           ├── linux
│   │           ├── mac (apple silicon)
│   │           ├── mac (intel)
│   │           └── win
│   ├── moss
│   └── test
├── notes
├── shared
│   └── shared_to_google_drive
│       ├── plots
│       │   ├── large
│       │   ├── large_linear
│       │   └── small
│       ├── test cases
│       │   ├── dos
│       │   └── unix
│       └── validity checker
│           ├── linux
│           ├── mac (apple silicon)
│           ├── mac (intel)
│           └── win
└── src
    ├── airline
    │   └── aircraft
    │       └── concrete
    ├── airport
    ├── executable
    ├── interfaces
    └── passenger
```
