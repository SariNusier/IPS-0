package com.company;

import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;

import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Server
{
    NaiveBayes globalNB;
    BayesNet globalBN;
    String currentBID;
    ArrayList<Pair<String,BayesNet>> classifiersBN;
    ArrayList<Pair<String,NaiveBayes>> classifiersNB;
    boolean nbinit;
    public Server()
    {
        classifiersBN = new ArrayList<>();
        classifiersNB = new ArrayList<>();

        try {
            ServerSocket sSocket = new ServerSocket(5000);
            System.out.println("Server started!");
            globalNB = new NaiveBayes();
            nbinit = false;
            currentBID = "";
            //Loop that runs server functions
            while(true) {
                //Wait for a client to connect
                Socket socket = sSocket.accept();
                //Create a new custom thread to handle the connection
                ClientThread cT = new ClientThread(socket);

                //Start the thread!
                new Thread(cT).start();

            }
        } catch(IOException exception) {
            System.out.println("Error: " + exception);
        }
    }

    //Here we create the ClientThread inner class and have it implement Runnable
    //This means that it can be used as a thread
    class ClientThread implements Runnable
    {
        Socket socket;
        public ClientThread(Socket socket)
        {
            this.socket = socket;
        }

        public void run()
        {
            try {
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String rec = br.readLine();
                while (rec == null) {
                    rec = br.readLine();
                }
                System.out.println(rec);
                JSONObject recJSON = new JSONObject(rec);
                if(recJSON.getString("command").equals("learn")){
                    //globalNB = Learner.learnFromJSON_NB(recJSON.getString("building_id"),recJSON.getJSONArray("learning_set"));
                    //globalBN = Learner.learnFromJSON_BN(recJSON.getString("building_id"),recJSON.getJSONArray("learning_set"));
                    BayesNet bn = Learner.learnFromJSON_BN(recJSON.getString("building_id"),recJSON.getJSONArray("learning_set"));
                    NaiveBayes nb = Learner.learnFromJSON_NB(recJSON.getString("building_id"),recJSON.getJSONArray("learning_set"));
                    int i = buildingClassifierBNInitialised(recJSON.getString("building_id"));
                    int j = buildingClassifierNBInitialised(recJSON.getString("building_id"));
                    if(i>-1){
                        classifiersBN.set(i,Pair.of(recJSON.getString("building_id"),bn));
                    } else {
                        classifiersBN.add(Pair.of(recJSON.getString("building_id"),bn));
                    }

                    if(i>-1){
                        classifiersNB.set(i,Pair.of(recJSON.getString("building_id"),nb));
                    } else {
                        classifiersNB.add(Pair.of(recJSON.getString("building_id"),nb));
                    }
                    pw.write("Done!");
                } else if(recJSON.getString("command").equals("classify")){
                    int i = buildingClassifierBNInitialised(recJSON.getString("building_id"));
                    int j = buildingClassifierNBInitialised(recJSON.getString("building_id"));

                    if(i>-1){
                        String res ="BN:"+Learner.classify_BN(recJSON.getString("building_id"),
                                recJSON.getJSONArray("learning_set"),classifiersBN.get(i).getRight());
                        res += ",NB:"+Learner.classify_NB(recJSON.getString("building_id"),
                                recJSON.getJSONArray("learning_set"),classifiersNB.get(j).getRight());
                        pw.write(res);
                        System.out.println(res);
                    } else if(Files.exists(Paths.get(recJSON.getString("building_id")+".arff"))){
                        BayesNet bn = Learner.getClassifierBN(recJSON.getString("building_id"));
                        NaiveBayes nb = Learner.getClassifierNB(recJSON.getString("building_id"));
                        classifiersBN.add(Pair.of(recJSON.getString("building_id"),bn));
                        classifiersNB.add(Pair.of(recJSON.getString("building_id"),nb));

                        i = buildingClassifierBNInitialised(recJSON.getString("building_id"));
                        j = buildingClassifierNBInitialised(recJSON.getString("building_id"));
                        String res ="BN:"+Learner.classify_BN(recJSON.getString("building_id"),
                                recJSON.getJSONArray("learning_set"),classifiersBN.get(i).getRight());
                        res += ",NB:"+Learner.classify_NB(recJSON.getString("building_id"),
                                recJSON.getJSONArray("learning_set"),classifiersNB.get(j).getRight());
                        pw.write(res);
                    } else {
                        pw.write("Weird classify request!");
                    }
                }  else if(recJSON.getString("command").equals("route")){
                    pw.write(Planner.route(recJSON.getJSONArray("request_set"),recJSON.getInt("deadline"),
                            recJSON.getString("building_id")));
                }
                pw.flush();
            } catch(IOException exception) {
                System.out.println("Error: " + exception);
            }
        }
    }

    public int buildingClassifierBNInitialised(String building_id){
        for(Pair<String,BayesNet> p:classifiersBN){
            if(p.getLeft().equals(building_id))
                return classifiersBN.indexOf(p);
        }
        return -1;
    }

    public int buildingClassifierNBInitialised(String building_id){
        for(Pair<String,NaiveBayes> p:classifiersNB){
            if(p.getLeft().equals(building_id))
                return classifiersNB.indexOf(p);
        }
        return -1;
    }
}