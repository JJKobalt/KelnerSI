package waiter.waiter;

import waiter.TileCoordinate;
import waiter.WaiterPresenter;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Waiter
{

    private final WaiterPresenter presenter;

    private int tileX;
    private int tileY;
    private WAITER_ANGLE angle = WAITER_ANGLE.EAST;

    private final static int speed = 250;
    private LocalTime lastMoveTime = LocalTime.now();

    public Waiter(int tileX, int tileY, WaiterPresenter presenter)
    {
        this.tileX = tileX;
        this.tileY = tileY;
        this.presenter = presenter;
    }

    public void setPosition(int x, int y){
        tileX = x;
        tileY = y;
    }

    public WAITER_ANGLE getAngle()
    {
        return angle;
    }

    public void setAngle(WAITER_ANGLE angle)
    {
        this.angle = angle;
    }

    public int getTileX()
    {
        return tileX;
    }

    public int getTileY()
    {
        return tileY;
    }

    public boolean rotateLeft(){
        return rotateLeft(false);
    }

    public boolean rotateLeft(boolean force)
    {
        LocalTime currentTime = LocalTime.now();

        if(!force && ChronoUnit.MILLIS.between(lastMoveTime, currentTime) < speed)
        {
            return false;
        }

        angle = angle.rotateLeft();
        lastMoveTime = currentTime;

        return true;
    }

    public boolean rotateRight(){
        return rotateRight(false);
    }

    public boolean rotateRight(boolean force)
    {
        LocalTime currentTime = LocalTime.now();

        if(!force && ChronoUnit.MILLIS.between(lastMoveTime, currentTime) < speed)
        {
            return false;
        }

        angle = angle.rotateRight();
        lastMoveTime = currentTime;

        return true;
    }

    public boolean moveForward(){
        return moveForward(false);
    }

    public boolean moveForward(boolean force)
    {
        LocalTime currentTime = LocalTime.now();

        if(!force && ChronoUnit.MILLIS.between(lastMoveTime, currentTime) < speed)
        {
            return false;
        }

        if(angle == WAITER_ANGLE.NORTH && presenter.isWalkable(tileX, tileY - 1))
        {
            --tileY;
        }
        else if(angle == WAITER_ANGLE.SOUTH && presenter.isWalkable(tileX, tileY + 1))
        {
            ++tileY;
        }
        else if(angle == WAITER_ANGLE.EAST && presenter.isWalkable(tileX + 1, tileY))
        {
            ++tileX;
        }
        else if(angle == WAITER_ANGLE.WEST && presenter.isWalkable(tileX - 1, tileY))
        {
            --tileX;
        }

        lastMoveTime = currentTime;
        return true;
    }


    public TileCoordinate getTileCoordinate(){

        return new TileCoordinate(tileX,tileY);
    }

}
