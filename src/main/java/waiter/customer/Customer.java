package waiter.customer;

import waiter.TileCoordinate;
import waiter.WaiterPresenter;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by JanJa on 16.05.2017.
 */
public class Customer {
    WaiterPresenter presenter;
    LocalTime startedWaitingTime;
    CustomerState state;
    private int tileX;
    private int tileY;
    String name;

    public Customer(String name, int tileX, int tileY, WaiterPresenter presenter) {
        this.name = name;
        this.tileX = tileX;
        this.tileY = tileY;
        this.presenter = presenter;
        state = new CustomerJustCame(presenter, this);
    }
    public Customer( TileCoordinate coordinate, WaiterPresenter presenter) {
        this("someCustomer",coordinate.getTileX(),coordinate.getTileY(),presenter);
    }



    public Customer  addCustomerState( CustomerState state) {
        this.setState(state);
        return this;
    }



    public LocalTime getStartedWaitingTime() {
        return startedWaitingTime;
    }

    public void setStartedWaitingTime(LocalTime timeOfLastService) {
        this.startedWaitingTime = timeOfLastService;
    }

    public CustomerState getState() {
        return state;
    }

    public void setState(CustomerState state) {

        this.state = state;

    }

    public double getWaitingTime() {

        return ChronoUnit.SECONDS.between(startedWaitingTime, LocalTime.now());
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public TileCoordinate getTileCoordinate() {
        return new TileCoordinate(tileX, tileY);
    }

    public void service() {

        state.service();
    }

    public boolean isWaiting() {
        return (state instanceof CustomerWaitingToEat || state instanceof CustomerJustCame || state instanceof CustomerWaitingToOrder || state instanceof CustomerPay);

    }

    public String getName() {
        return name;
    }

    public double getDistanceToFullService() {
if(state instanceof CustomerWaitingToEat)
    return presenter.getDistanceCustomerFoodPoint(this) + presenter.getDistanceWaiterFoodPoint();
else return getDistanceWaiterCustomer();

    }

    private int getDistanceWaiterCustomer() {
        return presenter.getWaiter().getTileCoordinate().distance(this.getTileCoordinate());
    }
}
