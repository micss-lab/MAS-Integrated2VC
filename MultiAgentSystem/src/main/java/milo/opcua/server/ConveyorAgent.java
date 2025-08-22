package milo.opcua.server;

import org.eclipse.milo.opcua.sdk.server.nodes.UaVariableNode;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;

import java.util.ArrayList;
import java.util.List;

/**
 * Template-configurable Conveyor Agent
 * Clean and organized conveyor management
 */
public class ConveyorAgent {
    
    // =====================================================================
    // CONFIGURATION - Template configurable
    // =====================================================================
    private UaVariableNode producedNode;
    private int conveyorId;
    
    public ConveyorAgent(UaVariableNode producedNode, int conveyorId) {
        this.producedNode = producedNode;
        this.conveyorId = conveyorId;
    }
    
    // =====================================================================
    // GETTERS - Clean interface
    // =====================================================================
    public UaVariableNode getProducedNode() {
        return producedNode;
    }
    
    public int getConveyorId() {
        return conveyorId;
    }
    
    // =====================================================================
    // CONVEYOR OPERATIONS - Well organized
    // =====================================================================
    public boolean getProduced() {
        try {
            DataValue value = producedNode.getValue();
            if (value != null && value.getValue() != null && value.getValue().getValue() != null) {
                return (Boolean) value.getValue().getValue();
            }
        } catch (Exception e) {
            System.err.println("Error getting produced value for conveyor " + conveyorId + ": " + e.getMessage());
        }
        return false;
    }
    
    public void setProduced(boolean produced) {
        try {
            producedNode.setValue(new DataValue(new Variant(produced)));
            System.out.println("Conveyor " + conveyorId + " produced status set to: " + produced);
        } catch (Exception e) {
            System.err.println("Error setting produced value for conveyor " + conveyorId + ": " + e.getMessage());
        }
    }
    
    // =====================================================================
    // UTILITY METHODS - Template friendly
    // =====================================================================
    public String getConveyorInfo() {
        return "Conveyor " + conveyorId + " (Produced: " + getProduced() + ")";
    }
    
    public boolean isProducing() {
        return getProduced();
    }
    
    public void startProduction() {
        setProduced(true);
    }
    
    public void stopProduction() {
        setProduced(false);
    }
}
