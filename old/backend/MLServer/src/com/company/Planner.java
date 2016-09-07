package com.company;

import models.indoormapping.Room;
import tools.Point;
import org.json.JSONArray;
import org.json.JSONObject;
import tools.RectangleDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Planner {
    private static final String[] pddlIntro = {
        "(define (problem simplemuseum)",
        "(:domain museum)",
        "(:objects",
        "    visitor - person"
    };

    public static String route(JSONArray jsonArray, int deadline, String building_id){
        ArrayList<Room> rooms = getRoomsFromJSON(jsonArray);
        makePDDL(rooms,deadline);
        building_id = building_id.replaceAll("[\\s&;]+","");
        ArrayList<String> toExecute = new ArrayList<>();
        toExecute.add("#!/bin/bash\n" +
                "ulimit -t 1\n");
        toExecute.add( "./planner " + "--optimise " + "domain.pddl "+building_id+"_temp.pddl");
        Path execPath = Paths.get("five-seconds");
        try {
            Files.write(execPath,toExecute, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String command = "./five-seconds";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                process.getInputStream()));
        ArrayList<String> output = new ArrayList<>();
        String s;
        try {
            while ((s = reader.readLine()) != null) {
                System.out.println("Script output: " + s);
                output.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return makeFinalResult(parseOutput(output),rooms);

    }
    public static ArrayList<Room> getRoomsFromJSON(JSONArray jsonArray){

        ArrayList<Room> toReturn = new ArrayList<>();
        for(Object o:jsonArray){
            JSONObject room =  (JSONObject) o;
            Point lt = new Point(room.getJSONObject("rectangle").getJSONObject("lt").getDouble("x"),
                    room.getJSONObject("rectangle").getJSONObject("lt").getDouble("y"));
            Point rt = new Point(room.getJSONObject("rectangle").getJSONObject("rt").getDouble("x"),
                    room.getJSONObject("rectangle").getJSONObject("rt").getDouble("y"));
            Point lb = new Point(room.getJSONObject("rectangle").getJSONObject("lb").getDouble("x"),
                    room.getJSONObject("rectangle").getJSONObject("lb").getDouble("y"));
            Point rb = new Point(room.getJSONObject("rectangle").getJSONObject("rb").getDouble("x"),
                    room.getJSONObject("rectangle").getJSONObject("rb").getDouble("y"));
            RectangleDB r = new RectangleDB(lt,rt,lb,rb);
            Room toAdd = new Room(room.getString("_id"),room.getString("building_id"),room.getString("name"),r,
                    room.getDouble("width"),room.getDouble("length"),
                    room.getDouble("est_time"),room.getInt("excitement"));
            toReturn.add(toAdd);
        }
        return toReturn;
    }

    private static void makePDDL(ArrayList<Room> rooms, int deadline){

        Path tempPath = Paths.get(rooms.get(0).getBuilding_id()+"_temp.pddl");
        ArrayList<String> toWrite = new ArrayList<>();
        toWrite.addAll(Arrays.asList(pddlIntro));
        toWrite.add("    ");
        for(Room r:rooms){
            toWrite.set(toWrite.size()-1,toWrite.get(toWrite.size()-1)+"e"+rooms.indexOf(r)+" ");
            System.out.println("ROOM CREATED: "+"\nINDEX: "+rooms.indexOf(r)+" "+r.getRoomName());
        }
        toWrite.set(toWrite.size()-1,toWrite.get(toWrite.size()-1)+"- exhibit");
        toWrite.add(")");
        toWrite.add("(:init");
        for(Room r:rooms){
            toWrite.add("(want-to-see "+"e"+rooms.indexOf(r)+")");
        }

        toWrite.addAll(getBuildingLayout(rooms));
        for(Room r:rooms){
            toWrite.add("(= (time-to-see "+"e"+rooms.indexOf(r)+") "+(int) r.getEst_time()+")");
        }
        toWrite.add("(at visitor "+"e0"+")");
        toWrite.add("(= (seen) 0)");
        for(Room r:rooms){
            toWrite.add("(= (excitement "+"e"+rooms.indexOf(r)+") "+ r.getExcitement()+(")"));
        }
        toWrite.add("(open)");
        toWrite.add("(at "+deadline+" (not (open)))");
        toWrite.add(")");
        toWrite.add("(:goal (and");

        for(Room r:rooms){
            toWrite.add(";(visited "+"e"+rooms.indexOf(r)+")");
        }
        toWrite.add(")");
        toWrite.add(")");
        toWrite.add("(:metric maximize (seen))");
        toWrite.add(")");
        try {
            Files.write(tempPath,toWrite, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> getBuildingLayout(ArrayList<Room> rooms){
        ArrayList<String> toReturn = new ArrayList<>();
        for(int i = 0; i<rooms.size()-1; ++i){
            for(int j=i+1; j<rooms.size();++j){
                if(rooms.get(i).getRectangleDB().isNeighbour(rooms.get(j).getRectangleDB())){
                    toReturn.add("(path " + "e"+i + " "+"e"+ j + ") (path " +"e"+j+" "+"e"+i+")");
                    toReturn.add("(= (time-to-walk "+"e"+i+" "+"e"+j+") 0)");
                    toReturn.add("(= (time-to-walk "+"e"+j+" "+"e"+i+") 0)");
                }
            }
        }
        return toReturn;
    }

    private static ArrayList<String> parseOutput(ArrayList<String> output){
        ArrayList<String> toReturn = new ArrayList<>();
        ArrayList<String> currentResult = new ArrayList<>();

        for(String s:output){
            if(s.equals(";;;; Solution Found")) {
                toReturn = currentResult;
                currentResult.clear();
            }
            if(!((s.startsWith(";")|| s.isEmpty()))) {
                if (s.contains("(") && s.contains(")")) {
                    currentResult.add( s.substring(s.indexOf("(")+1, s.indexOf(")")));
                }
            }
            if(s.startsWith("..")){
                toReturn = currentResult;
            }
        }

       return toReturn;
    }

    private static String makeFinalResult(ArrayList<String> output, ArrayList<Room> rooms){
        String toReturn = "";
        for(String s:output){
            String[] tempSplit = s.split(" ");
            if(tempSplit[0].equals("view")){
                int roomIndex =Integer.parseInt(tempSplit[2].substring(tempSplit[2].indexOf("e")+1));
                toReturn += "view:"+rooms.get(roomIndex).getId()+";";

            } else if(tempSplit[0].equals("walk")){
                int roomIndexOr = Integer.parseInt(tempSplit[2].substring(tempSplit[2].indexOf("e")+1));
                int roomIndexDest = Integer.parseInt(tempSplit[3].substring(tempSplit[3].indexOf("e")+1));

                toReturn += "walk:"+rooms.get(roomIndexOr).getId()+","+rooms.get(roomIndexDest).getId()+";";
            }
        }

        return toReturn.substring(0,toReturn.length()-1);
    }
}
