# Simulation_Source_Script.py Documentation

## Overview

`Simulation_Source_Script.py` is the main script for the VCSimulation part of the project. It is responsible for creating, configuring, and controlling all the components in the Visual Components simulation environment. This script manages the logic for robots, conveyors, pathways, idle locations, and their interactions. It also handles the connection and synchronization with the MultiAgentSystem through the OPC UA server.

## Main Responsibilities

- **Component Creation:** Sets up all the necessary components (robots, conveyors, pathways, idle locations) in the simulation based on configuration data.
- **Behavior Scripts:** Assigns and manages scripts for each type of component, defining how they behave and interact.
- **OPC UA Integration:** Handles reading from and writing to the OPC UA server, ensuring real-time synchronization with the MultiAgentSystem.
- **Task and Movement Logic:** Implements the logic for robot task assignment, movement, collision avoidance, and product handling.

## Component Scripts and Their Roles

### 1. Pathway Area
- **Purpose:** Represents the routes that robots can use to move between locations.
- **Behavior:** Reads pathway configuration from OPC UA, creates visual pathway objects in the simulation, and updates their positions and properties. Pathways are used by robots to plan and execute their movements.

### 2. Output Conveyor
- **Purpose:** Represents conveyors where products are delivered by robots.
- **Behavior:** Reads conveyor configuration from OPC UA, creates conveyor objects, and updates their positions. Handles the appearance and removal of output conveyors in the simulation.

### 3. Input Conveyor
- **Purpose:** Represents conveyors that produce products for robots to pick up.
- **Behavior:** Reads input conveyor configuration from OPC UA, creates and positions input conveyors, and manages the production of products. When a product is produced, it is made available for robots to pick up. The script also updates the produced status and product type for each conveyor.

### 4. Idle Location
- **Purpose:** Represents locations where robots wait when they are not assigned a task.
- **Behavior:** Reads idle location configuration from OPC UA, creates idle location objects, and updates their positions. Robots are sent to idle locations when they are not carrying products or assigned to a task.

### 5. Mobile Robot Resource (Robot)
- **Purpose:** Represents the robots that move products between conveyors.
- **Behavior:**
    - **Task Assignment:** Decides which robot should pick up a product based on availability and proximity.
    - **Movement:** Uses A* pathfinding to plan the shortest route through pathways to the target location (input conveyor, output conveyor, or idle location).
    - **Path Planning:** Considers pathway geometry, obstacles, and other robots. Calculates entry, exit, and intermediate points for smooth navigation.
    - **Collision Avoidance:** Implements multiple strategies to prevent robots from colliding, including side-by-side movement, bypass maneuvers, and velocity obstacle checks. Adjusts robot paths and speeds dynamically based on the positions and movements of other robots.
    - **Product Handling:** Picks up products from input conveyors, carries them, and drops them off at output conveyors. Updates the carried product status and synchronizes this with the OPC UA server.
    - **Coordination:** Handles situations where multiple robots need to coordinate their movements, such as in intersections or when approaching the same conveyor.
    - **State Synchronization:** Continuously updates robot properties (location, next location, battery level, carrying status, etc.) in the OPC UA server so the MultiAgentSystem always has the latest information.

## How It Is Used

- **Initialization:** When the simulation starts, the script reads configuration data and creates all required components. Each component is assigned its script and properties.
- **Simulation Loop:** The script runs continuously, updating the state of all components, handling robot movements, product production, and delivery.
- **OPC UA Communication:** The script reads from and writes to the OPC UA server, ensuring that all changes in the simulation are reflected in the MultiAgentSystem and vice versa.
- **User Interaction:** The simulation can be observed and, in some cases, controlled through the Visual Components interface, but most logic is automated by the script.

## Concepts

- **A* Pathfinding:** Robots use the A* algorithm to find the shortest and most efficient path through the network of pathways, taking into account obstacles and other robots.
- **Dynamic Task Assignment:** Robots are assigned tasks based on their current state and proximity to products or destinations.
- **Collision Avoidance:** Multiple strategies are used to prevent robots from colliding, including adjusting paths, speeds, and using side-by-side or bypass maneuvers.
- **Real-Time Synchronization:** All component states are kept up to date with the MultiAgentSystem through OPC UA, allowing for coordinated decision-making and simulation.

## Data Flow

1. **Configuration Loading:** Reads JSON files and OPC UA nodes to get the initial setup for all components.
2. **Component Creation:** Sets up all robots, conveyors, pathways, and idle locations in the simulation.
3. **Behavior Execution:** Each component runs its script, handling its own logic and interactions.
4. **State Updates:** All changes are synchronized with the OPC UA server, keeping the MultiAgentSystem informed.
5. **Continuous Operation:** The simulation loop keeps running, updating states, handling tasks, and managing interactions.


