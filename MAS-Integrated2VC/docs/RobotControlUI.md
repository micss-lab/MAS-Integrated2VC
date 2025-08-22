# RobotControlUI.java Documentation

## Overview

`RobotControlUI.java` is a part of the MultiAgentSystem that provides a graphical user interface (GUI) for monitoring and controlling the robots and conveyors in the system. It allows users to see the current state of the system and to interact with it by changing certain values or triggering actions.

## What It Does

- **Displays System State:** It shows information about all robots and conveyors, such as their locations, statuses, battery levels, and whether they are carrying products.
- **Allows User Interaction:** Users can change certain properties, such as the number of robots or conveyors, set targets for robots, or update the produced status of conveyors.
- **Updates in Real Time:** The interface regularly refreshes to show the latest data from the system, so users always see the current state.
- **Connects to OPC UA:** It communicates with the OPC UA server to read and write data, ensuring that any changes made in the UI are reflected in the system and vice versa.
- **Shows Statistics:** It can display statistics, such as how many products have been delivered and how long deliveries have taken.

## How It Is Used

- **Monitoring:** Users can use the UI to watch what is happening in the system, including where each robot is, what it is doing, and the status of each conveyor.
- **Control:** Users can interact with the system by changing values in the UI, such as updating the number of robots, setting robot targets, or marking conveyors as having produced a product.
- **Feedback:** The UI provides immediate feedback on any changes, showing updated values and statistics as the system runs.

## Concepts

- **Panels and Tabs:** The UI is organized into panels and tabs, grouping related information together for easier viewing and control.
- **Property Fields:** Each robot and conveyor has fields showing its properties, which can be updated by the user.
- **Synchronization:** All changes made in the UI are sent to the OPC UA server, so the rest of the system and the simulation are kept up to date.

## Data Flow

1. **Initialization:** When the UI starts, it connects to the OPC UA server and loads the current state of all robots and conveyors.
2. **User Interaction:** Users can change values or trigger actions using the UI.
3. **Updates:** The UI regularly refreshes to show the latest data from the system.
4. **Synchronization:** Any changes made in the UI are sent to the OPC UA server, and any changes in the system are shown in the UI.


