package waiter.waiter;

public class RightMoveCommand implements MoveCommand
{
    private final Waiter waiter;

    private int progress = 0;

    public RightMoveCommand(Waiter waiter)
    {
        this.waiter = waiter;
    }

    @Override
    public boolean go()
    {

        if(progress == 0)
        {
            if(waiter.rotateRight())
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
