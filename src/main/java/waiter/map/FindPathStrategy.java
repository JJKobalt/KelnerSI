package waiter.map;

import waiter.waiter.MoveCommand;

import java.util.List;

public interface FindPathStrategy
{
    List<MoveCommand> findPath(int targetX, int targetY);
}
