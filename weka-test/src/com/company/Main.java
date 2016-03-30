package com.company;

import org.json.*;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class Main {
    static final String SETTINGS_FILE_PATH = "settings" ;
    static final String TRAINING_ARFF_PATH= "training.arff" ;
    static final String TRAINING_JSON_PATH = "trainingJSON.data";
    static final String INPUT_JSON_PATH = "inputJSON.data";
    static final String INPUT_ARFF_PATH = "input.arff";
    public static void main(String[] args) throws Exception {

        NaiveBayes nb = new NaiveBayes();
        if(args[0].equals("--help") || args[0].equals("-h")){
            System.out.println("--train or -t");
            System.out.println("--classify or -c");
        } else
        if(args[0].equals("--train") || args[0].equals("-t")){
            buildTrainingARFFfromJSON(new File(TRAINING_JSON_PATH));
            Instances training = new Instances(new BufferedReader(new FileReader(TRAINING_ARFF_PATH)));
            training.setClassIndex(training.numAttributes()-1);
            nb.buildClassifier(training);
        } else
        if(args[0].equals("--classify") || args[0].equals("-c")){
            buildTestARFFfromJSON();
            Instances training = new Instances(new BufferedReader(new FileReader(TRAINING_ARFF_PATH)));
            training.setClassIndex(training.numAttributes()-1);
            nb.buildClassifier(training);
            Instances unlabeled = new Instances(new BufferedReader(new FileReader(INPUT_ARFF_PATH)));
            unlabeled.setClassIndex(unlabeled.numAttributes()-1);
            Instances labeled = new Instances(unlabeled);
            for (int i = 0; i < unlabeled.numInstances(); i++) {
                double clsLabel = nb.classifyInstance(unlabeled.instance(i));
                labeled.instance(i).setClassValue(clsLabel);
                System.out.println(labeled.instance(0).classAttribute().value((int)clsLabel));

            }

        }




        //ArffLoader loader = new ArffLoader();
        //loader.setFile(new File(outputFilee));
        //Instances structure = loader.getStructure();
        //structure.setClassIndex(structure.numAttributes()-1);


    }

    public static ArrayList<String> extractRPIDList(JSONArray data)
    {
        ArrayList<String> rpids = new ArrayList<>();
        for(Object reading: data){
            for(Object pair: ((JSONObject)reading).getJSONArray("rpv_pair")){
                rpids.add(((JSONObject) pair).getString("RPID"));
            }
        }
        Set<String> noDuplicates = new LinkedHashSet<String>(rpids);
        rpids.clear();
        rpids.addAll(noDuplicates);

        return rpids;
    }

    public static double getValueByRPID(JSONObject reading, String RPID){
        JSONArray pairs = reading.getJSONArray("rpv_pair");
        for(Object pair:pairs){
            if(RPID.equals(((JSONObject) pair).getString("RPID")))
                return ((JSONObject) pair).getDouble("value");
        }

        return 0;
    }
    public static void buildTestARFFfromJSON(){
        try {
            File settings = new File(SETTINGS_FILE_PATH);
            File arffFile = new File(INPUT_ARFF_PATH);
            File jsonFile = new File(INPUT_JSON_PATH);
            File trainingJson = new File(TRAINING_JSON_PATH);
            Files.copy(settings.toPath(),arffFile.toPath());
            FileWriter fw = new FileWriter(INPUT_ARFF_PATH, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("@data");
            bw.newLine();
            JSONObject reading = new JSONObject(new String(Files.readAllBytes(Paths.get(jsonFile.getPath())), StandardCharsets.UTF_8));
            JSONArray dataSetArray = new JSONArray(new String(Files.readAllBytes(Paths.get(trainingJson.getPath())), StandardCharsets.UTF_8));
            ArrayList<String> referencePoints = new ArrayList<>();
                referencePoints = extractRPIDList(dataSetArray);
            for(String RP: referencePoints)
                {
                    bw.write(Double.toString(getValueByRPID(reading,RP))+",");
                }
            bw.write("?");
            bw.newLine();

            bw.close();
            System.out.println("ARFF build!");
        } catch (IOException e) {
            System.out.println("Failed to build ARFF: "+e);
        }
    }
    public static void buildTrainingARFFfromJSON(File inputFile){
        String inData = "";
        System.out.println("Building settings ...");
        try {
            inData = new String(Files.readAllBytes(Paths.get(inputFile.getPath())), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray dataSetArray = new JSONArray(inData);
        ArrayList<String> referencePoints = new ArrayList<>();
        FileWriter fw = null;
        try {
        fw = new FileWriter(SETTINGS_FILE_PATH);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("@relation room\n\n");
        ArrayList<String> rooms = new ArrayList<>();
        referencePoints = extractRPIDList(dataSetArray);
        for(Object item:dataSetArray){
            rooms.add(((JSONObject) item).getString("room_id"));
        }
        Set<String> noDuplicates = new LinkedHashSet<String>(rooms);
        rooms.clear();
        rooms.addAll(noDuplicates);
        for(String rp:referencePoints){
            bw.write("@attribute "+rp+" "
                    +"NUMERIC");
            bw.newLine();
        }
        bw.write("@attribute class {");
        for(String room:rooms){
            bw.write(room+",");
        }
        bw.write("test_room");
        bw.write("}");
        bw.newLine();bw.newLine();
        bw.close();
        System.out.println("Settings built!");
        } catch (IOException e) {
            System.out.println("Failed to build settings: "+e);
        }

        System.out.println("Building ARFF from JSON ...");
        try {
            File settings = new File(SETTINGS_FILE_PATH);
            File arffFile = new File(TRAINING_ARFF_PATH);
            Files.copy(settings.toPath(),arffFile.toPath());

            fw = new FileWriter(TRAINING_ARFF_PATH, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("@data");
            bw.newLine();
            for(Object reading: dataSetArray){
                for(String RP: referencePoints)
                {
                    bw.write(Double.toString(getValueByRPID((JSONObject)reading,RP))+",");
                }
                bw.write(((JSONObject) reading).getString("room_id"));
                bw.newLine();
            }
            bw.close();
            System.out.println("ARFF build!");
        } catch (IOException e) {
            System.out.println("Failed to build ARFF: "+e);
        }
    }

}
