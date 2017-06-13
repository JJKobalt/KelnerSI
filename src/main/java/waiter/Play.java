package waiter;

import waiter.CustomerPrioritization.CustomerPrioritization;
import waiter.CustomerPrioritization.NeuralCustomerPrioritizer;
import waiter.customer.Customer;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by JanJa on 16.05.2017.
 */
public class Play implements Runnable {

    WaiterPresenter presenter;
    private CustomerPrioritization customerPrioritizer;

    public Play(WaiterPresenter presenter) {
        this.presenter = presenter;
        customerPrioritizer = new NeuralCustomerPrioritizer(presenter);
    }

    @Override
    public void run() {

        int i = 0;
        while (true) {



            Customer supportedClient = findCustomerToServe();

            supportedClient.service();


            presenter.redrawView();

        }
/*

*/


    }

    private Customer findCustomerToServe() {

        List<Customer> customers = presenter.getCustomers();

        customerPrioritizer.setCustomers(customers);

        Customer supportedClient = customerPrioritizer.getTheMostUrgentCustomer();
        List<Customer> actualCustomers;
        while (supportedClient == null) {

            sleepSeconds(1);

            presenter.redrawView();

            actualCustomers = presenter.getCustomers();

            customerPrioritizer.setCustomers(actualCustomers);

            supportedClient = customerPrioritizer.getTheMostUrgentCustomer();
        }
        return supportedClient;
    }

    private void sleepSeconds(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
