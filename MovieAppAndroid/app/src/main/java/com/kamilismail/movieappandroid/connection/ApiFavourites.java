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

public interface ApiFavourites {

    @GET("favourite/getFavourites/")
    Call<ArrayList<FavouritesDTO>> getFavoutites(@Header("Cookie") String cookie);

    @POST("favourite/addFavourite/")
    Call<BooleanDTO> addFavourite(@Header("Cookie") String cookie, @Query("movieID") String movieID);

    @DELETE("favourite/deleteFavourite/")
    Call<BooleanDTO> deleteFavourite(@Header("Cookie") String cookie, @Query("movieID") String movieID);
}
