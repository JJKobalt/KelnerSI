package waiter.waiter;

import waiter.WaiterPresenter;

public class Waiter {

    private final WaiterPresenter presenter;

    private int tileX;
    private int tileY;
    private WAITER_ANGLE angle = WAITER_ANGLE.EAST;

    public Waiter(int tileX, int tileY, WaiterPresenter presenter) {
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

    public void rotateLeft(){
        angle = angle.rotateLeft();
    }

    public void rotateRight(){
        angle = angle.rotateRight();
    }

    public void moveForward(){
        if(angle == WAITER_ANGLE.NORTH && presenter.isWalkable(tileX, tileY - 1)){
            --tileY;
        }
        else if(angle == WAITER_ANGLE.SOUTH && presenter.isWalkable(tileX, tileY + 1)){
            ++tileY;
        }
        else if(angle == WAITER_ANGLE.EAST && presenter.isWalkable(tileX+1, tileY)){
            ++tileX;
        }
        else if(angle == WAITER_ANGLE.WEST && presenter.isWalkable(tileX-1, tileY)){
            --tileX;
        }
    }

}
