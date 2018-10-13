package com.kamilismail.movieappandroid.connection;

import com.kamilismail.movieappandroid.DTO.BooleanDTO;
import com.kamilismail.movieappandroid.DTO.FavouritesDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRatings {

    //serverName
    String BASE_URL = "http://192.168.0.16:8080/";

    @GET("rating/")
    Call<ArrayList<FavouritesDTO>> getRatings(@Header("Cookie") String cookie);

    @POST("favourite/addFavourite/")
    Call<BooleanDTO> setRating(@Header("Cookie") String cookie, @Query("movieID") String movieID,
                               @Query("rating") String rating);
}
