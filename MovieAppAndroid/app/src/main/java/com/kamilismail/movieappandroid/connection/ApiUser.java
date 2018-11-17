package com.kamilismail.movieappandroid.connection;

import com.google.gson.JsonObject;
import com.kamilismail.movieappandroid.DTO.BooleanDTO;
import com.kamilismail.movieappandroid.DTO.UserDTO;
import com.kamilismail.movieappandroid.DTO.UserPhotoDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiUser {

    @GET("user/getUsername")
    Call<UserDTO> getUser(@Header("Authorization") String credentials);

    @POST("user/")
    Call<BooleanDTO> createNewUser(@Body JsonObject bean);

    @DELETE("user/")
    Call<BooleanDTO> deleteUser(@Header("Cookie") String cookie, @Body JsonObject bean);

    @DELETE("user/facebook")
    Call<BooleanDTO> deleteFacebookUser(@Header("Cookie") String cookie, @Body JsonObject bean);

    @PUT("user/")
    Call<BooleanDTO> changePassword(@Header("Cookie") String cookie, @Body JsonObject bean);

    @POST("user/setFirebaseID")
    Call<BooleanDTO> setFirebaseID(@Header("Cookie") String cookie, @Query("firebaseID") String firebaseID);

    @POST("user/facebookLogin")
    Call<UserDTO> facebookUserLogin(@Body JsonObject bean);

    @GET("user/facebookPhoto")
    Call<UserPhotoDTO> facebookPhoto(@Header("Cookie") String cookie);

    @POST("user/sendPhotoName")
    Call<BooleanDTO> sendPhoto(@Header("Cookie") String cookie, @Query("photoName") String photoName);
}
