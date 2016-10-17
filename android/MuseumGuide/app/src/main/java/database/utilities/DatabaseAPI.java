package database.utilities;


import java.util.ArrayList;

import database.models.Museum;
import retrofit.Call;
import retrofit.http.GET;

public interface DatabaseAPI {
    @GET("/museums")
    Call<ArrayList<Museum>> loadMuseums();
}
