package com.kamilismail.movieappandroid.connection;

import com.kamilismail.movieappandroid.DTO.BooleanDTO;
import com.kamilismail.movieappandroid.DTO.FavouritesDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiWantToWatch {

    @GET("want/getWants/")
    Call<ArrayList<FavouritesDTO>> getWants(@Header("Cookie") String cookie);

    @POST("want/addWant/")
    Call<BooleanDTO> addWant(@Header("Cookie") String cookie, @Query("movieID") String movieID);

    @DELETE("want/deleteWant/")
    Call<BooleanDTO> deleteWant(@Header("Cookie") String cookie, @Query("movieID") String movieID);
}
