package waiter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tiled.core.Map;
import tiled.io.TMXMapReader;
import waiter.map.FXOrthogonalMapRenderer;
import waiter.map.MapRenderer;

import java.io.File;

public class WaiterApp extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        try
        {
            Map map = loadMap();

            Canvas canvas = new Canvas(800, 640);

            MapRenderer renderer = createRenderer(map);

            renderer.paint(canvas.getGraphicsContext2D(), map);

            Pane root = new StackPane();
            Scene scene = new Scene(root, 800, 640);
            root.getChildren().add(canvas);

            primaryStage.setTitle("Inteligentny kelner | SI 2017SL");
            primaryStage.setScene(scene);
            primaryStage.show();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    private Map loadMap() throws Exception{
        String path = getClass().getResource("/map/restaurant.tmx").getPath();
        File file = new File(path);
        TMXMapReader reader = new TMXMapReader();
        return reader.readMap(file.getAbsolutePath());
    }

    private static MapRenderer createRenderer(Map map) {
        switch (map.getOrientation()) {
            case ORTHOGONAL:
                return new FXOrthogonalMapRenderer();

            case ISOMETRIC:
                throw new UnsupportedOperationException("Jak chce sie zmienić orientację mapki, to trzeba dodać renderera :)");

            default:
                throw new UnsupportedOperationException("No renderer found");
        }
    }
}
