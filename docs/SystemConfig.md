# SystemConfig.java Documentation

## Overview

`SystemConfig.java` is the main configuration file for the MultiAgentSystem. It defines all the key parameters, settings, and data sources that control how the agent system is set up and operates. This file acts as the central place where you specify how many robots and conveyors there are, what properties they have, and which data files are used for layout and system properties.

## What It Does

- **Defines System Quantities:** Sets the number of robots and input conveyors in the system.
- **Specifies Server Settings:** Sets the OPC UA server's port, name, and namespace URI.
- **Describes Robot Properties:** Lists all the attributes each robot will have (such as location, battery level, target, etc.), their data types, and default values.
- **Describes Conveyor Properties:** Lists all the attributes for input conveyors (such as produced status, product type, etc.), their data types, and default values.
- **Lists Component Properties:** Specifies which JSON files are used for system-wide properties like pathways, idle locations, and conveyor layouts.
- **Provides Configuration Classes:** Contains helper classes for defining and generating property names and node IDs for robots, conveyors, and system properties.

## How It Is Used

1. **System Initialization:** When the MultiAgentSystem starts, it reads all the values and settings from `SystemConfig.java` to determine how to set up the system.
2. **Node Creation:** The configuration is used to create all the necessary nodes in the OPC UA server for robots, conveyors, and system properties.
3. **Data Loading:** The file tells the system which JSON files to load for pathway layouts, idle locations, and conveyor properties.
4. **Property Management:** All robot and conveyor properties are created and managed according to the definitions in this file.

## Key Sections and How to Configure Them

### 1. Basic System Quantities
- **NUM_ROBOTS:** Set this to the number of robots you want in the system.
- **NUM_INPUT_CONVEYORS:** Set this to the number of input conveyors.

### 2. Server Configuration
- **SERVER_PORT:** The port number for the OPC UA server (default is 4840).
- **SERVER_NAME:** The display name for the server.
- **NAMESPACE_URI:** The unique identifier for the OPC UA namespace.

### 3. Robot Configuration
- **ROBOTS:** A list of `RobotConfig` objects, each defining a property for robots. For each property, specify:
    - `name`: The property name (e.g., "location").
    - `displayName`: How it will be shown in the UI or logs.
    - `dataType`: The data type (e.g., string, integer, boolean).
    - `defaultValue`: The initial value for this property.
- **How to Add/Remove Properties:** To add a new property to all robots, add a new `RobotConfig` entry. To remove a property, delete its entry.

### 4. Conveyor Configuration
- **INPUT_CONVEYORS:** A list of `ConveyorConfig` objects, each defining a property for input conveyors. For each property, specify:
    - `name`: The property name (e.g., "produced").
    - `displayName`: How it will be shown in the UI or logs.
    - `dataType`: The data type (e.g., string, integer, boolean).
    - `defaultValue`: The initial value for this property.
- **How to Add/Remove Properties:** To add a new property to all input conveyors, add a new `ConveyorConfig` entry. To remove a property, delete its entry.

### 5. Component Properties
- **COMPONENT_PROPERTIES:** A list of `ComponentProperty` objects, each specifying:
    - `name`: The property name (e.g., "pathwayProperties").
    - `nodeId`: The unique identifier for the OPC UA node.
    - `jsonFile`: The JSON file to load for this property.
- **How to Add/Remove Properties:** To add a new system-wide property, add a new `ComponentProperty` entry. To remove a property, delete its entry.

### 6. Configuration Classes
- **RobotConfig:** Used to define robot properties. Includes a method to generate unique node IDs for each robot.
- **ConveyorConfig:** Used to define conveyor properties. Includes a method to generate unique node IDs for each conveyor.
- **ComponentProperty:** Used to define system-wide properties and their data sources.

## Guidelines for Configuring the System

- **Changing the Number of Robots/Conveyors:** Update `NUM_ROBOTS` and `NUM_INPUT_CONVEYORS` to match your scenario.
- **Customizing Properties:** Edit the `ROBOTS` and `INPUT_CONVEYORS` lists to add, remove, or change properties for robots and conveyors.
- **Updating Data Files:** Change the `jsonFile` fields in `COMPONENT_PROPERTIES` to point to different JSON files if you want to use new layouts or property sets.
- **Server Settings:** Make sure the `SERVER_PORT`, `SERVER_NAME`, and `NAMESPACE_URI` match your intended OPC UA server configuration.

## Data Flow

1. **Startup:** The system reads all configuration values from `SystemConfig.java`.
2. **Node and Property Creation:** Nodes and properties are created in the OPC UA server based on these settings.
3. **Data Loading:** JSON files specified in `COMPONENT_PROPERTIES` are loaded to set up pathways, idle locations, and conveyors.
4. **Operation:** The system uses these settings to manage all robots, conveyors, and system properties during simulation.

