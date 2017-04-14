package waiter.waiter;

public class LeftMoveCommand implements MoveCommand
{
    private final Waiter waiter;

    private int progress = 0;

    public LeftMoveCommand(Waiter waiter)
    {
        this.waiter = waiter;
    }

    @Override
    public boolean go()
    {

        if(progress == 0)
        {
            if(waiter.rotateLeft())
            {
                progress = 1;
            }
            else{
                return false;
            }
        }

        if(progress == 1)
        {
            if(waiter.moveForward())
            {
                progress = 2;
            }
            else{
                return false;
            }
        }


        return progress == 2;
    }
}
