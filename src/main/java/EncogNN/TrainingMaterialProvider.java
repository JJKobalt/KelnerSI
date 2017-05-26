package EncogNN;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JanJa on 23.05.2017.
 */
public class TrainingMaterialProvider {
    final String fileName = "trainingMaterial.txt";
    List<double[]> input;
    List<double[]> idealOutput;


    public TrainingMaterialProvider() {
        ClassLoader classLoader = getClass().getClassLoader();
        String filePath = classLoader.getResource(fileName).getPath();

        input = new ArrayList<>();

        idealOutput = new ArrayList<>();
        readFromTrainingMaterial(filePath);
    }

    private void readFromTrainingMaterial(String filePath) {

        String line ;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            while ((line = reader.readLine()) != null) {


                    String[] splitLine = line.split(";");

                    double[] inputArray = stringToDoubleArray(splitLine[0]);

                    input.add(inputArray);

                    double[] outputArray = toOutputArray(splitLine[1]);
                    idealOutput.add(outputArray);




            }
        } catch (FileNotFoundException e) {


            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double[] toOutputArray(String s) {
        s = s.replaceAll("\\s+","");

        double[] value = {Double.parseDouble(s)};
        value[0] = value[0] /10;
        return value;
    }


    private double[] stringToDoubleArray(String inputLine) {


        Pattern p = Pattern.compile("(\\d+(?:\\.\\d+))");
        Matcher m = p.matcher(inputLine);

        List<Double> listAnswer = new ArrayList<>();

        while (m.find()) {
            double foundedDouble = Double.parseDouble(m.group(1));
            listAnswer.add(foundedDouble);
        }


        double[] answer = new double[listAnswer.size()];
        for (int i = 0; i < listAnswer.size(); i++) {
            answer[i] = listAnswer.get(i);

        }
        return answer;
    }

    public double[][] getTrainingInput() {
        double[][] trainingFile = new double[input.size()][];

        for(int i=0; i< input.size();i++)
        {
            trainingFile[i]=input.get(i);
            System.out.println( "Training input " + Arrays.toString(trainingFile[i]));

        }
        return trainingFile;

    }

    public double[][] getTrainingOutput() {
        double[][] TrainingOutput = new double[idealOutput.size()][];

        for(int i=0; i< input.size();i++)
        {
            TrainingOutput[i]=idealOutput.get(i);
            System.out.println( "Training output " + Arrays.toString(TrainingOutput[i]));

        }
        return TrainingOutput;
    }
}
