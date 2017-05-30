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

    public void setSauce(boolean sauce)
    {
        this.sauce = sauce;
    }

    public void setCheese(boolean cheese)
    {
        this.cheese = cheese;
    }

    public void setMushrooms(boolean mushrooms)
    {
        this.mushrooms = mushrooms;
    }

    public void setHam(boolean ham)
    {
        this.ham = ham;
    }

    public void setSalami(boolean salami)
    {
        this.salami = salami;
    }

    public void setPineapple(boolean pineapple)
    {
        this.pineapple = pineapple;
    }

    public void setTuna(boolean tuna)
    {
        this.tuna = tuna;
    }

    public void setCorn(boolean corn)
    {
        this.corn = corn;
    }

    public void setOnion(boolean onion)
    {
        this.onion = onion;
    }

    public void setKebab(boolean kebab)
    {
        this.kebab = kebab;
    }

    public void setSausage(boolean sausage)
    {
        this.sausage = sausage;
    }

    public void setPepper(boolean pepper)
    {
        this.pepper = pepper;
    }

    public void setCayenne(boolean cayenne)
    {
        this.cayenne = cayenne;
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

    @Override
    public String toString()
    {
        return "Pizza{" +
                "name='" + name + '\'' +
                ", sauce=" + sauce +
                ", cheese=" + cheese +
                ", mushrooms=" + mushrooms +
                ", ham=" + ham +
                ", salami=" + salami +
                ", pineapple=" + pineapple +
                ", tuna=" + tuna +
                ", corn=" + corn +
                ", onion=" + onion +
                ", kebab=" + kebab +
                ", sausage=" + sausage +
                ", pepper=" + pepper +
                ", cayenne=" + cayenne +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof Pizza))
        {
            return false;
        }

        Pizza pizza = (Pizza) o;

        if(sauce != pizza.sauce)
        {
            return false;
        }
        if(cheese != pizza.cheese)
        {
            return false;
        }
        if(mushrooms != pizza.mushrooms)
        {
            return false;
        }
        if(ham != pizza.ham)
        {
            return false;
        }
        if(salami != pizza.salami)
        {
            return false;
        }
        if(pineapple != pizza.pineapple)
        {
            return false;
        }
        if(tuna != pizza.tuna)
        {
            return false;
        }
        if(corn != pizza.corn)
        {
            return false;
        }
        if(onion != pizza.onion)
        {
            return false;
        }
        if(kebab != pizza.kebab)
        {
            return false;
        }
        if(sausage != pizza.sausage)
        {
            return false;
        }
        if(pepper != pizza.pepper)
        {
            return false;
        }
        return cayenne == pizza.cayenne;
    }

    @Override
    public int hashCode()
    {
        int result = (sauce ? 1 : 0);
        result = 31 * result + (cheese ? 1 : 0);
        result = 31 * result + (mushrooms ? 1 : 0);
        result = 31 * result + (ham ? 1 : 0);
        result = 31 * result + (salami ? 1 : 0);
        result = 31 * result + (pineapple ? 1 : 0);
        result = 31 * result + (tuna ? 1 : 0);
        result = 31 * result + (corn ? 1 : 0);
        result = 31 * result + (onion ? 1 : 0);
        result = 31 * result + (kebab ? 1 : 0);
        result = 31 * result + (sausage ? 1 : 0);
        result = 31 * result + (pepper ? 1 : 0);
        result = 31 * result + (cayenne ? 1 : 0);
        return result;
    }
}
