package com.kamilismail.movieappandroid.connection;

import com.kamilismail.movieappandroid.DTO.tvGuide.TVGuideDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiTVGuide {
    //serverName
    String BASE_URL = "http://192.168.0.16:8080/";

    @GET("tvguide/")
    Call<ArrayList<TVGuideDTO>> getTVGuide(@Header("Cookie") String cookie);
}
