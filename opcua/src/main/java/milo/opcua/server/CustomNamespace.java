package milo.opcua.server;

import com.google.common.collect.ImmutableSet;
import org.eclipse.milo.opcua.sdk.core.AccessLevel;
import org.eclipse.milo.opcua.sdk.server.OpcUaServer;
import org.eclipse.milo.opcua.sdk.server.api.DataItem;
import org.eclipse.milo.opcua.sdk.server.api.ManagedNamespace;
import org.eclipse.milo.opcua.sdk.server.api.MonitoredItem;
import org.eclipse.milo.opcua.sdk.server.model.nodes.objects.FolderTypeNode;
import org.eclipse.milo.opcua.sdk.server.nodes.UaFolderNode;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNode;
import org.eclipse.milo.opcua.sdk.server.nodes.UaNodeContext;
import org.eclipse.milo.opcua.sdk.server.nodes.UaVariableNode;
import org.eclipse.milo.opcua.sdk.server.util.SubscriptionModel;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;


public class CustomNamespace extends ManagedNamespace {
    private static final Logger logger = LoggerFactory.getLogger(CustomNamespace.class);
    public static final String URI = "urn:my:custom:namespace";

    static UaVariableNode robot1CurrentState;
    static UaVariableNode robot1PathwayLocation;

    static UaVariableNode robot2CurrentState;
    static UaVariableNode robot2PathwayLocation;

    static UaVariableNode robot3CurrentState;
    static UaVariableNode robot3PathwayLocation;

    static UaVariableNode robot4CurrentState;
    static UaVariableNode robot4PathwayLocation;

    static UaVariableNode Feeder1CreationInterval;
    static UaVariableNode Feeder2CreationInterval;

    private final SubscriptionModel subscriptionModel;

    public CustomNamespace(final OpcUaServer server, final String uri) throws Exception {
        super(server, uri);
        this.subscriptionModel = new SubscriptionModel(server, this);
        registerItems(getNodeContext());
    }

    private void registerItems(final UaNodeContext context) throws Exception {
        System.out.println("Registering items");

        // Create a folder
        final UaFolderNode folder = new UaFolderNode(
                context,
                newNodeId(1),
                newQualifiedName("FirstFolder"),
                LocalizedText.english("MainFolder"));
        context.getNodeManager().addNode(folder);

        // Add the folder to the objects folder
        final Optional<UaNode> objectsFolder = context.getServer()
                .getAddressSpaceManager()
                .getManagedNode(Identifiers.ObjectsFolder);
        objectsFolder.ifPresent(node -> ((FolderTypeNode) node).addComponent(folder));

        // Add variables for Robot 1
        robot1CurrentState = createUaVariableNode(newNodeId("1-unique-identifier"), AccessLevel.READ_WRITE, AccessLevel.READ_WRITE, Identifiers.String, "Robot 1 State", "Updating the Robot 1 State", "Get the Robot 1 State");
        robot1PathwayLocation = createUaVariableNode(newNodeId("2-unique-identifier"), AccessLevel.READ_WRITE, AccessLevel.READ_WRITE, Identifiers.String, "Robot 1 Pathway Location", "Updating the Robot 1 Pathway Location", "Get the Robot 1 Pathway Location");

        robot2CurrentState = createUaVariableNode(newNodeId("3-unique-identifier"), AccessLevel.READ_WRITE, AccessLevel.READ_WRITE, Identifiers.String, "Robot 2 State", "Updating the Robot 2 State", "Get the Robot 2 State");
        robot2PathwayLocation = createUaVariableNode(newNodeId("4-unique-identifier"), AccessLevel.READ_WRITE, AccessLevel.READ_WRITE, Identifiers.String, "Robot 2 Pathway Location", "Updating the Robot 2 Pathway Location", "Get the Robot 2 Pathway Location");

        robot3CurrentState = createUaVariableNode(newNodeId("5-unique-identifier"), AccessLevel.READ_WRITE, AccessLevel.READ_WRITE, Identifiers.String, "Robot 3 State", "Updating the Robot 3 State", "Get the Robot 3 State");
        robot3PathwayLocation = createUaVariableNode(newNodeId("6-unique-identifier"), AccessLevel.READ_WRITE, AccessLevel.READ_WRITE, Identifiers.String, "Robot 3 Pathway Location", "Updating the Robot 3 Pathway Location", "Get the Robot 3 Pathway Location");

        robot4CurrentState = createUaVariableNode(newNodeId("7-unique-identifier"), AccessLevel.READ_WRITE, AccessLevel.READ_WRITE, Identifiers.String, "Robot 4 State", "Updating the Robot 4 State", "Get the Robot 4 State");
        robot4PathwayLocation = createUaVariableNode(newNodeId("8-unique-identifier"), AccessLevel.READ_WRITE, AccessLevel.READ_WRITE, Identifiers.String, "Robot 4 Pathway Location", "Updating the Robot 4 Pathway Location", "Get the Robot 4 Pathway Location");

        Feeder1CreationInterval = createUaVariableNode(newNodeId("9-unique-identifier"), AccessLevel.READ_WRITE, AccessLevel.READ_WRITE, Identifiers.Int32, "Feeder1 CreationInterval", "Updating the Feeder1 Interval", "Get the Feeder1 Interval");
        Feeder2CreationInterval = createUaVariableNode(newNodeId("10-unique-identifier"), AccessLevel.READ_WRITE, AccessLevel.READ_WRITE, Identifiers.Int32, "Feeder2 CreationInterval", "Updating the Feeder2 Interval", "Get the Feeder1 Interval");

        // Set initial values for the variables
        Feeder1CreationInterval.setValue(new DataValue(new Variant(6)));
        Feeder2CreationInterval.setValue(new DataValue(new Variant(6)));



        // Helper method to add nodes to folder and context
        addNodeToFolderAndContext(folder, context, robot1CurrentState);
        addNodeToFolderAndContext(folder, context, robot1PathwayLocation);
        addNodeToFolderAndContext(folder, context, robot2CurrentState);
        addNodeToFolderAndContext(folder, context, robot2PathwayLocation);
        addNodeToFolderAndContext(folder, context, robot3CurrentState);
        addNodeToFolderAndContext(folder, context, robot3PathwayLocation);
        addNodeToFolderAndContext(folder, context, robot4CurrentState);
        addNodeToFolderAndContext(folder, context, robot4PathwayLocation);
        addNodeToFolderAndContext(folder, context, Feeder1CreationInterval);
        addNodeToFolderAndContext(folder, context, Feeder2CreationInterval);
    }

    private void addNodeToFolderAndContext(UaFolderNode folder, UaNodeContext context, UaVariableNode node) {
        folder.addOrganizes(node);
        context.getNodeManager().addNode(node);
    }



    @Override
    public void onDataItemsCreated(final List<DataItem> dataItems) {
        this.subscriptionModel.onDataItemsCreated(dataItems);
    }

    @Override
    public void onDataItemsModified(final List<DataItem> dataItems) {
        this.subscriptionModel.onDataItemsModified(dataItems);
    }

    @Override
    public void onDataItemsDeleted(final List<DataItem> dataItems) {
        this.subscriptionModel.onDataItemsDeleted(dataItems);
    }

    @Override
    public void onMonitoringModeChanged(final List<MonitoredItem> monitoredItems) {
        this.subscriptionModel.onMonitoringModeChanged(monitoredItems);
    }

    public UaVariableNode createUaVariableNode(NodeId nodeId, ImmutableSet<AccessLevel> accessLevel, ImmutableSet<AccessLevel> userAccessLevel, NodeId dataType, String qualifiedName, String displayName, String description) {
        UaVariableNode uaVariableNode = new UaVariableNode.UaVariableNodeBuilder(getNodeContext())
                .setNodeId(nodeId)
                .setAccessLevel(accessLevel)
                .setUserAccessLevel(userAccessLevel)
                .setDataType(dataType)
                .setBrowseName(newQualifiedName(qualifiedName))
                .setDisplayName(LocalizedText.english(displayName))
                .setDescription(LocalizedText.english(description))
                .build();
        return uaVariableNode;
    }
}
