package waiter.customer;

import PlatesNN.PlateChecker;
import waiter.WaiterPresenter;
import waiter.plate.Plate;
import waiter.plate.PlateFactory;

/**
 * Created by JanJa on 16.05.2017.
 */
public class CustomerWaitingToEat extends CustomerState {
    public CustomerWaitingToEat(WaiterPresenter presenter, Customer customer) {
        super(presenter, customer);

    }

    @Override
    void service() {

        presenter.orderWaiterToStandNextTo(4, 5);

        presenter.orderWaiterToStandNextTo(customer.getTileX(), customer.getTileY());
        Plate plate = PlateFactory.getInstanceOfPlate();
        while ( true){
            plate = PlateFactory.getInstanceOfPlate();
            System.out.println("Chosen Plate: " + plate.getPhotoDir());
            if (PlateChecker.checkPlate(plate)){
                System.out.println("Danie");
                break;
            }
            else {
                System.out.println("Pusto");
            }
        }

        customer.setState(new CustomerEat(presenter,customer));
    }

    @Override
    public double toValue() {
        return 4;
    }
}
