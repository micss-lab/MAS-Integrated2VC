# Visual Components Simulation with OPC UA and JADE Integration

## Introduction
This project is a lab exercise demonstrating the integration of a pre-configured Visual Components simulation with an OPC UA server implemented in Java and a JADE multi-agent system. The simulation models an industrial automation scenario with mobile robots, conveyors, feeders, and sinks. The OPC UA server exposes variables representing robot states, pathway locations, and feeder creation intervals, allowing for bidirectional communication between the simulation and external agents.

## Project Overview

### Simulation Environment:
- **Robots**: 4 mobile robots navigating pathways.
- **Conveyors**: 2 input and 2 output conveyors.
- **Feeders and Sinks**: 2 feeders attached to input conveyors and 2 sinks attached to output conveyors.
- **Pathway Areas**: 24 areas for robot navigation.
- **Idle and Charging Locations**: 6 idle locations and 4 charging stations for robots.
- **Mobile Robot Transport Controller**: Manages robot tasks and pathways.
- **Process Flow**: Defined in the Process tab under a Flow Group, linking conveyors, feeders, and sinks via the Mobile Robot Transport Controller.
- **Python Scripting**: Additional scripts in the Modeling tab provide robot location data.

### OPC UA Server and JADE Agents:
- **OPC UA Server**: Implemented in Java using Eclipse Milo, exposing simulation variables.
  - **Connected Variables**:
    - *Simulation to Server*: Robot states and pathway locations.
    - *Server to Simulation*: Feeder creation intervals.
- **JADE Multi-Agent System**: A `RobotAgent` reads robot states and locations from the OPC UA server.

## Features
- **Real-time Monitoring**: The `RobotAgent` reads and logs robot states and locations.
- **Control Over Simulation**: Adjust feeder creation intervals via the OPC UA server.
- **Bidirectional Communication**: The simulation sends data to the server and receives commands.

## How to Run the Project

### Prerequisites
- Java Development Kit (JDK) 8 or higher.
- Maven for building and running the Java project.
- Visual Components with the pre-configured simulation environment.

# Running the OPC UA Server and JADE Agent

## 1. Clone the Repository

## 2. Build the Project
Use Maven to build the project:
```bash
mvn clean install
```

## 3. Run the OPC UA Server
Start the server and the JADE agent.  
```bash
mvn exec:java -Dexec.mainClass="milo.opcua.server.Server"
```
The OPC UA server will start and listen on `opc.tcp://localhost:4840`.  
The JADE agent container will start, and the RobotAgent will begin executing.

## Start the Visual Components Simulation
1. Open the pre-configured simulation in Visual Components.
2. Ensure that the OPC UA connection is active and connected to `opc.tcp://localhost:4840`.
3. Run the simulation.

## Monitoring Output
In the console where the OPC UA server is running, you will see periodic outputs from the RobotAgent, displaying the current state and pathway location of each robot.

## Usage

### Adjusting Feeder Intervals
Modify the feeder creation intervals by changing the values of `Feeder1CreationInterval` and `Feeder2CreationInterval` variables in the OPC UA server.

### Extending the Project
Customize the Java code to add more agents or variables as needed.

## Java Code Overview

### Server.java
- Sets up and starts the OPC UA server.
- Configures security policies and endpoints.
- Registers the custom namespace and starts the JADE agent container.

### CustomNamespace.java
Defines the custom OPC UA namespace with variables:
- Robot states and pathway locations.
- Feeder creation intervals.
- Manages the subscription model for data items.

### Container.java
- Initializes and starts the JADE agent container.
- Creates and starts the RobotAgent.

### RobotAgent.java
- Implements a JADE agent that reads robot variables from the OPC UA server every 500 milliseconds.
- Outputs robot states and pathway locations to the console.

## Conclusion
This project demonstrates the integration of a Visual Components simulation with an OPC UA server and a JADE multi-agent system for industrial automation scenarios. It enables real-time monitoring and control of a simulated environment using standard protocols and agent-based systems.

Feel free to explore the code, modify the agents, or extend the variables to suit different simulation needs.
