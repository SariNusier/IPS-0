package com.company;

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
    boolean nbinit;
    public Server()
    {
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
                    globalNB = Learner.learnFromJSON_NB(recJSON.getString("building_id"),recJSON.getJSONArray("learning_set"));
                    globalBN = Learner.learnFromJSON_BN(recJSON.getString("building_id"),recJSON.getJSONArray("learning_set"));
                    nbinit = true;
                    currentBID = recJSON.getString("building_id");
                    pw.write("Done!");
                } else if(recJSON.getString("command").equals("classify")){
                    if(nbinit && recJSON.getString("building_id").equals(currentBID)){
                        String res ="Naive:"+Learner.classify_NB(recJSON.getString("building_id"),recJSON.getJSONArray("learning_set"),globalNB);
                        res +=",BN:"+Learner.classify_BN(recJSON.getString("building_id"),recJSON.getJSONArray("learning_set"),globalBN);
                        pw.write(res);
                        System.out.println(res);
                    } else if(Files.exists(Paths.get(recJSON.getString("building_id")+".arff"))){

                    } else {
                        pw.write("Weird classify request!");
                    }
                }
                pw.flush();
            } catch(IOException exception) {
                System.out.println("Error: " + exception);
            }
        }
    }
}