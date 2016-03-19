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
    static final String inputFile = "/home/sari/Development/weka-test/src/com/company/in.txt" ;
    static final String outputFilee = "/home/sari/Development/weka-test/out.arff" ;
    static final String outputFile = "/home/sari/Development/weka-test/src/com/company/out.txt";
    public static void main(String[] args) throws Exception {
        String inData = "";
        System.out.print("Aha");
        try {
            inData = new String(Files.readAllBytes(Paths.get(inputFile)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray dataSetArray = new JSONArray(inData);

        FileWriter fw = null;
        try {
            fw = new FileWriter(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("@relation room");
        bw.newLine();
        bw.newLine();
        ArrayList<String> rooms = new ArrayList<>();
        ArrayList<String> referencePoints = extractRPIDList(dataSetArray);
        for(Object item:dataSetArray){
            rooms.add(((JSONObject) item).getString("room_id"));
        }
        Set<String> noDuplicates = new LinkedHashSet<String>(rooms);
        rooms.clear();
        rooms.addAll(noDuplicates);

        //bw.write(referencePoints.toString());
        //bw.newLine();
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
        //ArffLoader loader = new ArffLoader();
        //loader.setFile(new File(outputFilee));
        //Instances structure = loader.getStructure();
        //structure.setClassIndex(structure.numAttributes()-1);

        Instances training = new Instances(new BufferedReader(new FileReader(outputFilee)));
        training.setClassIndex(training.numAttributes()-1);
        NaiveBayes nb = new NaiveBayes();
        nb.buildClassifier(training);
        String test="/home/sari/Development/weka-test/test-full.arff";
        Instances unlabeled = new Instances(new BufferedReader(new FileReader(test)));
        unlabeled.setClassIndex(unlabeled.numAttributes()-1);
        Instances labeled = new Instances(unlabeled);

        for (int i = 0; i < unlabeled.numInstances(); i++) {
            double clsLabel = nb.classifyInstance(unlabeled.instance(i));
            labeled.instance(i).setClassValue(clsLabel);
            System.out.println(clsLabel);
        }
        System.out.print(labeled);
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

}
