# Server.java Documentation

## Overview

`Server.java` is the entry point for starting the MultiAgentSystem and its connection to the simulation environment. It is responsible for launching the OPC UA server, initializing the agent logic, and starting the user interface. This file brings together all the main components and ensures they are running and connected.

## What It Does

- **Starts the OPC UA Server:** It sets up and launches the OPC UA server, which is used for communication between the MultiAgentSystem and the simulation (VCSimulation).
- **Initializes the Namespace:** It creates the custom namespace that organizes all the data nodes (robots, conveyors, system properties) that will be shared.
- **Connects the Client:** It creates a client connection to the OPC UA server, which is used by the user interface to read and write data.
- **Launches the User Interface:** It starts the graphical user interface (RobotControlUI) so users can monitor and control the system.
- **Starts the Agent Container:** It launches the agent logic, which manages the behavior and coordination of robots and conveyors.
- **Keeps the System Running:** It ensures that all components remain active and connected for the duration of the simulation.

## How It Is Used

- **System Startup:** When the MultiAgentSystem is started, `Server.java` is run. This sets up all the necessary components and connections.
- **Central Coordination:** It acts as the central coordinator, making sure the server, agents, and user interface are all running and able to communicate.
- **Continuous Operation:** The file contains logic to keep the system running indefinitely, so the simulation and agent logic can operate without interruption.

## Concepts

- **OPC UA Server:** The main communication channel between the agent system and the simulation.
- **Namespace:** The organizational structure for all shared data.
- **User Interface:** The graphical window for monitoring and control.
- **Agent Container:** The part of the system that manages the logic and behavior of all agents.

## Data Flow

1. **Initialization:** The server, namespace, client, user interface, and agent container are all started.
2. **Communication:** Data flows between the agent logic, user interface, and simulation through the OPC UA server.
3. **Continuous Operation:** The system remains active, allowing real-time updates and control.

