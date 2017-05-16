package waiter.menu;

import dt.BadDecisionException;
import dt.DecisionTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class Menu
{

    private final List<Pizza> pizzas = new ArrayList<>();
    private DecisionTree vegetableDecisionTree;
    private DecisionTree hotDecisionTree;

    public Menu()
    {
        vegetableDecisionTree = buildVegetableDecisionTree();
        hotDecisionTree = buildHotDecisionTree();

        //System.out.println("hot tree: " + hotDecisionTree.toString());
        //System.out.println("vegetable tree: " + vegetableDecisionTree.toString());

        List<Pizza> pizzasToAdd = Arrays.asList(
                new Pizza("Margherita"),
                new Pizza("Funghi").withMushrooms(),
                new Pizza("Vegetariana").withMushrooms().withCorn().withOnion(),
                new Pizza("Vegetariana Hot").withMushrooms().withCorn().withOnion().withOnion().withPepper(),
                new Pizza("Vesuvio").withHam(),
                new Pizza("Salami").withSalami(),
                new Pizza("Capriciosa").withHam().withMushrooms(),
                new Pizza("Hawajska").withHam().withPineapple(),
                new Pizza("Toscana").withMushrooms().withSalami(),
                new Pizza("Tono").withTuna().withOnion(),
                new Pizza("Bolonia").withHam().withMushrooms().withOnion(),
                new Pizza("Milano").withHam().withSausage().withCorn().withOnion(),
                new Pizza("Rzeźnicka").withSalami().withHam().withSausage().withOnion(),
                new Pizza("Kebab").withKebab().withOnion(),
                new Pizza("Diabolo").withSalami().withCayenne().withOnion().withHam(),
                new Pizza("Rzeźnicka Hot").withSalami().withHam().withSausage().withOnion().withPepper()
        );

        pizzas.addAll(pizzasToAdd);
    }


    public void addPizza(Pizza pizza)
    {
        pizzas.add(pizza);
        vegetableDecisionTree = buildVegetableDecisionTree();
    }

    public Pizza getPizzaByName(String name){

        for(Pizza pizza : pizzas){
            if(name.equalsIgnoreCase(pizza.getName())){
                return pizza;
            }
        }

        throw new NoSuchElementException("No pizza with name " + name  + " found.");
    }

    public Pizza getPizzaById(int id){

        if(id < pizzas.size()){
            return pizzas.get(id);
        }

        throw new NoSuchElementException("No pizza with id " + id  + " found.");
    }

    public int getPizzasCount(){
        return pizzas.size();
    }

    private DecisionTree buildHotDecisionTree()
    {
        DecisionTree dt = new DecisionTree();
        dt.setAttributes(new String[]{"cheese", "mushrooms", "ham", "salami", "pineapple", "tuna", "corn",
                "onion", "kebab", "sausage", "cayenne"});

        try
        {
            dt.addExample(new Pizza("").withCayenne().getIngradients(), true);
            dt.addExample(new Pizza("").withOnion().getIngradients(), false);
            dt.addExample(new Pizza("").withOnion().withPepper().getIngradients(), true);
            dt.addExample(new Pizza("").withPepper().getIngradients(), false);


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        dt.compile();

        return dt;
    }

    private DecisionTree buildVegetableDecisionTree()
    {
        DecisionTree dt = new DecisionTree();
        dt.setAttributes(new String[]{"cheese", "mushrooms", "ham", "salami", "pineapple", "tuna", "corn",
        "onion", "kebab", "sausage", "cayenne"});

        try
        {
            dt.addExample(new Pizza("").getIngradients(), true);
            dt.addExample(new Pizza("").withCheese().getIngradients(), true);
            dt.addExample(new Pizza("").withMushrooms().getIngradients(), true);
            dt.addExample(new Pizza("").withPineapple().getIngradients(), true);
            dt.addExample(new Pizza("").withCorn().getIngradients(), true);
            dt.addExample(new Pizza("").withOnion().getIngradients(), true);
            dt.addExample(new Pizza("").withCayenne().getIngradients(), true);
            dt.addExample(new Pizza("").withPepper().getIngradients(), true);

            dt.addExample(new Pizza("").withHam().getIngradients(), false);
            dt.addExample(new Pizza("").withSalami().getIngradients(), false);
            dt.addExample(new Pizza("").withTuna().getIngradients(), false);
            dt.addExample(new Pizza("").withKebab().getIngradients(), false);
            dt.addExample(new Pizza("").withSausage().getIngradients(), false);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        dt.compile();


        return dt;
    }

    public boolean isVegetable(Pizza pizza){
        try
        {
            return vegetableDecisionTree.apply(pizza.getIngradients());
        }
        catch(BadDecisionException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isHot(Pizza pizza){
        try
        {
            return hotDecisionTree.apply(pizza.getIngradients());
        }
        catch(BadDecisionException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public Stream<Pizza> getPizzas(){
        return pizzas.stream();
    }

}
