package waiter.customer;

import waiter.WaiterPresenter;

/**
 * Created by JanJa on 16.05.2017.
 */
public class CustomerJustCame extends CustomerState {


    public CustomerJustCame(WaiterPresenter presenter, Customer customer) {
        super(presenter, customer);

    }

    @Override
    public void service() {
        presenter.orderWaiterToStandNextTo(customer.getTileX(), customer.getTileY());

        customer.setState(new CustomerThink(presenter,customer));
    }

    @Override
    public double toValue() {
        return 0;
    }


}
