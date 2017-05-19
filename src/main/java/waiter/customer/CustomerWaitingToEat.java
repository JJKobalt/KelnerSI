package waiter.customer;

import waiter.WaiterPresenter;

/**
 * Created by JanJa on 16.05.2017.
 */
public class CustomerWaitingToEat extends CustomerState {
    public CustomerWaitingToEat(WaiterPresenter presenter, Customer customer) {
        super(presenter, customer);

    }

    @Override
    void service() {

        presenter.orderWaiterToStandNextTo(4, 3);
        presenter.orderWaiterToStandNextTo(customer.getTileX(), customer.getTileY());

        customer.setState(new CustomerEat(presenter,customer));
    }

    @Override
    public double toValue() {
        return 4;
    }
}
