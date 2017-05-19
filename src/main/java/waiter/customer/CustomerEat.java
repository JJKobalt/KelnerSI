package waiter.customer;

import waiter.WaiterPresenter;

/**
 * Created by JanJa on 16.05.2017.
 */
public class CustomerEat extends CustomerState {


    WaiterPresenter presenter;
    Customer customer;

    public CustomerEat(WaiterPresenter presenter, Customer customer) {
        super(presenter, customer);

        waitFromToAndLaterChangeState(5,15,new CustomerPay(presenter,customer));
    }


    @Override
    public void service() {
        System.err.println("CUSTOMER: IM IN " + customer.getState() + " I SHOULDNT BE SERVED");
    }

    @Override
    public double toValue() {
        return 5;
    }


}
