package milo.opcua.server;

import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;

public class RobotAgent extends Agent {

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");
        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        parallelBehaviour.addSubBehaviour(receiverBehaviour);
        addBehaviour(parallelBehaviour);
    }

    TickerBehaviour receiverBehaviour = new TickerBehaviour(this, 500) { // Reduced tick interval to 500ms
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onTick() {
            String robot1State = CustomNamespace.robot1CurrentState.getValue().getValue().toString();
            String robot1PathwayLocation = CustomNamespace.robot1PathwayLocation.getValue().getValue().toString();

            String robot2State = CustomNamespace.robot2CurrentState.getValue().getValue().toString();
            String robot2PathwayLocation = CustomNamespace.robot2PathwayLocation.getValue().getValue().toString();

            String robot3State = CustomNamespace.robot3CurrentState.getValue().getValue().toString();
            String robot3PathwayLocation = CustomNamespace.robot3PathwayLocation.getValue().getValue().toString();

            String robot4State = CustomNamespace.robot4CurrentState.getValue().getValue().toString();
            String robot4PathwayLocation = CustomNamespace.robot4PathwayLocation.getValue().getValue().toString();

            System.out.println("Robot 1 CurrentState: " + robot1State);
            System.out.println("Robot 1 PathwayLocation: " + robot1PathwayLocation);

            System.out.println("Robot 2 CurrentState: " + robot2State);
            System.out.println("Robot 2 PathwayLocation: " + robot2PathwayLocation);

            System.out.println("Robot 3 CurrentState: " + robot3State);
            System.out.println("Robot 3 PathwayLocation: " + robot3PathwayLocation);

            System.out.println("Robot 4 CurrentState: " + robot4State);
            System.out.println("Robot 4 PathwayLocation: " + robot4PathwayLocation);


            }

    };
}
