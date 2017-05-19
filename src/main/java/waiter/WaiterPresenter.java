package waiter;

import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.Tile;
import tiled.core.TileLayer;

import waiter.customer.Customer;

import waiter.map.AStarFindPath;

import waiter.map.FindPathStrategy;
import waiter.menu.Menu;
import waiter.menu.Pizza;
import waiter.waiter.*;

import java.util.ArrayList;
import java.util.LinkedList;
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
    private List<Customer> customers;
    private FindPathStrategy findPathStrategy;


    WaiterPresenter(WaiterView view, Map map) {
        this.view = view;
        this.map = map;
        waiter = new Waiter(3, 1);
        findPathStrategy = new AStarFindPath(this);
        menu = new Menu();


        customers = initializeCustomers();
    }

    private List<Customer> initializeCustomers() {
        List<Customer> customers = new LinkedList<>();
        customers.add(new Customer("ADAM",11, 2, this));
        customers.add(new Customer("BłAZEJ",3, 14, this));
        customers.add(new Customer("CEZARY",18, 14, this));
        return customers;
    }


    public void letTheWaiterToStartTheService() {


        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(new Play(this));

        executorService.shutdown();
    }


    public Waiter getWaiter() {
        return waiter;
    }

    public Map getMap()
    {
        return map;
    }

    public boolean isWalkable(int x, int y) {
        return isInMapBounds(x, y) && !isCollidable(x, y);
    }

    private boolean isInMapBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < map.getWidth() && y < map.getHeight();
    }

    private boolean isCollidable(int x, int y) {
        for(MapLayer layer : this.map) {
            Tile tile = ((TileLayer) layer).getTileAt(x, y);
            if(tile != null && "true".equalsIgnoreCase(tile.getProperties().getProperty("collidable"))) {
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
            executeCommandQueue(queue);

            view.redraw();
        };

        executorService.submit(task);

        executorService.shutdown();
    }

    void rotateWaiterLeft() {
        waiter.rotateLeft();
        view.redraw();
    }

    void rotateWaiterRight() {
        waiter.rotateRight();
        view.redraw();
    }

    void moveWaiterForward() {
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

    public int tileCoordinatesToId(int x, int y) {
        return y * map.getWidth() + x;
    }


    public void addPizza(Pizza pizza) {
        menu.addPizza(pizza);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public TileCoordinate getTheClosestTileCoordinateToWaiter(TileCoordinate tileCoordinate) {

        List<TileCoordinate> surrondings = getNotCollidableSurrondings(tileCoordinate);


        int distance;
        int minDistance = 100;
        TileCoordinate targetedCoordinate = null;
        for (TileCoordinate coordinate : surrondings) {
            distance = coordinate.distance(waiter.getTileCoordinate());
            if (distance < minDistance) {
                minDistance = distance;
                targetedCoordinate = coordinate;
            }
        }
        return targetedCoordinate;
    }

    private List<TileCoordinate> getNotCollidableSurrondings(TileCoordinate tileCoordinate) {
        List<TileCoordinate> coordinates = new ArrayList<>();
        List<TileCoordinate> surroundingCoordinates = tileCoordinate.getSurronding();

        for (TileCoordinate coordinate : surroundingCoordinates) {

            if (!isCollidable(coordinate.tileX, coordinate.tileY)) {
                coordinates.add(coordinate);
            }
        }

        return coordinates;
    }

    public void moveWaiterToTileWithoutThread(int targetX, int targetY) {

        List<WAITER_MOVE> commands = findPathStrategy.findPath(targetX, targetY);
        moveWaiterWithoutThread(commands);

    }

    private void moveWaiterWithoutThread(List<WAITER_MOVE> instructions) {

        List<MoveCommand> commands = generateMoveCommands(instructions);
        ConcurrentLinkedQueue<MoveCommand> queue = new ConcurrentLinkedQueue<>(commands);
        executeCommandQueue(queue);

        view.redraw();
    }

    private void executeCommandQueue(ConcurrentLinkedQueue<MoveCommand> queue) {
        while (!queue.isEmpty()) {

            MoveCommand command = queue.peek();
            boolean moved = command.go();

            if (moved) {
                queue.poll();
                view.renderWaiter();
            }
        }
    }


    public void orderWaiterToStandNextTo(int targetX, int targetY) {


        TileCoordinate target = new TileCoordinate(targetX, targetY);
        TileCoordinate coordinate = getTheClosestTileCoordinateToWaiter(target);
        moveWaiterToTileWithoutThread(coordinate.getTileX(), coordinate.getTileY());
        rotateWaiterToFace(target);


    }


    public void rotateWaiterToFace(TileCoordinate tileCoordinate) {

        WAITER_ANGLE angle = waiter.getAngle();
        ConcurrentLinkedQueue<MoveCommand> queue = new ConcurrentLinkedQueue<>();
        if (waiter.getTileX() > tileCoordinate.getTileX()) {

            if (angle == WAITER_ANGLE.SOUTH) {

                queue.add(new RotateRightMoveCommand(waiter));
                queue.add(new RotateRightMoveCommand(waiter));

            }
            if (angle == WAITER_ANGLE.WEST) {
                queue.add(new RotateRightMoveCommand(waiter));
            }
            if (angle == WAITER_ANGLE.EAST) {
                queue.add(new RotateLeftMoveCommand(waiter));
            }
        } else if (waiter.getTileX() < tileCoordinate.getTileX()) {

            if (angle == WAITER_ANGLE.NORTH) {
                queue.add(new RotateRightMoveCommand(waiter));
                queue.add(new RotateRightMoveCommand(waiter));
            }
            if (angle == WAITER_ANGLE.WEST) {
                queue.add(new RotateLeftMoveCommand(waiter));
            }
            if (angle == WAITER_ANGLE.EAST) {
                queue.add(new RotateRightMoveCommand(waiter));
            }
        } else if (waiter.getTileY() < tileCoordinate.getTileY()) {

            if (angle == WAITER_ANGLE.SOUTH) {
                queue.add(new RotateLeftMoveCommand(waiter));
            }
            if (angle == WAITER_ANGLE.WEST) {
                queue.add(new RotateRightMoveCommand(waiter));
                queue.add(new RotateRightMoveCommand(waiter));
            }
            if (angle == WAITER_ANGLE.NORTH) {
                queue.add(new RotateRightMoveCommand(waiter));
            }
        } else if (waiter.getTileY() > tileCoordinate.getTileY()) {

            if (angle == WAITER_ANGLE.SOUTH) {
                queue.add(new RotateRightMoveCommand(waiter));
            }
            if (angle == WAITER_ANGLE.WEST) {
                queue.add(new RotateRightMoveCommand(waiter));
                queue.add(new RotateRightMoveCommand(waiter));
            }
            if (angle == WAITER_ANGLE.NORTH) {
                queue.add(new RotateLeftMoveCommand(waiter));
            }
        }


    }

    public void deleteCustomer(Customer leavingCustomer) {

        int l = customers.size();
        customers.remove(leavingCustomer);
        int n = customers.size();

    }


    public void redrawView() {

        view.redraw();
    }

    public double getOvercrowded() {


        return (customers.size()/32)*100;
    }
}


