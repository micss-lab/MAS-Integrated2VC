package milo.opcua.server;

import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Template-configurable Robot Agent
 * Organized and easy to modify behavior patterns
 */
public class RobotAgent extends Agent {

    // =====================================================================
    // CONFIGURATION - Simple and clean
    // =====================================================================
    private static final int AGENT_INTERVAL = 500; // Fixed interval

    // =====================================================================
    // UTILITY METHODS
    // =====================================================================
    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    // =====================================================================
    // AGENT LIFECYCLE
    // =====================================================================
    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started with interval: " + AGENT_INTERVAL + "ms");
        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        parallelBehaviour.addSubBehaviour(robotBehavior);
        addBehaviour(parallelBehaviour);
    }

    // =====================================================================
    // MAIN ROBOT BEHAVIOR - Well organized
    // =====================================================================
    TickerBehaviour robotBehavior = new TickerBehaviour(this, AGENT_INTERVAL) {
        @Override
        public void onTick() {
            // 1. Display robot status
            displayRobotStatus();

            // 2. Handle conveyor production
            handleConveyorProduction();

            // 3. Handle robot pickup and delivery
            handleRobotOperations();
        }
    };

    // =====================================================================
    // ORGANIZED BEHAVIOR METHODS
    // =====================================================================
    private void displayRobotStatus() {
        for (int i = 0; i < CustomNamespace.robots.size(); i++) {
            RobotTemplate robot = CustomNamespace.robots.get(i);
            System.out.println("Robot " + (i + 1) + " Location: " + robot.getLocation() + ", Next Location: " + robot.getNextLocation());
        }
    }



    private void handleConveyorProduction() {
        checkConveyorProduction();
    }

    private void handleRobotOperations() {
        // Handle product pickup
        for (RobotTemplate robot : CustomNamespace.robots) {
            checkProductPickup(robot);
        }

        // Handle product dropoff
        for (RobotTemplate robot : CustomNamespace.robots) {
            checkProductDropoff(robot);
        }

        // Check for new targets
        for (RobotTemplate robot : CustomNamespace.robots) {
            checkAndSetNewTarget(robot);
        }
    }

    // =====================================================================
    // ROBOT MOVEMENT
    // =====================================================================


    private void checkConveyorProduction() {
        // Check dynamic input conveyors
        for (int i = 0; i < CustomNamespace.getInputConveyors().size(); i++) {
            ConveyorAgent conveyor = CustomNamespace.getInputConveyors().get(i);
            if (conveyor.getProduced()) {
                String targetName = getConveyorTargetName(i + 1);
                setRobotTarget(targetName);
            }
        }
    }

    private String getConveyorTargetName(int conveyorNumber) {
        if (conveyorNumber == 1) {
            return "InputConveyor";
        } else {
            return "InputConveyor #" + conveyorNumber;
        }
    }

    private boolean isInputConveyorTarget(String target) {
        return target.equals("InputConveyor") || target.startsWith("InputConveyor #");
    }

    private void setRobotTarget(String target) {
        // First, check if any robot is already assigned to this target and still needs to pick up
        for (RobotTemplate robot : CustomNamespace.robots) {
            if (robot.getTarget().equals(target) && !robot.isCarryingProduct()) {
                return; // Only skip if robot is still going to this conveyor for pickup
            }
        }

        try {
            // Parse input conveyor locations and idle locations using SystemConfig.COMPONENT_PROPERTIES
            String inputConveyorFile = null;
            String idleFile = null;
            for (SystemConfig.ComponentProperty prop : SystemConfig.COMPONENT_PROPERTIES) {
                if (prop.name.equals("inputconveyorProperties")) inputConveyorFile = prop.jsonFile;
                if (prop.name.equals("idleProperties")) idleFile = prop.jsonFile;
            }
            JSONParser parser = new JSONParser();
            JSONArray inputConveyorLocations = (JSONArray) parser.parse(new FileReader(inputConveyorFile));
            JSONArray idleLocations = (JSONArray) parser.parse(new FileReader(idleFile));

            // Find the target conveyor coordinates
            JSONObject targetConveyor = null;
            int conveyorIndex = -1;

            // More generic approach to find conveyor index
            if (target.equals("InputConveyor")) {
                conveyorIndex = 0;
            } else if (target.startsWith("InputConveyor #")) {
                try {
                    String numberStr = target.substring("InputConveyor #".length());
                    int conveyorNumber = Integer.parseInt(numberStr);
                    conveyorIndex = conveyorNumber - 1; // Convert to 0-based index
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing conveyor number from target: " + target);
                }
            }

            if (conveyorIndex >= 0 && conveyorIndex < inputConveyorLocations.size()) {
                targetConveyor = (JSONObject) inputConveyorLocations.get(conveyorIndex);
            }

            if (targetConveyor == null) return;

            // Find closest available robot
            RobotTemplate closestRobot = null;
            double minDistance = Double.MAX_VALUE;
            int availableCount = 0;

            for (RobotTemplate robot : CustomNamespace.robots) {
                int robotIndex = CustomNamespace.robots.indexOf(robot);
                // Robot is available if: no target OR target is idle location, AND not carrying product
                boolean hasNoTarget = robot.getTarget().isEmpty();
                boolean returningToIdle = robot.getTarget().startsWith("Idle Location");
                boolean isAvailable = (hasNoTarget || returningToIdle) && !robot.isCarryingProduct();

                if (isAvailable) {
                    availableCount++;
                    JSONObject robotLocation = (JSONObject) idleLocations.get(robotIndex);

                    double distance = calculateDistance(
                            ((Number) robotLocation.get("X")).doubleValue(),
                            ((Number) robotLocation.get("Y")).doubleValue(),
                            ((Number) targetConveyor.get("X")).doubleValue(),
                            ((Number) targetConveyor.get("Y")).doubleValue()
                    );

                    if (distance < minDistance) {
                        minDistance = distance;
                        closestRobot = robot;
                    }
                }
            }

            // Assign target to closest robot if one was found
            if (closestRobot != null) {
                int selectedIndex = CustomNamespace.robots.indexOf(closestRobot);
                closestRobot.setTarget(target);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkProductPickup(RobotTemplate robot) {
        String location = robot.getLocation();
        boolean isCarryingProduct = robot.isCarryingProduct();

        // Check for pickup at dynamic input conveyors
        for (int i = 0; i < CustomNamespace.getInputConveyors().size(); i++) {
            String conveyorTargetName = getConveyorTargetName(i + 1);
            if (location.equals(conveyorTargetName)) {
                ConveyorAgent conveyor = CustomNamespace.getInputConveyors().get(i);
                if (conveyor.getProduced()) {
                    System.out.println("Robot picking up product from " + conveyorTargetName);
                    robot.setCarryingProduct(true);
                    conveyor.setProduced(false);
                    break; // Exit loop once we find a match
                }
            }
        }
    }

    private void checkProductDropoff(RobotTemplate robot) {
        String location = robot.getLocation();
        boolean isCarryingProduct = robot.isCarryingProduct();
        String target = robot.getTarget();

        // Check if robot is carrying a product and is at a drop-off location
        if (isCarryingProduct && dropOffConveyorNamesContains(location) && dropOffConveyorNamesContains(target)) {
            // Robot has reached drop-off location, set CarryingProduct to false
            robot.setCarryingProduct(false);
            System.out.println("Robot dropped off product at " + location);
        }
    }

    private boolean dropOffConveyorNamesContains(String location) {
        try {
            String outputConveyorPropertiesString = CustomNamespace.outputconveyorProperties.getValue().getValue().getValue().toString();
            JSONParser parser = new JSONParser();
            JSONArray outputConveyorsArray = (JSONArray) parser.parse(outputConveyorPropertiesString);
            for (Object o : outputConveyorsArray) {
                JSONObject conveyor = (JSONObject) o;
                String name = (String) conveyor.get("Name");
                if (name.equals(location)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    private void checkAndSetNewTarget(RobotTemplate robot) {
        boolean isCarryingProduct = robot.isCarryingProduct();
        String currentTarget = robot.getTarget();
        String currentLocation = robot.getLocation();
        int robotIndex = CustomNamespace.robots.indexOf(robot);

        // When robot is carrying a product and needs a drop-off location
        if (isCarryingProduct && (isInputConveyorTarget(currentTarget) || currentTarget.isEmpty())) {

            try {
                // Get the JSON string from output_conveyor_Properties
                String outputConveyorPropertiesString = CustomNamespace.outputconveyorProperties.getValue().getValue().getValue().toString();
                // Parse the JSON string into a JSONArray
                JSONParser parser = new JSONParser();
                JSONArray outputConveyorsArray = (JSONArray) parser.parse(outputConveyorPropertiesString);
                // Extract Names into a List
                List<String> dropOffConveyors = new ArrayList<>();
                for (Object o : outputConveyorsArray) {
                    JSONObject conveyor = (JSONObject) o;
                    String name = (String) conveyor.get("Name");
                    dropOffConveyors.add(name);
                }
                // Randomly select one conveyor name
                if (!dropOffConveyors.isEmpty()) {
                    Random rand = new Random();
                    String dropOffTarget = dropOffConveyors.get(rand.nextInt(dropOffConveyors.size()));
                    robot.setTarget(dropOffTarget);
                    System.out.println("Set drop-off target " + dropOffTarget + " for robot");
                } else {
                    System.out.println("No drop-off conveyors available.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        // When robot has dropped off product and needs to return to idle location
        else if (!isCarryingProduct && dropOffConveyorNamesContains(currentLocation)) {
            // Set target to corresponding idle location
            String idleLocation = "Idle Location" + (robotIndex == 0 ? "" : " #" + (robotIndex + 1));
            robot.setTarget(idleLocation);
            System.out.println("Robot returning to idle location: " + idleLocation);
        }
        // When robot has reached its idle location
        else if (currentLocation.startsWith("Idle Location")) {
            if (currentTarget.startsWith("Idle Location")) {
                robot.setTarget("");
                System.out.println("Robot reached idle location. Ready for new tasks.");
                checkConveyorProduction();
            }
        }
    }
}