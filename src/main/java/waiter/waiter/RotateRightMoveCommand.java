package waiter.waiter;

public class RotateRightMoveCommand implements MoveCommand
{
    private final Waiter waiter;

    public RotateRightMoveCommand(Waiter waiter)
    {
        this.waiter = waiter;
    }

    @Override
    public boolean go()
    {
        return waiter.rotateRight();
    }

}
