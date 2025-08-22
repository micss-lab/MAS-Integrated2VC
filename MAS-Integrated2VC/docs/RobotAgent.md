# RobotAgent.java Documentation

## Overview

`RobotAgent.java` is a part of the MultiAgentSystem that represents the behavior and decision-making process of a robot in the system. It acts as the logic controller for a robot, determining what actions the robot should take based on the current state of the system and the tasks it needs to perform.

## What It Does

- **Controls Robot Behavior:** It defines how a robot should act in different situations, such as when to pick up a product, when to deliver it, and when to move to a new location.
- **Monitors System State:** It regularly checks the status of conveyors, products, and other robots to decide what the robot should do next.
- **Assigns Tasks:** When a new product is available or a task needs to be done, it decides which robot should handle it and updates the robot's target accordingly.
- **Handles Movement:** It manages the robot's movement from one location to another, including picking up and dropping off products, and returning to idle locations when not in use.
- **Updates Status:** It updates the robot's properties (such as location, target, and whether it is carrying a product) so that the rest of the system and the simulation always know what the robot is doing.

## How It Is Used

- **Agent Lifecycle:** When the system starts, each robot is assigned a `RobotAgent`. This agent runs continuously, checking the system state and making decisions for its robot.
- **Task Assignment:** When a product is produced on a conveyor, the `RobotAgent` decides which robot should pick it up and sets the robot's target to the conveyor.
- **Product Handling:** The agent monitors when the robot reaches a conveyor, picks up a product, and then assigns a drop-off location. After delivery, it sends the robot back to an idle location.
- **Coordination:** The agent ensures that robots do not interfere with each other and that tasks are distributed efficiently.

## Concepts

- **Behavior Loop:** The agent runs in a loop, regularly checking the system and updating the robot's actions.
- **Task Management:** It keeps track of which robots are available, which are busy, and assigns tasks based on proximity and availability.
- **State Synchronization:** All changes made by the agent are reflected in the robot's properties, which are shared with the simulation through the OPC UA server.

## Data Flow

1. **Monitoring:** The agent checks the status of conveyors, products, and robots.
2. **Decision Making:** Based on the current state, it decides what the robot should do next.
3. **Action:** It updates the robot's target and other properties to carry out the chosen action.
4. **Feedback:** The simulation and other parts of the system can see these updates and respond accordingly.

