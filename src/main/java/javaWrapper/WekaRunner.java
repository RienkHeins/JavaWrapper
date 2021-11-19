package javaWrapper;

import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WekaRunner {
    private final String fileName;
    private final String wekaModel = "testdata/RandomForest.model";

    public WekaRunner(String file) {
        fileName = file;
    }

    public void Start(){
        // Runs all the arguments
        String trainingFile = "testdata/TrainingData.arff";
        try {
            Instances instances = loadArff(trainingFile);
            printInstances(instances);
            RandomForest randomForest = buildClassifier(instances);
            saveClassifier(randomForest);
            RandomForest fromFile = loadClassifier();
            Instances unknownInstances = loadArff(fileName);
            System.out.println("\nunclassified unknownInstances = \n" + unknownInstances);
            classifyNewInstance(fromFile, unknownInstances);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void classifyNewInstance(RandomForest randomForest, Instances unknownInstances) throws Exception {
        // create copy
        Instances labeled = new Instances(unknownInstances);
        // label instances
        for (int i = 0; i < unknownInstances.numInstances(); i++) {
            double clsLabel = randomForest.classifyInstance(unknownInstances.instance(i));
            labeled.instance(i).setClassValue(clsLabel);
        }
        System.out.println("\nNew, labeled = \n" + labeled);
    }

    private RandomForest buildClassifier(Instances instances) throws Exception {
        // Builds and returns a randomforest classifier
        RandomForest classifier = new RandomForest();
        classifier.buildClassifier(instances);
        return classifier;
    }

    private void saveClassifier(RandomForest randomForest) throws Exception {
        // Saves the randomforest classifier
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(wekaModel));
        oos.writeObject(randomForest);
        oos.flush();
        oos.close();
    }

    private RandomForest loadClassifier() throws Exception {
        return (RandomForest) weka.core.SerializationHelper.read(wekaModel);
    }

    private void printInstances(Instances instances) {
        // Prints out the new instances
        int numAttributes = instances.numAttributes();

        for (int i = 0; i < numAttributes; i++) {
            System.out.println("attribute " + i + " = " + instances.attribute(i));
        }

        System.out.println("class index = " + instances.classIndex());

        int numInstances = instances.numInstances();
        for (int i = 0; i < numInstances; i++) {
            if (i == 6) break;
            Instance instance = instances.instance(i);
            System.out.println("instance = " + instance);
        }
    }

    private Instances loadArff(String datafile) throws IOException {
        // loads a .arff file and makes it an Instances object
        try {
            DataSource source = new DataSource(datafile);
            Instances data = source.getDataSet();
            if (data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);
            return data;
        } catch (Exception e) {
            throw new IOException("could not read from file");
        }
    }
}
