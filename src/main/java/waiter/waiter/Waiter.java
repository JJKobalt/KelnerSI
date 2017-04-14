package waiter.waiter;

import waiter.WaiterPresenter;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Waiter
{

    private final WaiterPresenter presenter;

    private int tileX;
    private int tileY;
    private WAITER_ANGLE angle = WAITER_ANGLE.EAST;

    private final int speed = 100;
    private LocalTime lastMoveTime = LocalTime.now();

    public Waiter(int tileX, int tileY, WaiterPresenter presenter)
    {
        this.tileX = tileX;
        this.tileY = tileY;
        this.presenter = presenter;
    }

    public WAITER_ANGLE getAngle()
    {
        return angle;
    }

    public int getTileX()
    {
        return tileX;
    }

    public int getTileY()
    {
        return tileY;
    }

    public boolean rotateLeft()
    {
        LocalTime currentTime = LocalTime.now();

        if(ChronoUnit.MILLIS.between(lastMoveTime, currentTime) < speed)
        {
            return false;
        }

        angle = angle.rotateLeft();
        lastMoveTime = currentTime;

        return true;
    }

    public boolean rotateRight()
    {
        LocalTime currentTime = LocalTime.now();

        if(ChronoUnit.MILLIS.between(lastMoveTime, currentTime) < speed)
        {
            return false;
        }

        angle = angle.rotateRight();
        lastMoveTime = currentTime;

        return true;
    }

    public boolean moveForward()
    {
        LocalTime currentTime = LocalTime.now();

        if(ChronoUnit.MILLIS.between(lastMoveTime, currentTime) < speed)
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

}
