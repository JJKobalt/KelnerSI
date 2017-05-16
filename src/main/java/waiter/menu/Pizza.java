package waiter.menu;

import java.util.HashMap;
import java.util.Map;

public class Pizza
{

    private final String name;

    private boolean sauce = true;

    private boolean cheese = true;

    private boolean mushrooms;

    private boolean ham;

    private boolean salami;

    private boolean pineapple;

    private boolean tuna;

    private boolean corn;

    private boolean onion;

    private boolean kebab;

    private boolean sausage;

    private boolean pepper;

    private boolean cayenne;

    public Pizza(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Pizza{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName()
    {
        return name;
    }

    public boolean hasSauce()
    {
        return sauce;
    }

    public Pizza withSauce()
    {
        this.sauce = true;
        return this;
    }

    public boolean hasChesee()
    {
        return cheese;
    }

    public Pizza withCheese()
    {
        this.cheese = true;
        return this;
    }

    public boolean hasMushrooms()
    {
        return mushrooms;
    }

    public Pizza withMushrooms()
    {
        this.mushrooms = true;
        return this;
    }

    public boolean hasHam()
    {
        return ham;
    }

    public Pizza withHam()
    {
        this.ham = true;
        return this;
    }

    public boolean hasSalami()
    {
        return salami;
    }

    public Pizza withSalami()
    {
        this.salami = true;
        return this;
    }

    public boolean hasPineapple()
    {
        return pineapple;
    }

    public Pizza withPineapple()
    {
        this.pineapple = true;
        return this;
    }

    public boolean hasTuna()
    {
        return tuna;
    }

    public Pizza withTuna()
    {
        this.tuna = true;
        return this;
    }

    public boolean hasCorn()
    {
        return corn;
    }

    public Pizza withCorn()
    {
        this.corn = true;
        return this;
    }

    public boolean hasOnion()
    {
        return onion;
    }

    public Pizza withOnion()
    {
        this.onion = true;
        return this;
    }

    public boolean hasKebab()
    {
        return kebab;
    }

    public Pizza withKebab()
    {
        this.kebab = true;
        return this;
    }

    public boolean hasSausage()
    {
        return sausage;
    }

    public Pizza withSausage()
    {
        this.sausage = true;
        return this;
    }

    public boolean hasCayenne()
    {
        return cayenne;
    }

    public Pizza withCayenne(){
        this.cayenne = true;
        return this;
    }

    public boolean hasPepper()
    {
        return pepper;
    }

    public Pizza withPepper(){
        this.pepper = true;
        return this;
    }


    public Map<String, String> getIngradients(){
        Map<String, String> ingradients = new HashMap<>();
        ingradients.put("cheese", this.hasChesee() ? "yes" : "no");
        ingradients.put("mushrooms", this.hasMushrooms() ? "yes" : "no");
        ingradients.put("ham", this.hasHam() ? "yes" : "no");
        ingradients.put("salami", this.hasSalami() ? "yes" : "no");
        ingradients.put("pineapple", this.hasPineapple() ? "yes" : "no");
        ingradients.put("tuna", this.hasTuna() ? "yes" : "no");
        ingradients.put("corn", this.hasCorn() ? "yes" : "no");
        ingradients.put("onion", this.hasOnion() ? "yes" : "no");
        ingradients.put("kebab", this.hasKebab() ? "yes" : "no");
        ingradients.put("sausage", this.hasSausage() ? "yes" : "no");
        ingradients.put("cayenne", this.hasCayenne() ? "yes" : "no");
        ingradients.put("pepper", this.hasPepper() ? "yes" : "no");
        return ingradients;
    }
}
