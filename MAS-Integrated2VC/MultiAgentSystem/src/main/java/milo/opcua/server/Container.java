package milo.opcua.server;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

/**
 * Simple Container - Fixed agents, no template configuration
 * Agents are code components, not configuration data
 */
public class Container {
    public static void startContainer() {
        try {
            Runtime runtime = Runtime.instance();
            Properties properties = new ExtendedProperties();
            properties.setProperty(Profile.GUI, "true");

            Profile profile = new ProfileImpl(properties);
            AgentContainer agentContainer = runtime.createMainContainer(profile);

            // Start fixed agents (these are code, not configuration)
            AgentController robotAgent = agentContainer.createNewAgent("RobotAgent", 
                    "milo.opcua.server.RobotAgent", new Object[]{});
            robotAgent.start();
            System.out.println("Started RobotAgent");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
