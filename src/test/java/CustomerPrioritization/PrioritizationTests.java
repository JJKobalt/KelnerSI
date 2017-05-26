package CustomerPrioritization;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import waiter.CustomerPrioritization.FuzzyCustomerPrioritization;

/**
 * Created by JanJa on 16.05.2017.
 */
public class PrioritizationTests {

    @Test
    public void shouldLoadFCLFile() throws Exception {
        FuzzyCustomerPrioritization fcp = new FuzzyCustomerPrioritization();
        Assert.assertTrue(fcp.isFisLoaded());
    }
/*
    @Test
    public void shouldBeAbleToGetCustomers() {

        FuzzyCustomerPrioritization fcp = new FuzzyCustomerPrioritization();
        List<Customer> Customers = new ArrayList<>();

        Customer customerA = new Customer(2, 2 , );
        customerA.setStartedWaitingTime(LocalTime.now().minus(20, ChronoUnit.MINUTES));

        Customer customerB = new Customer(3, 3);
        customerA.setStartedWaitingTime(LocalTime.now().minus(30, ChronoUnit.MINUTES));

        Customers.add(customerA);
        Customers.add(customerB);
        fcp.setCustomers(Customers);

    }
*/
/*
    @Test
    public void evaluateSingleClient() {
        FuzzyCustomerPrioritization fcp = new FuzzyCustomerPrioritization();
        Customer customerA = new Customer(2, 2);
        customerA.setStartedWaitingTime(LocalTime.now().minus(20, ChronoUnit.MINUTES));

        double priority = fcp.evaluatePriorityOfSingleUser(customerA);
        System.out.println(priority);
        Assert.assertNotNull(priority);
    }

    @Test
    public void CustomerShouldKnownHowLongIsWaiting()
    {
        Customer customer = new Customer(1,1);
        customer.setStartedWaitingTime(LocalTime.now().minus(20, ChronoUnit.MINUTES));
        System.out.println(customer.getWaitingTime());

    }
*/
}
