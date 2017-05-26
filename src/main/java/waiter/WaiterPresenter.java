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
    private List<TileCoordinate> spots;
private TileCoordinate foodPoint;

    WaiterPresenter(WaiterView view, Map map) {
        spots = initializeAllSpots();
        this.view = view;
        this.map = map;
        waiter = new Waiter(3, 1);
        findPathStrategy = new AStarFindPath(this);
        menu = new Menu();
        foodPoint = new TileCoordinate(4,3);

        customers = initializeCustomers();
    }

    private List<TileCoordinate> initializeAllSpots() {
        List<TileCoordinate> spots = new LinkedList<>();
        spots.add(new TileCoordinate(10,2));
        spots.add(new TileCoordinate(11,2));
        spots.add(new TileCoordinate(10,5));
        spots.add(new TileCoordinate(11,5));

        spots.add(new TileCoordinate(18,2));
        spots.add(new TileCoordinate(19,2));
        spots.add(new TileCoordinate(18,5));
        spots.add(new TileCoordinate(19,5));

        spots.add(new TileCoordinate(2,8));
        spots.add(new TileCoordinate(3,8));
        spots.add(new TileCoordinate(2,11));
        spots.add(new TileCoordinate(3,11));

        spots.add(new TileCoordinate(10,8));
        spots.add(new TileCoordinate(11,8));
        spots.add(new TileCoordinate(10,11));
        spots.add(new TileCoordinate(11,11));

        spots.add(new TileCoordinate(18,8));
        spots.add(new TileCoordinate(19,8));
        spots.add(new TileCoordinate(18,11));
        spots.add(new TileCoordinate(19,11));

        spots.add(new TileCoordinate(2,14));
        spots.add(new TileCoordinate(3,14));
        spots.add(new TileCoordinate(2,17));
        spots.add(new TileCoordinate(3,17));

        spots.add(new TileCoordinate(10,14));
        spots.add(new TileCoordinate(11,14));
        spots.add(new TileCoordinate(10,17));
        spots.add(new TileCoordinate(11,17));

        spots.add(new TileCoordinate(18,14));
        spots.add(new TileCoordinate(19,14));
        spots.add(new TileCoordinate(18,17));
        spots.add(new TileCoordinate(19,17));
        return spots;
    }

    private List<Customer> initializeCustomers() {
        List<Customer> customers = getCustomersListVer1();
        return customers;
    }

    private List<Customer> getCustomersListVer1() {
        List<Customer> customers = new LinkedList<>();
        customers.add(new Customer(spots.get(0), this ));
        customers.add(new Customer(spots.get(3), this ));
        customers.add(new Customer(spots.get(7), this ));
        customers.add(new Customer(spots.get(8), this ));
        customers.add(new Customer(spots.get(10), this ));
        customers.add(new Customer(spots.get(16), this ));
        customers.add(new Customer(spots.get(18), this ));
        customers.add(new Customer(spots.get(20), this ));
        customers.add(new Customer(spots.get(22), this ));
        customers.add(new Customer(spots.get(25), this ));
        customers.add(new Customer(spots.get(28), this ));
        return customers;
    }
    private List<Customer> getCustomersListAll() {
        List<Customer> customers = new LinkedList<>();
     for(int i=0;i<spots.size();i++)
     {
         customers.add(new Customer(spots.get(i), this ));
     }
        return customers;
    }






    public void letTheWaiterToStartTheService() {


        ExecutorService executorService = Executors.newFixedThreadPool(9999);

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


        return (customers.size()/32d);
    }

    public TileCoordinate getFoodPoint() {
        return foodPoint;
    }

    public double getDistanceWaiterFoodPoint()
    {
        return waiter.getTileCoordinate().distance(foodPoint);
    }
    public double getDistanceCustomerFoodPoint(Customer customer)
    {
        return customer.getTileCoordinate().distance(foodPoint);
    }

}


