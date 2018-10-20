package com.kamilismail.movieappandroid.connection;

import com.kamilismail.movieappandroid.DTO.DiscoverDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiDiscover {

    @GET("discover/")
    Call<DiscoverDTO> getDiscovery(@Header("Cookie") String cookie);
}
