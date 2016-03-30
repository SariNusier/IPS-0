package com.company;

import org.json.JSONArray;
import org.json.JSONObject;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;


public class Learner {


    /** Naive Bayes
     *
     * @param building_id
     * @param JSONData
     * @return
     */
    public static NaiveBayes learnFromJSON_NB(String building_id, JSONArray JSONData){
        makeLearnerARFFfromJSON(building_id,JSONData);
        NaiveBayes nb = new NaiveBayes();
        try {
            Instances instances = new Instances(new BufferedReader(new FileReader(building_id+".arff")));
            instances.setClassIndex(instances.numAttributes()-1);
            nb.buildClassifier(instances);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nb;
    }

    /**Bayes Networks
     *
     * @param building_id
     * @param JSONData
     * @return
     */
    public static BayesNet learnFromJSON_BN(String building_id, JSONArray JSONData){
        makeLearnerARFFfromJSON(building_id,JSONData);
        BayesNet bn = new BayesNet();
        try {
            Instances instances = new Instances(new BufferedReader(new FileReader(building_id+".arff")));
            instances.setClassIndex(instances.numAttributes()-1);
            bn.buildClassifier(instances);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bn;
    }

    public static BayesNet getClassifierBN(String building_id){
        BayesNet bn = new BayesNet();
        try {
            Instances instances = new Instances(new BufferedReader(new FileReader(building_id+".arff")));
            instances.setClassIndex(instances.numAttributes()-1);
            bn.buildClassifier(instances);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bn;
    }

    public static NaiveBayes getClassifierNB(String building_id){
        NaiveBayes nb = new NaiveBayes();
        try {
            Instances instances = new Instances(new BufferedReader(new FileReader(building_id+".arff")));
            instances.setClassIndex(instances.numAttributes()-1);
            nb.buildClassifier(instances);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nb;
    }

    public static String classify_NB(String building_id, JSONArray JSONdata, NaiveBayes nb){
        makeClassifierARFF(building_id,JSONdata);
        Instances unlabeled;
        Instances labeled = null;
        double clsLabel = 0;
        try {
            unlabeled = new Instances(new BufferedReader(new FileReader(building_id+"_temp.arff")));
            unlabeled.setClassIndex(unlabeled.numAttributes()-1);
            labeled = new Instances(unlabeled);
            clsLabel = nb.classifyInstance(unlabeled.firstInstance());
            labeled.firstInstance().setClassValue(clsLabel);
            Files.deleteIfExists(Paths.get(building_id+"_temp.arff"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return labeled.instance(0).classAttribute().value((int)clsLabel);

    }

    public static String classify_BN(String building_id, JSONArray JSONdata, BayesNet bn){
        makeClassifierARFF(building_id,JSONdata);
        Instances unlabeled;
        Instances labeled = null;
        double clsLabel = 0;
        try {
            unlabeled = new Instances(new BufferedReader(new FileReader(building_id+"_temp.arff")));
            unlabeled.setClassIndex(unlabeled.numAttributes()-1);
            labeled = new Instances(unlabeled);
            clsLabel = bn.classifyInstance(unlabeled.firstInstance());
            labeled.firstInstance().setClassValue(clsLabel);
            Files.deleteIfExists(Paths.get(building_id+"_temp.arff"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return labeled.instance(0).classAttribute().value((int)clsLabel);

    }

    public static void makeLearnerARFFfromJSON(String building_id, JSONArray JSONData){
        Path arffPath = Paths.get(building_id+".arff");
        Path roomsPath = Paths.get(building_id+"_rooms.data");
        Path rpPath = Paths.get(building_id+"_RPs.data");

        ArrayList<String> arffData = new ArrayList<>();
        ArrayList<String> rpids = getRPs(JSONData);
        ArrayList<String> rooms = getRooms(JSONData);

        arffData.add("@relation room"); arffData.add("");
        for(String s:rpids){
            arffData.add("@attribute "+s+" NUMERIC");
        }
        arffData.add("@attribute class {");
        for(int i=0;i<rooms.size()-1;++i){
            arffData.set(arffData.size()-1,arffData.get(arffData.size()-1)+rooms.get(i)+",");
        }
        arffData.set(arffData.size()-1,arffData.get(arffData.size()-1)+rooms.get(rooms.size()-1)+"}");
        arffData.add("@data");
        arffData.addAll(getOrderedReadings(JSONData,rpids));
        try {
            Files.write(arffPath,arffData, Charset.forName("UTF-8"));
            Files.write(roomsPath,rooms, Charset.forName("UTF-8"));
            Files.write(rpPath,rpids, Charset.forName("UTF-8"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void makeClassifierARFF(String building_id,JSONArray JSONData){

        Path tempPath = Paths.get(building_id+"_temp.arff");
        Path roomsPath = Paths.get(building_id+"_rooms.data");
        Path rpPath = Paths.get(building_id+"_RPs.data");

        ArrayList<String> arffData = new ArrayList<>();
        ArrayList<String> rpids = new ArrayList<>();
        ArrayList<String> rooms = new ArrayList<>();

        try {
            BufferedReader br = Files.newBufferedReader(roomsPath,Charset.forName("UTF-8"));
            String line;
            while((line = br.readLine()) != null){
                rooms.add(line);
            }
            br = Files.newBufferedReader(rpPath,Charset.forName("UTF-8"));
            while((line = br.readLine()) != null){
                rpids.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        arffData.add("@relation room"); arffData.add("");
        for(String s:rpids){
            arffData.add("@attribute "+s+" NUMERIC");
        }
        arffData.add("@attribute class {");
        for(int i=0;i<rooms.size()-1;++i){
            arffData.set(arffData.size()-1,arffData.get(arffData.size()-1)+rooms.get(i)+",");
        }
        arffData.set(arffData.size()-1,arffData.get(arffData.size()-1)+rooms.get(rooms.size()-1)+"}");
        arffData.add("@data");
        arffData.addAll(getOrderedReadings(JSONData,rpids));
        try {
            Files.write(tempPath,arffData, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> getRPs(JSONArray JSONdata){
        ArrayList<String> rpids = new ArrayList<>();
        for(Object reading: JSONdata){
            for(Object pair: ((JSONObject)reading).getJSONArray("rpv_pair")){
                rpids.add(((JSONObject) pair).getString("RPID"));
            }
        }
        Set<String> noDuplicates = new LinkedHashSet<String>(rpids);
        rpids.clear();
        rpids.addAll(noDuplicates);
        return rpids;
    }

    public static ArrayList<String> getRooms(JSONArray JSONdata){
        ArrayList<String> rooms = new ArrayList<>();
        for(Object reading: JSONdata){
            rooms.add(((JSONObject) reading).getString("room_id"));
        }
        Set<String> noDuplicates = new LinkedHashSet<String>(rooms);
        rooms.clear();
        rooms.addAll(noDuplicates);
        return rooms;
    }

    public static ArrayList<String> getOrderedReadings(JSONArray jsonData, ArrayList<String> rpids){
        ArrayList<String> toReturn= new ArrayList<>();
        for(Object o:jsonData){
            String curLine = "";
            for(String rp:rpids){
                curLine += Double.toString(getValueByRPID(((JSONObject) o),rp))+",";

            }
            curLine += ((JSONObject) o).getString("room_id");
            toReturn.add(curLine);
        }

        return toReturn;
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
