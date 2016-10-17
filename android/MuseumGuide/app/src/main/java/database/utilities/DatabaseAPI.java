package database.utilities;


import java.util.ArrayList;

import database.models.Museum;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface DatabaseAPI {
    @GET("/museums")
    Call<ArrayList<Museum>> loadMuseums();
    @GET("/museum/{id}")
    Call<Museum> loadMuseumById(@Path("id") int museumId);
}
