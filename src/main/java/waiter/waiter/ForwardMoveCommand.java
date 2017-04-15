package waiter.waiter;

public class ForwardMoveCommand implements MoveCommand
{
    private final Waiter waiter;

    public ForwardMoveCommand(Waiter waiter)
    {
        this.waiter = waiter;
    }

    @Override
    public boolean go()
    {
        return waiter.moveForward(false);
    }

    @Override
    public boolean simulate()
    {
        return waiter.moveForward(true);
    }
}
