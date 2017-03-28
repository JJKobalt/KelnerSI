package waiter.map;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.Tile;
import tiled.core.TileLayer;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class FXOrthogonalMapRenderer implements MapRenderer
{
    private GraphicsContext context;
    private Map map;

    private java.util.Map<Integer, Image> tilesCache = new HashMap<>();

    @Override
    public void paint(GraphicsContext context, Map map)
    {
        this.context = context;
        this.map = map;
        renderAllLayers();
    }

    private void renderAllLayers(){
        for (MapLayer layer : map) {
            if (layer instanceof TileLayer) {
                renderLayer((TileLayer) layer);
            }
        }
    }

    private void renderLayer(TileLayer layer){

        int tileWidth = this.map.getTileWidth();
        int tileHeight = this.map.getTileHeight();

        int mapWidth = map.getWidth() * tileWidth;
        int mapHeight = map.getHeight() * tileHeight;

        for(int x = 0; x <= mapWidth; ++x) {
            for(int y = 0; y <= mapHeight; ++y) {
                Tile tile = layer.getTileAt(x , y);

                if(tile != null) {
                    Image image = getTileImage(tile);
                    context.drawImage(image, x*tileWidth, y * tileHeight);
                }
            }
        }

    }

    private Image getTileImage(Tile tile){

        int tid = tile.getId();

        if(tilesCache.containsKey(tid)){
            return tilesCache.get(tid);
        }

        WritableImage img = new WritableImage(tile.getWidth(), tile.getHeight());
        BufferedImage buffered = (BufferedImage) tile.getImage();
        SwingFXUtils.toFXImage(buffered, img);
        tilesCache.put(tid, img);
        return img;
    }
}
