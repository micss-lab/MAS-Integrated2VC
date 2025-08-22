# ConfigurationScript.py Documentation

## Overview

`ConfigurationScript.py` is the configuration and setup script for the VCSimulation part of the project. Its main purpose is to define which components (robots, conveyors, pathways, idle locations) will be created in the simulation, what properties and scripts they will have, and how they are initialized. This script acts as the entry point for setting up the simulation environment, using the logic and behaviors defined in `Simulation_Source_Script.py`.

## What It Does

- **Imports the Main Logic:** Loads all the functions and scripts from `Simulation_Source_Script.py`, making them available for use in the configuration process.
- **Defines Component Configurations:** Specifies, in a structured format, all the components that should exist in the simulation, including their names, folders, properties, and scripts.
- **Creates Components:** Uses the configuration data to create each component in the Visual Components environment, assign its properties, and attach the correct behavior script.
- **Initializes the Simulation:** Ensures that all components are set up and ready to interact with the MultiAgentSystem through OPC UA.

## How It Is Used

1. **Importing Logic:** The script first imports all the logic from `Simulation_Source_Script.py`, so all component creation and behavior functions are available.
2. **Defining Components:** It defines a list of components to create, using a structured format (a list of dictionaries). Each component entry includes:
    - The component's name and folder (where to find the 3D model in Visual Components)
    - Any special layout name (for templates)
    - Properties to create (such as OPC UA property names, types, and default values)
    - Numbered properties (for components that have multiple similar properties, like multiple robots or conveyors)
    - The script to attach (from `Simulation_Source_Script.py`)
    - How many sets of numbered properties to create (e.g., number of robots or conveyors)
3. **Component Creation:** The script loops through the configuration and calls the `create_component` function for each entry, which creates the component, sets its properties, and attaches its script.
4. **Simulation Ready:** After running this script, the simulation environment is fully set up with all required components, properties, and behaviors.

## Component Configuration Explained

Each component in the configuration has several fields:
- **name:** The display name of the component (e.g., "Mobile Robot Resource").
- **folder:** The folder in Visual Components where the component's 3D model is located.
- **layout_name:** (Optional) The name to use for the component in the simulation layout.
- **properties:** A list of properties to create for the component. Each property has a name, type (string, number, boolean), and a default value.
- **numbered_properties:** For components that have multiple similar properties (like robots with multiple attributes), this defines the template for those properties.
- **property_sets:** How many sets of numbered properties to create (e.g., 4 for 4 robots).
- **script:** The behavior script to attach to the component (from `Simulation_Source_Script.py`).

## Guidelines for Configuring the Simulation

- **Adding a New Component:** To add a new component, create a new entry in the configuration list with the required fields. Specify the name, folder, properties, and script.
- **Setting Properties:** Use the `properties` field to define any OPC UA or simulation properties the component should have. Set the type and default value for each.
- **Numbered Properties:** For components that need multiple similar properties (like multiple robots), use `numbered_properties` and set `property_sets` to the desired number.
- **Attaching Scripts:** Assign the correct script from `Simulation_Source_Script.py` to the `script` field. This determines how the component will behave in the simulation.
- **Templates and Layout Names:** Use `layout_name` to specify a template or special name for the component in the simulation layout if needed.
- **Running the Script:** When you run `ConfigurationScript.py`, it will create all components as defined, set their properties, and attach their scripts. The simulation will then be ready to run and interact with the MultiAgentSystem.

## Example Component Entry

```
{
    "name": "Mobile Robot Resource",
    "folder": "Mobile Robots",
    "layout_name": "_Template_Mobile_Robot_Resource",
    "properties": [
        {"name": "robotQuantity", "type": "number", "default": 0},
        {"name": "initialPositions", "type": "string", "default": ""}
    ],
    "numbered_properties": [
        {"name_template": "location", "type": "string", "default": "initial"},
        ...
    ],
    "property_sets": 4,
    "script": Robot
}
```

