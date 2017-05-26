package EncogNN;

import waiter.customer.*;

/**
 * Created by JanJa on 23.05.2017.
 */
public class ServiceFrame {

    double waiting;
    double distance;
    CustomerState state;
    double overcrowded;


    public ServiceFrame(double waiting, double distance, CustomerState state, double overcrowded) {
        this.waiting = waiting;
        this.distance = distance;
        this.state = state;
        this.overcrowded = overcrowded;
    }

    public double[] toInputArray() {
        double[] input = {0,  //waiting
                0,  //distance
                0,  //justCame
                0, //waitingToOrder
                0, //waitingToEat
                0, //pay
                0, // overcrowded
        };

        input[0] = waiting;
        input[1] = distance;
        if (state instanceof CustomerJustCame) input[2] = 1;
        if (state instanceof CustomerWaitingToOrder) input[3] = 1;
        if (state instanceof CustomerWaitingToEat) input[4] = 1;
        if (state instanceof CustomerPay) {
            System.err.println("STATE IS PAY");
            input[5] = 1;
        }
        input[6] = overcrowded;

        return input;
    }


    public String toString() {
        double[] input = toInputArray();
        StringBuilder sb = new StringBuilder();
        for (Double d : input) {
            sb.append(d.toString() + " ");

        }
        return sb.toString();
    }
}
