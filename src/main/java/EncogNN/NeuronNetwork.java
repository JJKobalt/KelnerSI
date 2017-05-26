package EncogNN;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.persist.EncogDirectoryPersistence;

import java.io.File;

/**
 * Created by JanJa on 23.05.2017.
 */
public class NeuronNetwork {

    BasicNetwork network;
    final String FILENAME = "NeuralNetwork.txt";


    public NeuronNetwork() {
        initializeNetwork();
    }


    private void initializeNetwork() {
        try {
            File f = new File(FILENAME);
            System.out.println(f);
            network = (BasicNetwork) EncogDirectoryPersistence.loadObject(new File(FILENAME));
            System.out.println("Load Network");


        } catch (Exception e) {
            System.out.println("Create New Network");
            train();
        }
    }

    private void train() {
        MLDataSet trainingSet = setTrainingResources();
        network = getBasicNetwork();
        final ResilientPropagation train = new ResilientPropagation(network, trainingSet);

        System.out.println("Training Started");
        do {
            train.iteration();
            System.out.println("Error " + train.getError());
        } while (train.getError() > 0.01);

        System.out.println("Training Finished");
        double e = network.calculateError(trainingSet);
        System.out.println("Network trained to error: " + e);
        EncogDirectoryPersistence.saveObject(new File(FILENAME), network);
    }

    private MLDataSet setTrainingResources() {
        TrainingMaterialProvider trainingMaterialProvider = new TrainingMaterialProvider();


        double TrainingInput[][] = trainingMaterialProvider.getTrainingInput();


        double TrainingOutput[][] = trainingMaterialProvider.getTrainingOutput();

        return new BasicMLDataSet(TrainingInput, TrainingOutput);
    }


    public BasicNetwork getBasicNetwork() {
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, true, 6));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 3));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1));
        network.getStructure().finalizeStructure();

        network.reset();
        return network;
    }


    public double calculateHowUrgentIsCustomer(ServiceFrame frame) {
        double[] output = new double[1];
        network.compute(frame.toInputArray(),output);
        return  output[0];
    }
}
