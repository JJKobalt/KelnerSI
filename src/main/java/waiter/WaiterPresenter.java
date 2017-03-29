package waiter;

import tiled.core.MapLayer;
import tiled.core.Tile;
import tiled.core.TileLayer;

public class WaiterPresenter {

    private WaiterView view;

    WaiterPresenter(WaiterView view) {
        this.view = view;
    }

    boolean isCollidable(int x, int y){
        for(MapLayer layer : view.getMap()){
            Tile tile = ((TileLayer) layer).getTileAt(x, y);
            if(tile != null && "true".equalsIgnoreCase(tile.getProperties().getProperty("collidable"))){
                return true;
            }
        }

        return false;
    }

    void moveWaiterLeft() {
        view.moveWaiterLeft();
    }

    void moveWaiterUp() {
        view.moveWaiterUp();
    }

    void moveWaiterDown() {
        view.moveWaiterDown();
    }

    void moveWaiterRight() {
        view.moveWaiterRight();
    }
}
