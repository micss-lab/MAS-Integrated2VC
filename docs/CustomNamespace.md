# CustomNamespace.java Documentation

## Overview

`CustomNamespace.java` is a central part of the MultiAgentSystem. Its main role is to define and organize all the data and properties that need to be shared between the agent-based control system and the simulation environment. It acts as a bridge, making sure that all relevant information about robots, conveyors, and the system itself is available and structured in a way that both the MultiAgentSystem and the simulation (VCSimulation) can access and update it.

## What It Does

- **Defines the Data Structure:** It sets up the structure for all the information that needs to be shared, such as robot states, conveyor statuses, and system properties.
- **Organizes Components:** It keeps track of all robots and conveyors in the system, storing them in organized lists.
- **Loads Configuration:** It reads configuration data from JSON files (like robot positions, conveyor locations, and pathway layouts) and uses this data to create the necessary nodes and properties.
- **Creates Nodes:** For every robot, conveyor, and system property, it creates a corresponding node that can be accessed and updated through the OPC UA server.
- **Handles Updates:** It manages updates to these nodes, so that changes in the simulation or the agent logic are reflected in real time.
- **Schedules Background Tasks:** It can run background tasks, such as gradually reducing the battery level of robots over time.

## How It Is Used

- **Initialization:** When the MultiAgentSystem starts, `CustomNamespace.java` is initialized. It sets up all the folders, nodes, and properties based on the current configuration.
- **Data Sharing:** All the data that needs to be shared with the simulation is exposed through this namespace. The simulation can read and write to these nodes using the OPC UA protocol.
- **Component Management:** Whenever a new robot or conveyor is added (according to the configuration), `CustomNamespace.java` creates the necessary nodes and keeps them organized.
- **Property Updates:** If a property (like a robot's location or a conveyor's status) changes, this change is updated in the corresponding node, making it visible to both the agent system and the simulation.
- **Legacy Support:** It maintains some references for backward compatibility, so older parts of the system can still access the data they expect.

## Concepts

- **Namespace:** A way to group and organize all the data nodes so they don't conflict with other systems.
- **Nodes:** Individual pieces of data (like a robot's battery level or a conveyor's produced status) that can be accessed and updated.
- **Folders:** Used to organize nodes into logical groups (for example, all robots or all conveyors).
- **System Properties:** General settings and data about the whole system, such as the number of robots or the layout of pathways.

## Data Flow

1. **Configuration Loading:** Reads JSON files to get the initial setup for robots, conveyors, pathways, and idle locations.
2. **Node Creation:** For each item in the configuration, creates a node in the namespace.
3. **Real-Time Updates:** As the simulation runs, these nodes are updated to reflect the current state of the system.
4. **External Access:** The simulation and other external systems can read from and write to these nodes using OPC UA.

