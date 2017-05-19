package waiter.FuzzyCustomerPrioritization;


import net.sourceforge.jFuzzyLogic.FIS;
import waiter.WaiterPresenter;
import waiter.customer.Customer;

import java.util.List;

/**
 * Created by JanJa on 16.05.2017.
 */
public class FuzzyCustomerPrioritization implements CustomerPrioritization {

    WaiterPresenter presenter;
    private final String fileName = "FL/CustomerPrioritization.fcl";
    List<Customer> customers;
    FIS fis;


    public FuzzyCustomerPrioritization(WaiterPresenter presenter) {
        this.presenter = presenter;

        fis = FIS.load(fileName, true);
    }

    public boolean isFisLoaded() {
        return !(fis == null);
    }

    @Override
    public void setCustomers(List<Customer> customers) {

        this.customers = customers;

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
                    maxPriority=priority;
                }
            }
        }
        System.out.println("---------END-----------");
        return theMostUrgentCustomer;
    }

    public double evaluatePriorityOfSingleUser(Customer customer) {


        double waiting =  customer.getWaitingTime();
        double distance =  presenter.getWaiter().getTileCoordinate().distance(customer.getTileCoordinate());
        fis.setVariable("waiting", waiting);
        fis.setVariable("distance", distance);
        fis.setVariable("state", customer.getState().toValue());
        fis.setVariable("overcrowded" , presenter.getOvercrowded());



        fis.evaluate();
        double result = fis.getVariable("priority").getValue();
        System.out.println("result: " + result +" of user named " + customer.getName() +  " who is waiting " + waiting + "   and is " + distance + " distance away");
        return result;
    }


}
