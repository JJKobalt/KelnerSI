package waiter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import tiled.core.Map;
import tiled.io.TMXMapReader;
import waiter.map.FXOrthogonalMapRenderer;
import waiter.map.MapRenderer;

import java.io.File;
import java.io.IOException;

/**
 * Created by JanJa on 28.03.2017.
 */
public class WaiterView extends Application {

    Rectangle waiter;

    WaiterPresenter presenter;

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            presenter = new WaiterPresenter(this);

            Pane root = new StackPane();
            Scene scene = new Scene(root, 800, 640);

            initializeKeybordEventHandler(scene);


            Canvas restaurantCanvas = getRestaurantCanvas();
            root.getChildren().add(restaurantCanvas);



            root.getChildren().add(createWaiterPane());


            primaryStage.setTitle("Inteligentny kelner | SI 2017SL");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initializeKeybordEventHandler(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {

            if(keyEvent.getCode().equals(KeyCode.UP)){
                presenter.moveWaiterUp();
            }
            if(keyEvent.getCode().equals(KeyCode.DOWN)){
                presenter.moveWaiterDown();
            }
            if(keyEvent.getCode().equals(KeyCode.RIGHT)){
                presenter.moveWaiterRight();
            }
            if(keyEvent.getCode().equals(KeyCode.LEFT)){
                presenter.moveWaiterLeft();
            }
        });
    }

    private Canvas getRestaurantCanvas() throws Exception {

        Map map = loadMap();
        Canvas canvas = new Canvas(800, 640);
        MapRenderer renderer = createRenderer(map);
        renderer.paint(canvas.getGraphicsContext2D(), map);

        return canvas;
    }


    private Map loadMap() throws Exception {

        String path = getClass().getResource("/map/restaurant.tmx").getPath();
        File file = new File(path);
        TMXMapReader reader = new TMXMapReader();

        return reader.readMap(file.getAbsolutePath());
    }


    public Pane createWaiterPane() throws IOException {
        Pane waiterPane = new Pane();


        waiter = new Rectangle();
        waiter.setHeight(32);
        waiter.setWidth(32);
        waiter.setX(64);
        waiter.setY(64);


        Image image = new Image(WaiterView.class.getClassLoader().getResource("waiter.jpg").toString());
        waiter.setFill(new ImagePattern(image));

        waiterPane.getChildren().add(waiter);
        return waiterPane;

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


    public void moveWaiterLeft() {
    waiter.setX(waiter.getX()-32);
    }

    public void moveWaiterUp() {
        waiter.setY(waiter.getY()-32);
    }

    public void moveWaiterDown() {
        waiter.setY(waiter.getY()+32);
    }

    public void moveWaiterRight() {
        waiter.setX(waiter.getX()+32);
    }
}

