package waiter.waiter;

public class RotateLeftMoveCommand implements MoveCommand
{
    private final Waiter waiter;

    public RotateLeftMoveCommand(Waiter waiter)
    {
        this.waiter = waiter;
    }

    @Override
    public boolean go()
    {
        return waiter.rotateLeft();
    }

    @Override
    public boolean simulate()
    {
        return waiter.rotateLeft(true);
    }
}
