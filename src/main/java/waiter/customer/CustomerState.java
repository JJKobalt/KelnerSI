package waiter.customer;

import waiter.WaiterPresenter;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created by JanJa on 16.05.2017.
 */
public abstract class CustomerState {

    WaiterPresenter presenter;
    Customer customer;

    public CustomerState(WaiterPresenter presenter, Customer customer) {
        this.presenter = presenter;
        this.customer = customer;
        customer.setStartedWaitingTime(LocalTime.now());
    }

    abstract void  service() ;


    public void waitFromToAndLaterChangeState(int minTime, int maxTime, CustomerState nextState){

        int waitingTime = ThreadLocalRandom.current().nextInt(minTime, maxTime + 1);


        Runnable wait = () -> {
            Random generator = new Random();
            try {
                TimeUnit.SECONDS.sleep(waitingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            customer.setState(nextState);
            presenter.redrawView();
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(wait);

        executorService.shutdown();

    }

    public abstract double toValue();
}
