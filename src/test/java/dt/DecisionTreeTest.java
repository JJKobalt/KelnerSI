package dt;


import org.junit.Before;
import org.junit.jupiter.api.Test;
import waiter.menu.Pizza;

import java.util.*;

public class DecisionTreeTest
{

    @Test
    public void name() throws Exception
    {

        DecisionTree dt = new DecisionTree();

        dt.setAttributes(new String[]{"Outlook",  "Temperature", "Humidity", "Wind"})
                .addExample( new String[]{"Rain", "Mild", "High", "Strong"}, false);


        Map<String, String> case1 = new HashMap<>();
        case1.put("Outlook", "Overcast");
        case1.put("Temperature", "Hot");
        case1.put("Humidity", "High");
        case1.put("Wind", "Strong");

        System.out.println();

    }


    private List<Pizza> menu = new ArrayList<>();

    @Before
    public void setUp() throws Exception
    {
        Pizza margherita = new Pizza("Margherita");
        Pizza funghi = new Pizza("Funghi").withMushrooms();
        Pizza vesuvio = new Pizza("Vesuvio").withHam();
        menu = Arrays.asList(margherita, funghi, vesuvio);

    }

    private String[] buildExampleOfPizza(Pizza pizza){
        return new String[]{
                pizza.hasChesee() ? "yes" : "no",
                pizza.hasMushrooms() ? "yes" : "no",
                pizza.hasHam() ? "yes" : "no"
        };
    }

    @org.junit.jupiter.api.Test
    public void shouldFindVegetablePizzas() throws Exception
    {
        DecisionTree dt = new DecisionTree();

        dt.setAttributes(new String[]{"cheese", "mushrooms", "ham" });
        dt.addExample(buildExampleOfPizza(menu.get(0)), true);
        dt.addExample(buildExampleOfPizza(menu.get(1)), true);
        dt.addExample(buildExampleOfPizza(menu.get(2)), false);


        Map<String, String> case1 = new HashMap<>();
        case1.put("cheese", "yes");
        case1.put("mushrooms", "no");
        case1.put("ham", "no");

        System.out.println(dt.apply(case1));


    }
}