package waiter.map;

import waiter.waiter.WAITER_MOVE;

import java.util.List;

public interface FindPathStrategy
{
    List<WAITER_MOVE> findPath(int targetX, int targetY);
}
