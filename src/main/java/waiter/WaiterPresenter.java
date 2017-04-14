package waiter;

import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.Tile;
import tiled.core.TileLayer;
import waiter.waiter.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaiterPresenter {

    private WaiterView view;
    private Waiter waiter;
    private final Map map;

    WaiterPresenter(WaiterView view, Map map) {
        this.view = view;
        this.map = map;
        waiter = new Waiter(3, 3, this);
    }

    Waiter getWaiter()
    {
        return waiter;
    }

    public boolean isWalkable(int x, int y){
        return isInMapBounds(x, y) && !isCollidable(x, y);
    }

    private boolean isInMapBounds(int x, int y)
    {
        return x >= 0 && y >= 0 && x < map.getWidth() && y < map.getHeight();
    }

    private boolean isCollidable(int x, int y){
        for(MapLayer layer : this.map){
            Tile tile = ((TileLayer) layer).getTileAt(x, y);
            if(tile != null && "true".equalsIgnoreCase(tile.getProperties().getProperty("collidable"))){
                return true;
            }
        }

        return false;
    }


    List<MoveCommand> generateMoveCommands(){
        List<MoveCommand> commands = new ArrayList<>();
        commands.add(new RotateLeftMoveCommand(getWaiter()));
        commands.add(new ForwardMoveCommand(getWaiter()));
        commands.add(new ForwardMoveCommand(getWaiter()));
        commands.add(new RotateRightMoveCommand(getWaiter()));
        commands.add(new ForwardMoveCommand(getWaiter()));
        commands.add(new ForwardMoveCommand(getWaiter()));
        commands.add(new ForwardMoveCommand(getWaiter()));
        return commands;
    }

    void moveWaiter(List<MoveCommand> commands){

        ConcurrentLinkedQueue<MoveCommand> queue = new ConcurrentLinkedQueue<>(commands);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Runnable task = () ->
        {
            view.redraw();

            while(!queue.isEmpty()){

                MoveCommand command = queue.peek();
                boolean moved = command.go();

                if(moved){
                    queue.poll();
                    view.renderWaiter();
                }
            }
        };

        executorService.submit(task);

        executorService.shutdown();
    }

    void rotateWaiterLeft(){
        waiter.rotateLeft();
        view.redraw();
    }

    void rotateWaiterRight(){
        waiter.rotateRight();
        view.redraw();
    }

    void moveWaiterForward(){
        waiter.moveForward();
        view.redraw();
    }
}
