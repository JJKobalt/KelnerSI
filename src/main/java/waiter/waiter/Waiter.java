package waiter.waiter;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Waiter
{

    private int tileX;
    private int tileY;
    private WAITER_ANGLE angle = WAITER_ANGLE.EAST;

    private final static int speed = 250;

    private final static int rotationSpeed = 100;

    private LocalTime lastMoveTime = LocalTime.now();

    public Waiter(int tileX, int tileY)
    {
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public int getSpeed()
    {
        return speed;
    }

    public int getRotationSpeed()
    {
        return rotationSpeed;
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

        if(!force && ChronoUnit.MILLIS.between(lastMoveTime, currentTime) < rotationSpeed)
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

        if(!force && ChronoUnit.MILLIS.between(lastMoveTime, currentTime) < rotationSpeed)
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

        if(angle == WAITER_ANGLE.NORTH)
        {
            --tileY;
        }
        else if(angle == WAITER_ANGLE.SOUTH)
        {
            ++tileY;
        }
        else if(angle == WAITER_ANGLE.EAST )
        {
            ++tileX;
        }
        else if(angle == WAITER_ANGLE.WEST)
        {
            --tileX;
        }

        lastMoveTime = currentTime;
        return true;
    }

}
