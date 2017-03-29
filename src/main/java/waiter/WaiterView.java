package waiter;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import tiled.core.Map;
import tiled.io.TMXMapReader;
import waiter.map.FXOrthogonalMapRenderer;
import waiter.map.MapRenderer;

import java.io.File;
import java.io.IOException;


public class WaiterView extends Application {

    private Rectangle waiter;

    private WaiterPresenter presenter;
    private Map map;
     TranslateTransition transition;

    private static final int      KEYBOARD_MOVEMENT_DELTA = 5;
    private static final Duration TRANSLATE_DURATION      = Duration.seconds(0.25);


    @Override
    public void start(Stage primaryStage) throws Exception {

        try {

            Pane waiterPane = createWaiterPane();
            transition = createTranslateTransition(waiter);
            presenter = new WaiterPresenter(this);

            Pane root = new StackPane();
            Scene scene = new Scene(root, 800, 640);

            initializeKeyboardEventHandler(scene);
            initializeMouseClickEventHandler(scene);

            Canvas restaurantCanvas = createRestaurantCanvas();
            root.getChildren().add(restaurantCanvas);


            root.getChildren().add(waiterPane);


            primaryStage.setTitle("Inteligentny kelner | SI 2017SL");
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private TranslateTransition createTranslateTransition(final Rectangle rectangle) {
        final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, rectangle);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent t) {
                rectangle.setX(rectangle.getTranslateX() + rectangle.getX());
                rectangle.setY(rectangle.getTranslateY() + rectangle.getY());
                rectangle.setTranslateX(0);
                rectangle.setTranslateY(0);
            }
        });
        return transition;
    }




    private void initializeMouseClickEventHandler(Scene scene) {

        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int tileX = doubleToTileId(mouseEvent.getX());
                int tileY = doubleToTileId(mouseEvent.getY());
                presenter.orderWaiterToGoTo(tileX, tileY);
            }
        });
    }

    private int doubleToTileId(double x) {

        return (int) x / 32;
    }

    private void initializeKeyboardEventHandler(Scene scene) {
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

    private Canvas createRestaurantCanvas() throws Exception {

        map = loadMap();
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


    private Pane createWaiterPane() throws IOException {
        Pane waiterPane = new Pane();


        waiter = new Rectangle();
        waiter.setHeight(32);
        waiter.setWidth(32);
        waiter.setX(64);
        waiter.setY(64);


        Image image = new Image(WaiterView.class.getClassLoader().getResource("waiter.png").toString());
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


    void moveWaiterLeft() {
        waiter.setX(waiter.getX()-32);
    }

    void moveWaiterUp() {
        waiter.setY(waiter.getY()-32);
    }

    void moveWaiterDown() {
        waiter.setY(waiter.getY()+32);
    }

    void moveWaiterRight() {

        waiter.setX(waiter.getX()+32);
    }

    Map getMap() {
        return map;
    }


    public void setWaiterPosition(int tileX, int tileY) {


        transition.setToX(tileIndexToCoordinate(tileX) - waiter.getX());
        transition.setToY(tileIndexToCoordinate(tileY) - waiter.getY());
        transition.playFromStart();

    }

    public int tileIndexToCoordinate(int tileId) {
        return tileId * 32;
    }



}