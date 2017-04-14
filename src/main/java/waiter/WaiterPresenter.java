package waiter;

import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.Tile;
import tiled.core.TileLayer;
import waiter.waiter.Waiter;

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
