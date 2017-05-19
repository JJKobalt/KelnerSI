package waiter.customer;

import waiter.WaiterPresenter;

/**
 * Created by JanJa on 16.05.2017.
 */
public class CustomerThink extends CustomerState {
    public CustomerThink(WaiterPresenter presenter, Customer customer) {
        super(presenter, customer);

        waitFromToAndLaterChangeState(5,15,new CustomerWaitingToOrder(presenter,customer));
    }

    @Override
    void service() {
        System.err.println("CUSTOMER: IM IN " + customer.getState() + " I SHOULDNT BE SERVED");
    }

    @Override
    public double toValue() {
        return 1;
    }
}
