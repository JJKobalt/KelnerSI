package PlatesNN;

import waiter.plate.Plate;

public class PlateChecker  {
    private static final PythonConnector python = new PythonConnector();

    public static boolean checkPlate(Plate plate ){
        return  python.parse(plate.getPhotoDir());

    }
}
