package com.kamilismail.movieappandroid.connection;

import com.google.gson.JsonObject;
import com.kamilismail.movieappandroid.DTO.BooleanDTO;
import com.kamilismail.movieappandroid.DTO.UserDTO;
import com.kamilismail.movieappandroid.R;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiUser {

    @GET("user/getUsername")
    Call<UserDTO> getUser(@Header("Authorization") String credentials);

    @POST("user/")
    Call<BooleanDTO> createNewUser(@Body JsonObject bean);

    @DELETE("user/")
    Call<BooleanDTO> deleteUser(@Header("Cookie") String cookie, @Body JsonObject bean);

    @POST("user/setFirebaseID")
    Call<BooleanDTO> setFirebaseID(@Header("Cookie") String cookie, @Query("firebaseID") String firebaseID);
}
