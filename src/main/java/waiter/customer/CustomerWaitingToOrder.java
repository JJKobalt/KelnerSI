package waiter.customer;

import PlatesNN.PlateChecker;
import waiter.WaiterPresenter;
import waiter.plate.Plate;
import waiter.plate.PlateFactory;

/**
 * Created by JanJa on 16.05.2017.
 */
public class CustomerWaitingToOrder extends CustomerState {
    public CustomerWaitingToOrder(WaiterPresenter presenter, Customer customer) {
        super(presenter, customer);


    }

    @Override
    void service() {
        presenter.orderWaiterToStandNextTo(customer.getTileX(), customer.getTileY());

        customer.setState(new CustomerWaitingForOrder(presenter,customer));
    }

    @Override
    public double toValue() {
        return 2;
    }
}
