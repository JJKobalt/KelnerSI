package waiter;

/**
 * Created by JanJa on 28.03.2017.
 */
public class WaiterPresenter {

    WaiterView view;

    public WaiterPresenter(WaiterView view) {
        this.view = view;
    }


    public void moveWaiterLeft() {
        view.moveWaiterLeft();

    }


    public void moveWaiterUp() {
        view.moveWaiterUp();
    }

    public void moveWaiterDown() {
        view.moveWaiterDown();
    }

    public void moveWaiterRight() {
        view.moveWaiterRight();
    }
}
