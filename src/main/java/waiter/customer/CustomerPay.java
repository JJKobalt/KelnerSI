package waiter.customer;

import waiter.WaiterPresenter;

/**
 * Created by JanJa on 16.05.2017.
 */
public class CustomerPay extends CustomerState {


    public CustomerPay(WaiterPresenter presenter, Customer customer) {
        super(presenter, customer);

    }

    @Override
    void service() {
        presenter.orderWaiterToStandNextTo(customer.getTileX(), customer.getTileY());
        presenter.deleteCustomer(customer);
    }

    @Override
    public double toValue() {
        return 6;
    }
}
