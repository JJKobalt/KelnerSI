package waiter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tiled.core.Map;
import tiled.io.TMXMapReader;
import waiter.map.FXOrthogonalMapRenderer;
import waiter.map.MapRenderer;
import waiter.menu.Pizza;
import waiter.waiter.WAITER_ANGLE;
import waiter.waiter.Waiter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class WaiterView extends Application {

    private static final int TILE_SIZE = 32;
    private MapRenderer mapRenderer;

    private Pane root;
    private Canvas canvas;

    private WaiterPresenter presenter;
    private java.util.Map<WAITER_ANGLE, Image> waiterImages;

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {

            waiterImages = generateWaiterImages();

            root = new HBox();
            Scene scene = new Scene(root, 1000, 640);

            initializeKeyboardEventHandler(scene);


            Map map = loadMap();
            canvas = new Canvas(800, 640);
            canvas.setOnMouseClicked(e -> presenter.moveWaiterToTile(doubleToTileId(e.getX()), doubleToTileId(e.getY())));

            mapRenderer = createRenderer(map);
            root.getChildren().add(canvas);

            presenter = new WaiterPresenter(this, map);

            Pane rightPane = generateMenuPane();

            root.getChildren().add(rightPane);


            redraw();

            primaryStage.setTitle("Inteligentny kelner | SI 2017SL");
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Pane generateMenuPane()
    {
        Pane rightPane = new VBox();
        rightPane.setId("rightPane");
        rightPane.setMinWidth(200);
        rightPane.setMaxWidth(200);

        rightPane.getChildren().add(new Text(""));
        Text title = new Text("Pizze wegetariańskie:");
        title.setStyle("-fx-font-weight: bold;");
        rightPane.getChildren().add(title);

        for(String pizza : presenter.getVegetablePizzas()){
            rightPane.getChildren().add(new Text(pizza));
        }

        rightPane.getChildren().add(new Text(""));
        title = new Text("Pizze mięsne:");
        title.setStyle("-fx-font-weight: bold;");
        rightPane.getChildren().add(title);

        for(String pizza : presenter.getMeatPizzas()){
            rightPane.getChildren().add(new Text(pizza));
        }

        rightPane.getChildren().add(new Text(""));
        title = new Text("Pizze ostre:");
        title.setStyle("-fx-font-weight: bold;");
        rightPane.getChildren().add(title);

        for(String pizza : presenter.getHotPizzas()){
            rightPane.getChildren().add(new Text(pizza));
        }
        rightPane.getChildren().add(new Text(""));
        rightPane.getChildren().add(new Text(""));

        Button button = new Button();
        button.setText("Dodaj nową pizze");
        rightPane.getChildren().add(button);

        button.setOnMouseClicked(event -> {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/addPizza.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();

            TextField nameField = (TextField) scene.lookup("#name");

            Button addNewButton = (Button) scene.lookup("#submit");

            addNewButton.setOnMouseClicked(ev -> {
                Pizza pizza = new Pizza(nameField.getText());

                if(((CheckBox) scene.lookup("#mushrooms")).isSelected()){
                    pizza.withMushrooms();
                }

                if(((CheckBox) scene.lookup("#ham")).isSelected()){
                    pizza.withHam();
                }

                if(((CheckBox) scene.lookup("#salami")).isSelected()){
                    pizza.withSalami();
                }

                if(((CheckBox) scene.lookup("#pineapple")).isSelected()){
                    pizza.withPineapple();
                }

                if(((CheckBox) scene.lookup("#tuna")).isSelected()){
                    pizza.withTuna();
                }

                if(((CheckBox) scene.lookup("#corn")).isSelected()){
                    pizza.withCorn();
                }

                if(((CheckBox) scene.lookup("#onion")).isSelected()){
                    pizza.withOnion();
                }

                if(((CheckBox) scene.lookup("#kebab")).isSelected()){
                    pizza.withKebab();
                }

                if(((CheckBox) scene.lookup("#sausage")).isSelected()){
                    pizza.withSausage();
                }

                if(((CheckBox) scene.lookup("#pepper")).isSelected()){
                    pizza.withPepper();
                }

                if(((CheckBox) scene.lookup("#cayenne")).isSelected()){
                    pizza.withCayenne();
                }

                presenter.addPizza(pizza);

                root.getChildren().remove(1);
                root.getChildren().add(generateMenuPane());

                ((Node)(ev.getSource())).getScene().getWindow().hide();
            });

            stage.setTitle("Dodawanie nowej pizzy");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e);
        }
    });

        return rightPane;
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

    java.util.Map<WAITER_ANGLE, Image> generateWaiterImages(){
        java.util.Map<WAITER_ANGLE, Image> images = new HashMap<>();

        images.put(WAITER_ANGLE.NORTH, new Image(WaiterView.class.getClassLoader().getResource("waiter-north.png").toString()));
        images.put(WAITER_ANGLE.EAST, new Image(WaiterView.class.getClassLoader().getResource("waiter-east.png").toString()));
        images.put(WAITER_ANGLE.SOUTH, new Image(WaiterView.class.getClassLoader().getResource("waiter-south.png").toString()));
        images.put(WAITER_ANGLE.WEST, new Image(WaiterView.class.getClassLoader().getResource("waiter-west.png").toString()));

        return images;
    }

    void redraw(){
        renderMap();
        renderWaiter();
    }

    void renderWaiter()
    {
        Waiter waiter = presenter.getWaiter();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.drawImage(waiterImages.get(waiter.getAngle()),
                tileIdToDouble(waiter.getTileX()),
                tileIdToDouble(waiter.getTileY()),
                TILE_SIZE, TILE_SIZE);
    }

    private void renderMap()
    {
        mapRenderer.paint(canvas.getGraphicsContext2D());
    }



}