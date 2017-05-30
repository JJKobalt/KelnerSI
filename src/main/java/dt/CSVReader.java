package dt;

import waiter.menu.Pizza;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVReader
{

    public static Map<Pizza, Boolean> read(String csvFile) {

        String line = "";
        String cvsSplitBy = ";";
        boolean skippedFirst = false;

        Map<Pizza, Boolean> pizzas = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                if(!skippedFirst){
                    skippedFirst = true;
                    continue;
                }

                // use comma as separator
                String[] entry = line.split(cvsSplitBy);
                Pizza pizza = convertToPizza(entry);
                pizzas.put(pizza, stringToBool(entry[13]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return pizzas;
    }

    private static Pizza convertToPizza(String[] entry)
    {
        //sauce;cheese;mushrooms;ham;salami;pineapple;tuna;corn;onion;kebab;sausage;pepper;cayenne;hot
        Pizza pizza = new Pizza("foo");
        pizza.setSauce(stringToBool(entry[0]));
        pizza.setCheese(stringToBool(entry[1]));
        pizza.setMushrooms(stringToBool(entry[2]));
        pizza.setHam(stringToBool(entry[3]));
        pizza.setSalami(stringToBool(entry[4]));
        pizza.setPineapple(stringToBool(entry[5]));
        pizza.setTuna(stringToBool(entry[6]));
        pizza.setCorn(stringToBool(entry[7]));
        pizza.setOnion(stringToBool(entry[8]));
        pizza.setKebab(stringToBool(entry[9]));
        pizza.setSausage(stringToBool(entry[10]));
        pizza.setPepper(stringToBool(entry[11]));
        pizza.setCayenne(stringToBool(entry[12]));
        return pizza;
    }

    private static boolean stringToBool(String str){
        return "1".equalsIgnoreCase(str);
    }
}
