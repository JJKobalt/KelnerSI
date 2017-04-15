package waiter.map;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import tiled.core.Map;
import waiter.WaiterPresenter;
import waiter.waiter.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NaiveFindPathStrategy implements FindPathStrategy
{
    private final WaiterPresenter presenter;
    private final Map map;
    private final Waiter waiter;

    public NaiveFindPathStrategy(WaiterPresenter presenter)
    {
        this.presenter = presenter;
        map = presenter.getMap();
        waiter = presenter.getWaiter();
    }

    @Override
    public List<MoveCommand> findPath(int targetX, int targetY)
    {
        int sourceId = presenter.tileCoordinatesToId(waiter.getTileX(), waiter.getTileY());
        int targetId = presenter.tileCoordinatesToId(targetX, targetY);

        DirectedGraph<Integer, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        for(int y = 0; y < map.getHeight(); y++)
        {
            for(int x = 0; x < map.getWidth(); x++)
            {
                if(presenter.isWalkable(x, y))
                {
                    graph.addVertex(presenter.tileCoordinatesToId(x, y));
                }
            }
        }


        if(!graph.containsVertex(targetId) || !graph.containsVertex(sourceId)){
            return Collections.emptyList();
        }

        for(int y = 0; y < map.getHeight(); y++)
        {
            for(int x = 0; x < map.getWidth(); x++)
            {
                addEdge(x, y, x, y - 1, graph);
                addEdge(x, y, x, y + 1, graph);
                addEdge(x, y, x - 1, y, graph);
                addEdge(x, y, x + 1, y, graph);

            }
        }

        DijkstraShortestPath<Integer, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        List<Integer> vertices = dijkstraAlg.getPath(sourceId, targetId).getVertexList();

        List<MoveCommand> commands = new LinkedList<>();

        int startX = waiter.getTileX();
        int startY = waiter.getTileY();
        WAITER_ANGLE angle = waiter.getAngle();

        for(int i = 1; i < vertices.size(); i++)
        {
            if(vertices.get(i) == getNextTileId()){
                commands.add(new ForwardMoveCommand(waiter));
                waiter.moveForward(true);
                continue;
            }

            if(vertices.get(i) == getLeftTileId()){
                commands.add(new RotateLeftMoveCommand(waiter));
                commands.add(new ForwardMoveCommand(waiter));
                waiter.rotateLeft(true);
                waiter.moveForward(true);
                continue;
            }

            if(vertices.get(i) == getRightTileId()){
                commands.add(new RotateRightMoveCommand(waiter));
                commands.add(new ForwardMoveCommand(waiter));
                waiter.rotateRight(true);
                waiter.moveForward(true);
                continue;
            }

            if(vertices.get(i) == getPreviousTileId()){
                commands.add(new RotateRightMoveCommand(waiter));
                commands.add(new RotateRightMoveCommand(waiter));
                commands.add(new ForwardMoveCommand(waiter));
                waiter.rotateRight(true);
                waiter.rotateRight(true);
                waiter.moveForward(true);
                continue;
            }

        }

        System.out.println(commands);

        waiter.setPosition(startX, startY);
        waiter.setAngle(angle);

        return commands;
    }

    private int getPreviousTileId(){
        switch(waiter.getAngle()){
            case EAST:
                return presenter.tileCoordinatesToId(waiter.getTileX()-1, waiter.getTileY());
            case WEST:
                return presenter.tileCoordinatesToId(waiter.getTileX()+1, waiter.getTileY());
            case NORTH:
                return presenter.tileCoordinatesToId(waiter.getTileX(), waiter.getTileY()+1);
            default:
                return presenter.tileCoordinatesToId(waiter.getTileX(), waiter.getTileY()-1);
        }
    }

    private int getNextTileId(){
        switch(waiter.getAngle()){
            case EAST:
                return presenter.tileCoordinatesToId(waiter.getTileX()+1, waiter.getTileY());
            case WEST:
                return presenter.tileCoordinatesToId(waiter.getTileX()-1, waiter.getTileY());
            case NORTH:
                return presenter.tileCoordinatesToId(waiter.getTileX(), waiter.getTileY()-1);
            default:
                return presenter.tileCoordinatesToId(waiter.getTileX(), waiter.getTileY()+1);
        }
    }

    private int getLeftTileId(){
        switch(waiter.getAngle()){
            case EAST:
                return presenter.tileCoordinatesToId(waiter.getTileX(), waiter.getTileY()-1);
            case WEST:
                return presenter.tileCoordinatesToId(waiter.getTileX(), waiter.getTileY()+1);
            case NORTH:
                return presenter.tileCoordinatesToId(waiter.getTileX()-1, waiter.getTileY());
            default:
                return presenter.tileCoordinatesToId(waiter.getTileX()+1, waiter.getTileY());
        }
    }

    private int getRightTileId(){
        switch(waiter.getAngle()){
            case EAST:
                return presenter.tileCoordinatesToId(waiter.getTileX(), waiter.getTileY()+1);
            case WEST:
                return presenter.tileCoordinatesToId(waiter.getTileX(), waiter.getTileY()-1);
            case NORTH:
                return presenter.tileCoordinatesToId(waiter.getTileX()+1, waiter.getTileY());
            default:
                return presenter.tileCoordinatesToId(waiter.getTileX()-1, waiter.getTileY());
        }
    }

    private void addEdge(int x1, int y1, int x2, int y2, DirectedGraph<Integer, DefaultEdge> graph)
    {
        if(x1 == x2 && y1 == y2)
        {
            return;
        }

        if(!presenter.isWalkable(x1, y1) || !presenter.isWalkable(x2, y2)){
            return;
        }

        try
        {
            graph.addEdge(presenter.tileCoordinatesToId(x1, y1), presenter.tileCoordinatesToId(x2, y2));
        }
        catch(IllegalArgumentException e)
        {
            // ignore
        }
    }




}
