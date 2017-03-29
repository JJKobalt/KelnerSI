package waiter;

/**
 * Created by JanJa on 29.03.2017.
 */
public class Waiter {

    int tileX;
    int tileY;

    WaiterPresenter presenter;


    public Waiter(int tileX, int tileY, WaiterPresenter presenter) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.presenter = presenter;
        moveRectangle();
    }

    public void setPosition(int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
    }


    public void moveRectangle() {
        presenter.moveWaiter(tileX, tileY);

    }


    public void tryGoTo(int tileX, int tileY) {

        if (!presenter.isCollidable(tileX, tileY)) {

                presenter.moveWaiter(tileX, tileY);


        }
    }

    public void MockWalking(int goalX, int goalY) throws InterruptedException {


        while (tileX != goalX) {
            if (tileX < goalX) {
                tileX++;
            }else  tileX--;

            presenter.moveWaiter(tileX, tileY);
        }

        while (tileY != goalY) {
            if (tileY < goalY) {
                tileY++;

            }else tileY--;

            presenter.moveWaiter(tileX, tileY);
        }

    }


}
