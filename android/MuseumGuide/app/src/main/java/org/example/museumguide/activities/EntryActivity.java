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
import database.utilities.DatabaseAPI;
import exceptions.IndexOutOfBoundsException;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class EntryActivity extends AppCompatActivity implements Callback<Museum> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
    }

    public void getMuseums(View v){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://138.68.148.113:34343")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DatabaseAPI dataBaseApi = retrofit.create(DatabaseAPI.class);
        Call<Museum> call = dataBaseApi.loadMuseumById(3);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<Museum> response, Retrofit retrofit) {

            Museum m = response.body();
        String toPrint = null;
        try {
            toPrint = m.getId()+" "+m.getName()+" "+m.getAddress()+" "+m.getDescription() +
                    " " + m.getWebsite() + " " + m.getBuildings()[0].getName() + " "+
                    m.getBuildings()[0].getGeoLocation().getPoint(3).getY();
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
}
