package waiter.map;

import javafx.scene.canvas.GraphicsContext;
import tiled.core.Map;

public interface MapRenderer
{
    void paint(GraphicsContext context, Map map);
}
