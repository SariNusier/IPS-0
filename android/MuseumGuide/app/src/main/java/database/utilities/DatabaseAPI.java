package database.utilities;


import java.util.ArrayList;

import database.models.Building;
import database.models.Museum;
import database.models.Room;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface DatabaseAPI {
    @GET("/museums")
    Call<ArrayList<Museum>> loadMuseums();
    @GET("/museum/{id}")
    Call<Museum> loadMuseumById(@Path("id") int museumId);
    @GET("/buildings")
    Call<ArrayList<Building>> loadBuildings();
    @GET("/building/{id}")
    Call<Building> loadBuildingById(@Path("id") int buildingId);
    @GET("/rooms")
    Call<ArrayList<Room>> loadRooms();
    @GET("/rooms/{id}")
    Call<Room> loadRoomById(@Path("id") int roomId);
}
