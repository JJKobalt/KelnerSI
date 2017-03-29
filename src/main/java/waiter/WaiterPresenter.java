package waiter;

import tiled.core.MapLayer;
import tiled.core.Tile;
import tiled.core.TileLayer;

public class WaiterPresenter {

    private WaiterView view;
    Waiter waiter;



    WaiterPresenter(WaiterView view) {
        this.view = view;
        waiter = new Waiter(3, 3, this);


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

    public void moveWaiter(int tileX, int tileY) {

        view.setWaiterPosition(tileX, tileY);

    }

    public void orderWaiterToGoTo(int tileX, int tileY) {

        waiter.tryGoTo(tileX, tileY);

    }
}
