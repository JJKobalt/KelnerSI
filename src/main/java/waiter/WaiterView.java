package waiter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tiled.core.Map;
import tiled.io.TMXMapReader;
import waiter.map.FXOrthogonalMapRenderer;
import waiter.map.MapRenderer;
import waiter.waiter.WAITER_ANGLE;
import waiter.waiter.Waiter;

import java.io.File;


public class WaiterView extends Application {

    private static final int TILE_SIZE = 32;
    private MapRenderer mapRenderer;

    private Canvas canvas;

    private WaiterPresenter presenter;

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {

            Pane root = new StackPane();
            Scene scene = new Scene(root, 800, 640);

            initializeKeyboardEventHandler(scene);

            Map map = loadMap();
            canvas = new Canvas(800, 640);
            mapRenderer = createRenderer(map);
            root.getChildren().add(canvas);

            presenter = new WaiterPresenter(this, map);

            redraw();

            primaryStage.setTitle("Inteligentny kelner | SI 2017SL");
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int doubleToTileId(double x) {
        return (int) x / TILE_SIZE;
    }

    private double tileIdToDouble(int tileId){
        return (double) tileId * TILE_SIZE;
    }


    private void initializeKeyboardEventHandler(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {

            if(keyEvent.getCode().equals(KeyCode.UP)){
                presenter.moveWaiterForward();
            }
            if(keyEvent.getCode().equals(KeyCode.RIGHT)){
                presenter.rotateWaiterRight();
            }
            if(keyEvent.getCode().equals(KeyCode.LEFT)){
                presenter.rotateWaiterLeft();
            }
        });
    }


    private Map loadMap() throws Exception {

        String path = getClass().getResource("/map/restaurant.tmx").getPath();
        File file = new File(path);
        TMXMapReader reader = new TMXMapReader();

        return reader.readMap(file.getAbsolutePath());
    }


    /*private Pane createWaiterPane() throws IOException {
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
    }*/

    private static MapRenderer createRenderer(Map map) {
        switch (map.getOrientation()) {
            case ORTHOGONAL:
                return new FXOrthogonalMapRenderer(map);

            case ISOMETRIC:
                throw new UnsupportedOperationException("Jak chce sie zmienić orientację mapki, to trzeba dodać renderera :)");

            default:
                throw new UnsupportedOperationException("No renderer found");
        }
    }

    void redraw(){
        renderMap();
        renderWaiter();
    }

    private void renderWaiter()
    {
        Image image;

        Waiter waiter = presenter.getWaiter();

        if(waiter.getAngle() == WAITER_ANGLE.NORTH){
            image = new Image(WaiterView.class.getClassLoader().getResource("waiter-north.png").toString());
        }
        else if(waiter.getAngle() == WAITER_ANGLE.EAST){
            image = new Image(WaiterView.class.getClassLoader().getResource("waiter-east.png").toString());
        }
        else if(waiter.getAngle() == WAITER_ANGLE.SOUTH){
            image = new Image(WaiterView.class.getClassLoader().getResource("waiter-south.png").toString());
        }
        else{
            image = new Image(WaiterView.class.getClassLoader().getResource("waiter-west.png").toString());
        }

        canvas.getGraphicsContext2D().drawImage(image,
                tileIdToDouble(waiter.getTileX()),
                tileIdToDouble(waiter.getTileY()),
                TILE_SIZE, TILE_SIZE);
    }

    private void renderMap()
    {
        mapRenderer.paint(canvas.getGraphicsContext2D());
    }



}