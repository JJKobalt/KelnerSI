package waiter.CustomerPrioritization;

import EncogNN.NeuronNetwork;
import EncogNN.ServiceFrame;
import waiter.WaiterPresenter;
import waiter.customer.Customer;

import java.util.List;

/**
 * Created by JanJa on 23.05.2017.
 */
public class NeuralCustomerPrioritizer implements CustomerPrioritization {

    WaiterPresenter presenter;

    List<Customer> customers;

    NeuronNetwork network;

    public NeuralCustomerPrioritizer(WaiterPresenter presenter) {
        this.presenter = presenter;
        network = new NeuronNetwork();


    }


    @Override
    public Customer getTheMostUrgentCustomer() {
        Double priority;
        Double maxPriority = 0d;
        Customer theMostUrgentCustomer = null;
        System.out.println("---------START-----------");
        for (Customer customer : customers) {
            if (customer.isWaiting()) {

                priority = evaluatePriorityOfSingleUser(customer);

                if (priority > maxPriority) {
                    theMostUrgentCustomer = customer;
                    maxPriority = priority;
                }
            }
        }
        System.out.println("---------END-----------");
        return theMostUrgentCustomer;
    }

    private Double evaluatePriorityOfSingleUser(Customer customer) {

        double waiting =  customer.getWaitingTime();
        double distance =  presenter.getWaiter().getTileCoordinate().distance(customer.getTileCoordinate());

        return   network.calculateHowUrgentIsCustomer(new ServiceFrame(waiting, distance, customer.getState(), presenter.getOvercrowded()));
    }

    @Override
    public void setCustomers(List<Customer> customers) {

        this.customers = customers;

    }
}
