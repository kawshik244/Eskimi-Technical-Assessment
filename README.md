# Eskimi Engineering - Backend Engineer Tasks

This repository contains the solutions for the Eskimi Backend Engineer technical assessment. The solution consists of three distinct Spring Boot applications, each addressing a specific problem statement as defined in the requirements.

##  Repository Structure

The project is divided into three isolated modules:

* [cite_start]**`dateCalculation/`**: Task 1 - Calculates the absolute difference between two dates without using Java's built-in date libraries[cite: 3, 4].
* [cite_start]**`NumberOfWords/`**: Task 2 - Converts numeric values (integers and decimals) into their English word representation[cite: 6].
* [cite_start]**`dhakaWeather/`**: Task 3 - Retrieves weather data for Dhaka, calculates statistics (Min/Max/Avg), and converts values to text[cite: 15].

##  Technology Stack

* [cite_start]**Language:** Java 17 (JDK 17) [cite: 40]
* **Framework:** Spring Boot 3.2.0
* **Build Tool:** Maven
* [cite_start]**Containerization:** Docker 
* [cite_start]**Testing:** JUnit 5 & Mockito [cite: 43]

##  Getting Started

### Prerequisites
Ensure you have the following installed on your machine:
1.  **Java 17 Development Kit (JDK)**
2.  **Docker Desktop** (Running)
3.  **Git**

### Global Build Instructions
Each folder contains its own `pom.xml` and build wrapper. You can build and run each service independently.

Please navigate to the specific directories (`dateCalculation`, `numWords`, `dhakaWeather`) to view detailed instructions on how to build, test, and containerize each application.
