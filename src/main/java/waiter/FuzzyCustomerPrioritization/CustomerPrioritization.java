package waiter.FuzzyCustomerPrioritization;

import waiter.customer.Customer;

import java.util.List;

/**
 * Created by JanJa on 16.05.2017.
 */
public interface CustomerPrioritization {

    public Customer getTheMostUrgentCustomer();
    public void setCustomers(List<Customer> customers);
}
