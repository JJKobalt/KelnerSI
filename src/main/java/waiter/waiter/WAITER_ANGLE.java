package waiter.waiter;

import java.util.NoSuchElementException;

public enum WAITER_ANGLE{

    NORTH(0), EAST(1), SOUTH(2), WEST(3);

    private int value;

    WAITER_ANGLE(int value)
    {
        this.value = value;
    }

    public static WAITER_ANGLE valueOf(int key){
        for(WAITER_ANGLE angle : WAITER_ANGLE.values()){
            if(angle.value == key){
                return angle;
            }
        }

        throw new NoSuchElementException("No angle value with key " + key);
    }

    public WAITER_ANGLE rotateRight(){
        int length = WAITER_ANGLE.values().length;
        int newValue = (value + 1) % length;
        return WAITER_ANGLE.valueOf(newValue);
    }

    public WAITER_ANGLE rotateLeft(){
        int length = WAITER_ANGLE.values().length;
        int newValue = (value - 1) % length;
        if(newValue < 0) newValue = length + newValue;
        return WAITER_ANGLE.valueOf(newValue);
    }

    @Override
    public String toString()
    {
        String pos = "x";

        if(this == WAITER_ANGLE.NORTH){
            pos = "↑";
        }
        else if(this == WAITER_ANGLE.EAST){
            pos = "→";
        }
        else if(this == WAITER_ANGLE.SOUTH){
            pos = "↓";
        }
        else if(this == WAITER_ANGLE.WEST){
            pos = "←";
        }

        return pos;
    }
}
