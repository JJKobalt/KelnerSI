package waiter.CustomerPrioritization;

import EncogNN.ServiceFrame;

import java.io.*;

/**
 * Created by JanJa on 23.05.2017.
 */
public class FuzzyTrainningFileCreator {

    final String FILENAME = "trainingMaterial.txt";
    private FileWriter writer;
    String path;

    public FuzzyTrainningFileCreator() {
        ClassLoader classLoader = getClass().getClassLoader();
        path = classLoader.getResource(FILENAME).getPath();
        System.err.println(path);

    }

    public void addToTrainingFile(ServiceFrame frame, double output) {

        StringBuilder line = new StringBuilder();
        line.append(frame.toString());
        line.append(" ; ");
        line.append(output);
        System.out.println("line " + line.toString());

        try {
            writer = new FileWriter(path,true);
            writer.write(line.toString() );
            writer.write( System.getProperty("line.separator") );


            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
