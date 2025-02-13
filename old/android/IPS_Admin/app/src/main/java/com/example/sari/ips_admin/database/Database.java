package com.example.sari.ips_admin.database;


import android.util.Log;
import com.example.sari.ips_admin.models.indoormapping.Building;
import com.example.sari.ips_admin.models.indoormapping.Floor;
import com.example.sari.ips_admin.models.indoormapping.Room;
import com.example.sari.ips_admin.models.positioning.RPMeasurement;
import com.example.sari.ips_admin.tools.Point;
import com.example.sari.ips_admin.tools.RectangleDB;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Class that connects to the api.
 */
public class Database {

    public final static String API_URL = "http://178.62.127.39:3000/";
    public static Building[] getBuildings(){
        String StringData = getData("buildings");
        ArrayList<Building> toReturn = new ArrayList<>();
        Log.d("BUILDINGS: ", StringData);
        try {
            JSONArray JSONData = new JSONArray(StringData);
            for(int i = 0; i<JSONData.length();++i){
                JSONObject building = JSONData.getJSONObject(i);
                Point lt = new Point(building.getJSONObject("rectangle").getJSONObject("lt").getDouble("x"),
                                     building.getJSONObject("rectangle").getJSONObject("lt").getDouble("y"));
                Point rt = new Point(building.getJSONObject("rectangle").getJSONObject("rt").getDouble("x"),
                                     building.getJSONObject("rectangle").getJSONObject("rt").getDouble("y"));
                Point lb = new Point(building.getJSONObject("rectangle").getJSONObject("lb").getDouble("x"),
                                     building.getJSONObject("rectangle").getJSONObject("lb").getDouble("y"));
                Point rb = new Point(building.getJSONObject("rectangle").getJSONObject("rb").getDouble("x"),
                                     building.getJSONObject("rectangle").getJSONObject("rb").getDouble("y"));
                RectangleDB r = new RectangleDB(lt,rt,lb,rb);
                Building toAdd = new Building(building.getString("_id"),r,building.getString("name"),
                        building.getDouble("width"),building.getDouble("length"),
                        getRooms(building.getString("_id")));
                toReturn.add(toAdd);
                getRooms(building.getString("_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toReturn.toArray(new Building[toReturn.size()]);
    }

    public static boolean addBuilding(Building building){
        JSONObject buildingToAdd = new JSONObject();
        try {
            buildingToAdd.put("name",building.getName());
            buildingToAdd.put("width", building.getWidth());
            buildingToAdd.put("length",building.getLength());
            JSONObject rectangle = new JSONObject();
            rectangle.put("lt", new JSONObject().put("x", building.getRectangle().getLt().getX())
                                                .put("y", building.getRectangle().getLt().getY()))
                     .put("rt", new JSONObject().put("x", building.getRectangle().getRt().getX())
                                                .put("y", building.getRectangle().getRt().getY()))
                     .put("lb", new JSONObject().put("x", building.getRectangle().getLb().getX())
                                                .put("y", building.getRectangle().getLb().getY()))
                     .put("rb", new JSONObject().put("x", building.getRectangle().getRb().getX())
                                                .put("y", building.getRectangle().getRb().getY()));

            buildingToAdd.put("rectangle", rectangle);
            postData("buildings", buildingToAdd.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean postMeasurement(RPMeasurement measurement){

        JSONArray JSONAllM= new JSONArray();
        JSONObject toSend = new JSONObject();
        try {
            for(int i = 0; i<measurement.getReadings().size(); ++i){
                JSONAllM.put(new JSONObject().put("RPID",measurement.getReadings().get(i).first)
                        .put("value",measurement.getReadings().get(i).second));
            }

            toSend.put("rpv_pair", JSONAllM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        postData("measurements", toSend.toString(), measurement.getRoom_id());

        return true;
    }

    public static Building getBuilding(String id){
        String StringData = getData("buildings",id);
        Building toReturn = null;
        try {
            JSONObject building = new JSONObject(StringData);
            Point lt = new Point(building.getJSONObject("rectangle").getJSONObject("lt").getDouble("x"),
                                 building.getJSONObject("rectangle").getJSONObject("lt").getDouble("y"));
            Point rt = new Point(building.getJSONObject("rectangle").getJSONObject("rt").getDouble("x"),
                        building.getJSONObject("rectangle").getJSONObject("rt").getDouble("y"));
            Point lb = new Point(building.getJSONObject("rectangle").getJSONObject("lb").getDouble("x"),
                        building.getJSONObject("rectangle").getJSONObject("lb").getDouble("y"));
            Point rb = new Point(building.getJSONObject("rectangle").getJSONObject("rb").getDouble("x"),
                       building.getJSONObject("rectangle").getJSONObject("rb").getDouble("y"));
            RectangleDB r = new RectangleDB(lt,rt,lb,rb);
            toReturn = new Building(building.getString("_id"),r,building.getString("name"),
                        building.getDouble("width"),building.getDouble("length"),
                        getRooms(building.getString("_id")));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public static Floor[] getFloors(String building_id){
        return null;
    }

    public static Room[] getRooms(String building_id){
        String StringData = getData("rooms", building_id);
        Log.d("Rooms",StringData);
        ArrayList<Room> toReturn = new ArrayList<>();
        try {
            JSONArray JSONData = new JSONArray(StringData);
            for(int i = 0; i<JSONData.length();++i){
                JSONObject room = JSONData.getJSONObject(i);
                Point lt = new Point(room.getJSONObject("rectangle").getJSONObject("lt").getDouble("x"),
                                     room.getJSONObject("rectangle").getJSONObject("lt").getDouble("y"));
                Point rt = new Point(room.getJSONObject("rectangle").getJSONObject("rt").getDouble("x"),
                                     room.getJSONObject("rectangle").getJSONObject("rt").getDouble("y"));
                Point lb = new Point(room.getJSONObject("rectangle").getJSONObject("lb").getDouble("x"),
                                     room.getJSONObject("rectangle").getJSONObject("lb").getDouble("y"));
                Point rb = new Point(room.getJSONObject("rectangle").getJSONObject("rb").getDouble("x"),
                                     room.getJSONObject("rectangle").getJSONObject("rb").getDouble("y"));
                RectangleDB r = new RectangleDB(lt,rt,lb,rb);
                Room toAdd = new Room(room.getString("_id"),building_id,
                        room.getString("name"),r,room.getDouble("width"),
                        room.getDouble("length"),room.getDouble("est_time"));
                toReturn.add(toAdd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toReturn.toArray(new Room[toReturn.size()]);
    }

    public static boolean addRoom(String building_id, Room room){
        JSONObject roomToAdd = new JSONObject();
        try {
            roomToAdd.put("name", room.getRoomName());
            roomToAdd.put("width", room.getWidth());
            roomToAdd.put("length", room.getLength());
            JSONObject rectangle = new JSONObject();
            rectangle.put("lt", new JSONObject().put("x", room.getRectangleDB().getLt().getX())
                    .put("y", room.getRectangleDB().getLt().getY()))
                    .put("rt", new JSONObject().put("x", room.getRectangleDB().getRt().getX())
                            .put("y", room.getRectangleDB().getRt().getY()))
                    .put("lb", new JSONObject().put("x", room.getRectangleDB().getLb().getX())
                            .put("y", room.getRectangleDB().getLb().getY()))
                    .put("rb", new JSONObject().put("x", room.getRectangleDB().getRb().getX())
                            .put("y", room.getRectangleDB().getRb().getY()));

            roomToAdd.put("rectangle", rectangle);
            postData("rooms", roomToAdd.toString(), building_id);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    public static String getData(String... params) {
        String data = "";
        String building_id = "";
        String request = params[0];
        InputStream inputStream;

        if (params.length >= 2){
            building_id = params[1];
        }

        try {
            URL url = new URL(API_URL+request+"/"+building_id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(connection.getInputStream());
            data = IOUtils.toString(inputStream,"UTF-8");
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;

    }

    public static String postData(String... params){
        String id = "";
        String request = params[0];
        String data = params[1];
        InputStream is;
        String dataRec = "";
        if (params.length > 2){
            id = params[2];
        }

        try {
            URL url = new URL(API_URL+request+"/"+id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            Log.d("Error", "Ex " + url.toString());
            connection.setDoOutput(true);
            connection.setChunkedStreamingMode(0);
            connection.setRequestProperty("Content-Type", "application/json");
            OutputStream os = new BufferedOutputStream(connection.getOutputStream());
            os.write(data.getBytes());
            os.flush();
            is = new BufferedInputStream(connection.getInputStream());
            dataRec = IOUtils.toString(is,"UTF-8");
            Log.d("Found:", "Room:"+dataRec);
            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("HTTP code : "
                        + connection.getResponseCode());
            }
            os.close();
            connection.disconnect();
        } catch (Exception e) {
            Log.d("Error","Ex "+e);
            e.printStackTrace();
        }

        return dataRec;
    }

    public static void deleteData(String... params){
        String building_id = "";
        String request = params[0];


        if (params.length == 2){
            building_id = params[1];
        }

        try {
            URL url = new URL(API_URL+request+"/"+building_id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("DELETE");
            connection.getContent();
            connection.disconnect();
        } catch (Exception e) {
            Log.d("Error","Ex "+e);
            e.printStackTrace();
        }
    }

    public static String classify(RPMeasurement measurement, String building_id){
        JSONArray JSONAllM= new JSONArray();
        JSONObject toSend = new JSONObject();
        JSONArray toSendArray = new JSONArray();
        try {
            for(int i = 0; i<measurement.getReadings().size(); ++i){
                JSONAllM.put(new JSONObject().put("RPID",measurement.getReadings().get(i).first)
                        .put("value",measurement.getReadings().get(i).second));
            }

            toSend.put("rpv_pair", JSONAllM);
            toSend.put("room_id","?");
            toSendArray.put(toSend);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postData("locate", building_id, toSend.toString());
    }

}
