package waiter.plate;

import org.encog.mathutil.randomize.RandomChoice;


import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

public class PlateFactory {
    //  static final Path  platesDir = Paths.get("C:\\Users\\Dominika\\Desktop\\TalerzeApp\\");

    static File[] plates;

    static {
        try {
            File file = new File("C:\\Users\\Dominika\\Desktop\\TalerzeApp");
            plates = file.listFiles();
        }

      catch( Exception ex)

    {
        // I/O error encounted during the iteration, the cause is an IOException
        System.err.println("Plates can not be found ");
        throw new RuntimeException();
    }

}


    public static Plate getInstanceOfPlate() {
        Random random = new Random();

        return new Plate(
                plates[
                        random.ints(0, plates.length).findAny().getAsInt()
                ].getAbsolutePath()

        );
    }


}
