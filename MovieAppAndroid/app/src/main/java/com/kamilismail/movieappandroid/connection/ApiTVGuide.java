package com.kamilismail.movieappandroid.connection;

import com.kamilismail.movieappandroid.DTO.TVGuideDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiTVGuide {

    @GET("tvguide/")
    Call<ArrayList<TVGuideDTO>> getTVGuide(@Header("Cookie") String cookie);
}
