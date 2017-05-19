package waiter;

import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.Tile;
import tiled.core.TileLayer;
import waiter.map.AStarFindPath;
import waiter.map.FindPathStrategy;
import waiter.menu.Menu;
import waiter.menu.Pizza;
import waiter.waiter.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class WaiterPresenter
{

    private final Map map;
    private WaiterView view;
    private Waiter waiter;
    private Menu menu;

    private FindPathStrategy findPathStrategy;

    WaiterPresenter(WaiterView view, Map map)
    {
        this.view = view;
        this.map = map;
        waiter = new Waiter(3, 1);
        findPathStrategy = new AStarFindPath(this);
        menu = new Menu();
    }

    public Waiter getWaiter()
    {
        return waiter;
    }

    public Map getMap()
    {
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


    private List<MoveCommand> generateMoveCommands(List<WAITER_MOVE> instructions)
    {
        List<MoveCommand> commands = new ArrayList<>();

        for(WAITER_MOVE instruction : instructions)
        {
            if(instruction == WAITER_MOVE.LEFT)
            {
                commands.add(new RotateLeftMoveCommand(getWaiter()));
            }
            else if(instruction == WAITER_MOVE.RIGHT)
            {
                commands.add(new RotateRightMoveCommand(getWaiter()));
            }
            else if(instruction == WAITER_MOVE.BACKWARD){
                commands.add(new RotateRightMoveCommand(getWaiter()));
                commands.add(new RotateRightMoveCommand(getWaiter()));
            }
            commands.add(new ForwardMoveCommand(getWaiter()));
        }

        return commands;
    }


    private void moveWaiter(List<WAITER_MOVE> instructions)
    {

        List<MoveCommand> commands = generateMoveCommands(instructions);

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
        List<WAITER_MOVE> commands = findPathStrategy.findPath(targetX, targetY);
        moveWaiter(commands);
    }

    List<String> getVegetablePizzas()
    {
        return menu.getPizzas().filter(pizza -> menu.isVegetable(pizza))
                .map(Pizza::getName).collect(Collectors.toList());
    }

    List<String> getMeatPizzas()
    {
        return menu.getPizzas().filter(pizza -> !menu.isVegetable(pizza))
                .map(Pizza::getName).collect(Collectors.toList());
    }

    List<String> getHotPizzas()
    {
        return menu.getPizzas().filter(pizza -> menu.isHot(pizza))
                .map(Pizza::getName).collect(Collectors.toList());
    }

    public int tileCoordinatesToId(int x, int y)
    {
        return y * map.getWidth() + x;
    }


    public void addPizza(Pizza pizza)
    {
        menu.addPizza(pizza);
    }
}
