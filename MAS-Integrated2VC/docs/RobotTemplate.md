# RobotTemplate.java Documentation

## Overview

`RobotTemplate.java` is a part of the MultiAgentSystem that represents the structure and state of a single robot in the system. It acts as a container for all the information and properties related to a robot, making it possible to keep track of each robot's current status and to update its data as the simulation runs.

## What It Does

- **Holds Robot Data:** It stores all the key properties of a robot, such as its location, next location, battery level, target, whether it is stopped, its priority, whether it is carrying a product, what product it is carrying, and its speed.
- **Provides Access:** It offers a way to get and set each of these properties. This means other parts of the system can easily check a robot's current state or update it as needed.
- **Connects to OPC UA Nodes:** Each property is linked to a node in the OPC UA server, so changes to a robot's state are immediately available to both the agent system and the simulation.

## How It Is Used

- **Robot Management:** Whenever a robot is created in the system, a `RobotTemplate` object is created for it. This object is then used to keep track of all the robot's properties.
- **State Updates:** As the robot moves, picks up products, or changes its status, the corresponding properties in the `RobotTemplate` are updated. These updates are reflected in the OPC UA server, so the simulation and agent logic always have the latest information.
- **Data Access:** Other parts of the MultiAgentSystem, such as agent logic or the user interface, use the `RobotTemplate` to read or change a robot's state.

## Concepts

- **Properties:** Each robot has several properties (location, battery, target, etc.) that describe its current state. These are stored and managed by the `RobotTemplate`.
- **Synchronization:** Because each property is connected to an OPC UA node, any change is immediately visible to both the simulation and the agent system.

## Data Flow

1. **Initialization:** When a robot is added to the system, a `RobotTemplate` is created and its properties are set to their initial values.
2. **Updates:** As the simulation runs, the properties are updated to reflect the robot's actions and status.
3. **External Access:** The simulation and agent logic can read or write these properties at any time, ensuring both always have the current state.


