package mainPack;

import logist.agent.Agent;
import logist.behavior.DeliberativeBehavior;
import logist.plan.Plan;
import logist.simulation.Vehicle;
import logist.task.TaskDistribution;
import logist.task.TaskSet;
import logist.topology.Topology;

import java.util.ArrayList;

/**
 * Created by lorenzotara on 21/10/17.
 */
public class DeliberativeAgent implements DeliberativeBehavior {

    enum Algorithm { BFS, ASTAR }

    /* Environment */
    private Topology topology;
    private TaskDistribution td;

    /* the properties of the agent */
    private Agent agent;
    int capacity;
    private DeliberativeStrategy strategy;

    /* the planning class */
    private Algorithm algorithm;

    @Override
    public void setup(Topology topology, TaskDistribution distribution, Agent agent) {

        this.topology = topology;
        this.td = td;
        this.agent = agent;

        // initialize the planner
        int capacity = agent.vehicles().get(0).capacity();
        String algorithmName = agent.readProperty("algorithm", String.class, "ASTAR");

        // Throws IllegalArgumentException if algorithm is unknown
        algorithm = Algorithm.valueOf(algorithmName.toUpperCase());

        // ...

        strategy = new DeliberativeStrategy();
    }

    @Override
    public Plan plan(Vehicle vehicle, TaskSet tasks) {
        Plan plan;

        // Compute the plan with the selected algorithm.
        switch (algorithm) {
            case ASTAR:
                plan = strategy.astar(vehicle, tasks);
                break;

            case BFS:

                double startTime = System.currentTimeMillis();

                plan = strategy.bfs(vehicle, tasks, new ArrayList<>(vehicle.getCurrentTasks()));

                double finishTime = System.currentTimeMillis();

                System.out.println("time to calculate BFS: " + ((finishTime - startTime)/1000) + " seconds");
                break;

            default:
                throw new AssertionError("Should not happen.");
        }
        return plan;
    }


    @Override
    public void planCancelled(TaskSet carriedTasks) {
        /*if (!carriedTasks.isEmpty()) {
            // This cannot happen for this simple agent, but typically
            // you will need to consider the carriedTasks when the next
            // plan is computed.


        }*/

        //strategy.bfs(agent.vehicles().get(0), )
    }
}