package waiter.menu;

import org.junit.Assert;
import org.junit.Test;

public class MenuTest
{

    @Test
    public void firstFourPizzasAreVegetable() throws Exception
    {

        Menu menu = new Menu();

        for(int i = 0; i < 4; i++)
        {
            Pizza pizza = menu.getPizzaById(i);
            Assert.assertEquals(pizza + " jest wegetariańska!", true, menu.isVegetable(pizza));
        }

        for(int i = 4; i < menu.getPizzasCount(); i++)
        {
            Pizza pizza = menu.getPizzaById(i);
            Assert.assertEquals(pizza + " nie jest wegetariańska!", false, menu.isVegetable(pizza));
        }

    }


    @Test
    public void shouldClassifyAddedPizza() throws Exception
    {
        Pizza pizza = new Pizza("Testowa").withPineapple().withMushrooms().withCayenne();
        Menu menu = new Menu();

        Assert.assertEquals(true, menu.isVegetable(pizza));
    }

    @Test
    public void shouldClassifyHotPizza() throws Exception
    {
        Menu menu = new Menu();
        Assert.assertEquals(true, menu.isHot(menu.getPizzaByName("Diabolo")));
        Assert.assertEquals(true, menu.isHot(menu.getPizzaByName("Rzeźnicka hot")));
        Assert.assertEquals(true, menu.isHot(menu.getPizzaByName("Vegetariana hot")));

        for(int i = 0; i < menu.getPizzasCount(); i++)
        {
            Pizza pizza = menu.getPizzaById(i);

            if(!"Diabolo".equalsIgnoreCase(pizza.getName()) &&
                    !"Rzeźnicka hot".equalsIgnoreCase(pizza.getName()) &&
                    !"Vegetariana Hot".equalsIgnoreCase(pizza.getName()))
            {
                Assert.assertEquals(false, menu.isHot(pizza));
            }
        }
    }
}