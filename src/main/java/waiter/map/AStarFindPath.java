package waiter.map;

import waiter.WaiterPresenter;
import waiter.waiter.WAITER_ANGLE;
import waiter.waiter.WAITER_MOVE;
import waiter.waiter.Waiter;

import java.util.*;

class Node
{

    int x;
    int y;
    WAITER_ANGLE angle;
    Node parent;
    WAITER_MOVE action;
    int priority;

    Node(int x, int y, WAITER_ANGLE angle)
    {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    Node(WAITER_MOVE action, Node state, Node parent)
    {
        this.action = action;
        this.parent = parent;
        this.x = state.x;
        this.y = state.y;
        this.angle = state.angle;
    }

    Node(Waiter waiter)
    {
        this.x = waiter.getTileX();
        this.y = waiter.getTileY();
        this.angle = waiter.getAngle();
    }


    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof Node))
        {
            return false;
        }

        Node node = (Node) o;

        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode()
    {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString()
    {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", angle=" + angle +
                '}';
    }
}


public class AStarFindPath implements FindPathStrategy
{

    private final WaiterPresenter presenter;

    public AStarFindPath(WaiterPresenter presenter)
    {
        this.presenter = presenter;
    }

    @Override
    public List<WAITER_MOVE> findPath(int targetX, int targetY)
    {
        PriorityQueue<Node> fringe = new PriorityQueue<>((a, b) -> (a.priority - b.priority));
        Set<Node> explored = new HashSet<>();

        fringe.add(new Node(presenter.getWaiter()));

        while(!fringe.isEmpty())
        {
            Node elem = fringe.poll();

            if(isTargetReached(elem, targetX, targetY))
            {
                System.out.println("Travel cost: " + calculatePriorityForNodeBasedOnSpeed(elem));
                return produceActionsList(elem);
            }

            explored.add(elem);

            Map<WAITER_MOVE, Node> successors = getSuccessorsOf(elem);
            for(Map.Entry<WAITER_MOVE, Node> entry : successors.entrySet())
            {

                if(!fringe.contains(entry.getValue()) && !explored.contains(entry.getValue()))
                {
                    Node x = new Node(entry.getKey(), entry.getValue(), elem);
                    x.priority = calculatePriorityForNodeBasedOnSpeed(x);
                    fringe.add(x);
                }
                else if(fringe.contains(entry.getValue()))
                {
                    Node x = new Node(entry.getKey(), entry.getValue(), elem);

                    int r = getNodePriorityInFringe(fringe, entry.getValue());
                    int p = calculatePriorityForNodeBasedOnSpeed(x);

                    if(r > p)
                    {
                        x.priority = p;
                        fringe.remove(entry.getValue());
                        fringe.add(x);
                    }

                }
            }

        }

        return Collections.emptyList();
    }

    private int calculatePriorityForNodeBasedOnSpeed(Node node)
    {
        int priority = 0;

        while(node != null)
        {
            if(node.action == WAITER_MOVE.FORWARD)
            {
                priority += presenter.getWaiter().getSpeed();
            }

            if(node.action == WAITER_MOVE.LEFT || node.action == WAITER_MOVE.RIGHT)
            {
                priority += presenter.getWaiter().getSpeed() + presenter.getWaiter().getRotationSpeed();
            }

            if(node.action == WAITER_MOVE.BACKWARD)
            {
                priority += presenter.getWaiter().getSpeed() + 2 * presenter.getWaiter().getRotationSpeed();
            }

            node = node.parent;
        }

        return priority;
    }

    private int getNodePriorityInFringe(PriorityQueue<Node> fringe, Node value)
    {
        for(Node node : fringe)
        {
            if(node.equals(value))
            {
                return node.priority;
            }
        }

        return Integer.MAX_VALUE;
    }

    private List<WAITER_MOVE> produceActionsList(Node elem)
    {
        List<WAITER_MOVE> commands = new ArrayList<>();
        while(elem.parent != null)
        {
            commands.add(0, elem.action);
            elem = elem.parent;
        }

        return commands;
    }

    private boolean isTargetReached(Node elem, int targetX, int targetY)
    {
        return elem.x == targetX && elem.y == targetY;
    }

    private Map<WAITER_MOVE, Node> getSuccessorsOf(Node node)
    {
        Map<WAITER_MOVE, Node> successors = new EnumMap<>(WAITER_MOVE.class);

        addNorthSuccessors(node, successors);
        addEastSuccessors(node, successors);
        addSouthSuccessors(node, successors);
        addWestSuccessors(node, successors);

        return successors;
    }

    private void addWestSuccessors(Node node, Map<WAITER_MOVE, Node> successors)
    {
        if(node.angle == WAITER_ANGLE.WEST)
        {
            if(presenter.isWalkable(node.x - 1, node.y))
            {
                successors.put(WAITER_MOVE.FORWARD, new Node(node.x - 1, node.y, node.angle));
            }

            if(presenter.isWalkable(node.x + 1, node.y))
            {
                successors.put(WAITER_MOVE.BACKWARD, new Node(node.x + 1, node.y, node.angle.rotateLeft().rotateLeft()));
            }

            if(presenter.isWalkable(node.x, node.y - 1))
            {
                successors.put(WAITER_MOVE.RIGHT, new Node(node.x, node.y - 1, node.angle.rotateRight()));
            }

            if(presenter.isWalkable(node.x, node.y + 1))
            {
                successors.put(WAITER_MOVE.LEFT, new Node(node.x, node.y + 1, node.angle.rotateLeft()));
            }
        }
    }

    private void addSouthSuccessors(Node node, Map<WAITER_MOVE, Node> successors)
    {
        if(node.angle == WAITER_ANGLE.SOUTH)
        {
            if(presenter.isWalkable(node.x, node.y + 1))
            {
                successors.put(WAITER_MOVE.FORWARD, new Node(node.x, node.y + 1, node.angle));
            }

            if(presenter.isWalkable(node.x, node.y - 1))
            {
                successors.put(WAITER_MOVE.BACKWARD, new Node(node.x, node.y - 1, node.angle.rotateLeft().rotateLeft()));
            }

            if(presenter.isWalkable(node.x - 1, node.y))
            {
                successors.put(WAITER_MOVE.RIGHT, new Node(node.x - 1, node.y, node.angle.rotateRight()));
            }

            if(presenter.isWalkable(node.x + 1, node.y))
            {
                successors.put(WAITER_MOVE.LEFT, new Node(node.x + 1, node.y, node.angle.rotateLeft()));
            }
        }
    }

    private void addEastSuccessors(Node node, Map<WAITER_MOVE, Node> successors)
    {
        if(node.angle == WAITER_ANGLE.EAST)
        {
            if(presenter.isWalkable(node.x + 1, node.y))
            {
                successors.put(WAITER_MOVE.FORWARD, new Node(node.x + 1, node.y, node.angle));
            }

            if(presenter.isWalkable(node.x - 1, node.y))
            {
                successors.put(WAITER_MOVE.BACKWARD, new Node(node.x - 1, node.y, node.angle.rotateLeft().rotateLeft()));
            }

            if(presenter.isWalkable(node.x, node.y + 1))
            {
                successors.put(WAITER_MOVE.RIGHT, new Node(node.x, node.y + 1, node.angle.rotateRight()));
            }

            if(presenter.isWalkable(node.x, node.y - 1))
            {
                successors.put(WAITER_MOVE.LEFT, new Node(node.x, node.y - 1, node.angle.rotateLeft()));
            }
        }
    }

    private void addNorthSuccessors(Node node, Map<WAITER_MOVE, Node> successors)
    {
        if(node.angle == WAITER_ANGLE.NORTH)
        {
            if(presenter.isWalkable(node.x, node.y - 1))
            {
                successors.put(WAITER_MOVE.FORWARD, new Node(node.x, node.y - 1, node.angle));
            }

            if(presenter.isWalkable(node.x, node.y + 1))
            {
                successors.put(WAITER_MOVE.BACKWARD, new Node(node.x, node.y + 1, node.angle.rotateLeft().rotateLeft()));
            }

            if(presenter.isWalkable(node.x + 1, node.y))
            {
                successors.put(WAITER_MOVE.RIGHT, new Node(node.x + 1, node.y, node.angle.rotateRight()));
            }

            if(presenter.isWalkable(node.x - 1, node.y))
            {
                successors.put(WAITER_MOVE.LEFT, new Node(node.x - 1, node.y, node.angle.rotateLeft()));
            }
        }
    }

}
