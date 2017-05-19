package waiter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JanJa on 16.05.2017.
 */
public class TileCoordinate {
    int tileX;
    int tileY;

    public TileCoordinate(int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
    }


    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    TileCoordinate onNorth() {
        return new TileCoordinate(tileX, tileY - 1);
    }

    TileCoordinate onSouth() {
        return new TileCoordinate(tileX, tileY + 1);
    }

    TileCoordinate onWest() {
        return new TileCoordinate(tileX - 1, tileY);
    }

    TileCoordinate onEast() {
        return new TileCoordinate(tileX + 1, tileY);
    }

    public int distance(TileCoordinate tileCoordinate) {
        if (tileCoordinate.equals(this)) return 0;

        return (int) Math.ceil(Math.sqrt(Math.pow(tileX - tileCoordinate.tileX, 2) + Math.pow(tileY - tileCoordinate.tileY, 2)));
    }

    public List<TileCoordinate> getSurronding() {
        List<TileCoordinate> surronding = new ArrayList<>();
        surronding.add(this.onNorth());
        surronding.add(this.onSouth());
        surronding.add(this.onWest());
        surronding.add(this.onEast());
        return surronding;
    }
}
