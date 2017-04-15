package waiter;

import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.Tile;
import tiled.core.TileLayer;
import waiter.map.FindPathStrategy;
import waiter.map.NaiveFindPathStrategy;
import waiter.waiter.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaiterPresenter
{

    private final Map map;
    private WaiterView view;
    private Waiter waiter;

    private FindPathStrategy findPathStrategy;

    WaiterPresenter(WaiterView view, Map map)
    {
        this.view = view;
        this.map = map;
        waiter = new Waiter(3, 3, this);
        findPathStrategy = new NaiveFindPathStrategy(this);
    }

    public Waiter getWaiter()
    {
        return waiter;
    }

    public Map getMap(){
        return map;
    }

    public boolean isWalkable(int x, int y)
    {
        return isInMapBounds(x, y) && !isCollidable(x, y);
    }

    private boolean isInMapBounds(int x, int y)
    {
        return x >= 0 && y >= 0 && x < map.getWidth() && y < map.getHeight();
    }

    private boolean isCollidable(int x, int y)
    {
        for(MapLayer layer : this.map)
        {
            Tile tile = ((TileLayer) layer).getTileAt(x, y);
            if(tile != null && "true".equalsIgnoreCase(tile.getProperties().getProperty("collidable")))
            {
                return true;
            }
        }

        return false;
    }


    List<MoveCommand> generateMoveCommands()
    {
        List<MoveCommand> commands = new ArrayList<>();
        commands.add(new LeftMoveCommand(getWaiter()));
        commands.add(new ForwardMoveCommand(getWaiter()));
        commands.add(new RightMoveCommand(getWaiter()));
        commands.add(new ForwardMoveCommand(getWaiter()));
        commands.add(new ForwardMoveCommand(getWaiter()));

        return commands;
    }

    void moveWaiter(List<MoveCommand> commands)
    {

        ConcurrentLinkedQueue<MoveCommand> queue = new ConcurrentLinkedQueue<>(commands);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Runnable task = () ->
        {
            while(!queue.isEmpty())
            {

                MoveCommand command = queue.peek();
                boolean moved = command.go();

                if(moved)
                {
                    queue.poll();
                    view.renderWaiter();
                }
            }

            view.redraw();
        };

        executorService.submit(task);

        executorService.shutdown();
    }

    void rotateWaiterLeft()
    {
        waiter.rotateLeft();
        view.redraw();
    }

    void rotateWaiterRight()
    {
        waiter.rotateRight();
        view.redraw();
    }

    void moveWaiterForward()
    {
        waiter.moveForward();
        view.redraw();
    }


    void moveWaiterToTile(int targetX, int targetY)
    {
        List<MoveCommand> commands = findPathStrategy.findPath(targetX, targetY);
        moveWaiter(commands);
    }

    public int tileCoordinatesToId(int x, int y)
    {
        return y * map.getWidth() + x;
    }


}
