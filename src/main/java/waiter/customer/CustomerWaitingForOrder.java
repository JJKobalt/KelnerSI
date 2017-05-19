package waiter.customer;

import waiter.WaiterPresenter;

/**
 * Created by JanJa on 17.05.2017.
 */
public class CustomerWaitingForOrder extends CustomerState {
    public CustomerWaitingForOrder(WaiterPresenter presenter, Customer customer) {
        super(presenter, customer);

        waitFromToAndLaterChangeState(10,15,new CustomerWaitingToEat(presenter,customer));
    }

    @Override
    void service() {
        System.err.println("CUSTOMER: IM IN " + customer.getState() + " I SHOULDNT BE SERVED");
    }

    @Override
    public double toValue() {
        return 3;
    }
}
