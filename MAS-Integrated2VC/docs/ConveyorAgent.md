# ConveyorAgent.java Documentation

## Overview

`ConveyorAgent.java` is a part of the MultiAgentSystem that represents the state and behavior of a conveyor in the system. It is responsible for keeping track of each conveyor's status and making this information available to both the agent logic and the simulation environment.

## What It Does

- **Tracks Conveyor State:** It stores information about each conveyor, such as whether it has produced a product and its unique identifier.
- **Provides Access:** It allows other parts of the system to check if a conveyor has produced a product, to update this status, and to get information about the conveyor.
- **Connects to OPC UA Nodes:** The status of each conveyor is linked to a node in the OPC UA server, so changes are immediately visible to both the agent system and the simulation.
- **Controls Production:** It can start or stop the production status of a conveyor, reflecting changes in real time.

## How It Is Used

- **Conveyor Management:** Whenever a conveyor is created in the system, a `ConveyorAgent` object is created for it. This object is then used to keep track of the conveyor's status.
- **State Updates:** As products are produced or removed from the conveyor, the `ConveyorAgent` updates the produced status. These updates are reflected in the OPC UA server, so the simulation and agent logic always have the latest information.
- **Data Access:** Other parts of the MultiAgentSystem, such as agent logic or the user interface, use the `ConveyorAgent` to read or change a conveyor's state.

## Concepts

- **Produced Status:** The main property of a conveyor is whether it has produced a product. This status is updated as products are created or picked up.
- **Synchronization:** Because the produced status is connected to an OPC UA node, any change is immediately visible to both the simulation and the agent system.

## Data Flow

1. **Initialization:** When a conveyor is added to the system, a `ConveyorAgent` is created and its properties are set to their initial values.
2. **Updates:** As the simulation runs, the produced status is updated to reflect the conveyor's activity.
3. **External Access:** The simulation and agent logic can read or write this status at any time, ensuring both always have the current state.

