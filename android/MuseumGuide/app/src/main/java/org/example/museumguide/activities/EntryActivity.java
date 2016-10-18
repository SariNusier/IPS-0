package org.example.museumguide.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.example.museumguide.R;

import java.util.ArrayList;

import database.models.Building;
import database.models.Museum;
import database.models.Room;
import database.utilities.DatabaseAPI;
import exceptions.IndexOutOfBoundsException;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
    }

    public void getMuseumById(View v){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://138.68.148.113:34343")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DatabaseAPI dataBaseApi = retrofit.create(DatabaseAPI.class);
        Call<Museum> call = dataBaseApi.loadMuseumById(3);
        call.enqueue(new Callback<Museum>() {
            @Override
            public void onResponse(Response<Museum> response, Retrofit retrofit) {
                Museum museum = response.body();
                String toPrint = null;
                try {
                    toPrint = museum.getId() + " " + museum.getName() + " " +
                            museum.getAddress() + " " + museum.getDescription() + " " +
                            museum.getWebsite() + " " + museum.getBuildings()[0].getName() + " " +
                            museum.getBuildings()[0].getGeoLocation().getPoint(3).getY();
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                Log.d("RESPONSE", toPrint);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(EntryActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR:", t.getMessage());
            }
        });
    }

    public void getMuseums(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://138.68.148.113:34343")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DatabaseAPI dataBaseApi = retrofit.create(DatabaseAPI.class);
        Call<ArrayList<Museum>> call = dataBaseApi.loadMuseums();
        call.enqueue(new Callback<ArrayList<Museum>>() {
            @Override
            public void onResponse(Response<ArrayList<Museum>> response, Retrofit retrofit) {
                ArrayList<Museum> museumList = response.body();
                for (Museum m : museumList) {
                    String toPrint = null;
                    try {
                        toPrint = m.getId() + " " + m.getName() + " " +
                                m.getAddress() + " " + m.getDescription() + " " +
                                m.getWebsite() + " " + m.getBuildings()[0].getName() + " " +
                                m.getBuildings()[0].getGeoLocation().getPoint(3).getY();
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    Log.d("RESPONSE", toPrint);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(EntryActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR:", t.getMessage());
            }
        });
    }

    public void getBuildings(View v){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://138.68.148.113:34343")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DatabaseAPI dataBaseApi = retrofit.create(DatabaseAPI.class);
        Call<ArrayList<Building>> call = dataBaseApi.loadBuildings();
        call.enqueue(new Callback<ArrayList<Building>>() {
            @Override
            public void onResponse(Response<ArrayList<Building>> response, Retrofit retrofit) {
                ArrayList<Building> buildingList = response.body();
                String toPrint;
                for (Building b : buildingList) {
                    toPrint = b.getId() + " " + b.getName();
                    Log.d("RESPONSE", toPrint);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(EntryActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR:", t.getMessage());
            }
        });
    }

    public void getBuildingById(View v){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://138.68.148.113:34343")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DatabaseAPI dataBaseApi = retrofit.create(DatabaseAPI.class);
        Call<Building> call = dataBaseApi.loadBuildingById(3);
        call.enqueue(new Callback<Building>() {
            @Override
            public void onResponse(Response<Building> response, Retrofit retrofit) {
                Building building = response.body();
                String toPrint = building.getId() + " " + building.getName();
                Log.d("RESPONSE", toPrint);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(EntryActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR:", t.getMessage());
            }
        });
    }

    public void getRooms(View v){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://138.68.148.113:34343")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DatabaseAPI dataBaseApi = retrofit.create(DatabaseAPI.class);
        Call<ArrayList<Room>> call = dataBaseApi.loadRooms();
        call.enqueue(new Callback<ArrayList<Room>>() {
            @Override
            public void onResponse(Response<ArrayList<Room>> response, Retrofit retrofit) {
                ArrayList<Room> roomList = response.body();
                String toPrint;
                for (Room r : roomList) {
                    toPrint = r.getId() + " " + r.getName();
                    Log.d("RESPONSE", toPrint);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(EntryActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR:", t.getMessage());
            }
        });
    }

    public void getRoomById(View v){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://138.68.148.113:34343")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DatabaseAPI dataBaseApi = retrofit.create(DatabaseAPI.class);
        Call<Room> call = dataBaseApi.loadRoomById(3);
        call.enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Response<Room> response, Retrofit retrofit) {
                Room room = response.body();
                String toPrint = room.getId() + " " + room.getName();
                Log.d("RESPONSE", toPrint);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(EntryActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR:", t.getMessage());
            }
        });
    }
}
