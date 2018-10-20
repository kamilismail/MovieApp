package com.kamilismail.movieappandroid.connection;

import com.kamilismail.movieappandroid.DTO.BooleanDTO;
import com.kamilismail.movieappandroid.DTO.RemindersDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiReminders {

    @GET("reminder/getReminders/")
    Call<ArrayList<RemindersDTO>> getReminders(@Header("Cookie") String cookie);

    @POST("reminder/addReminder/")
    Call<BooleanDTO> addReminder(@Header("Cookie") String cookie, @Query("movieID") String movieID);

    @DELETE("reminder/deleteReminder/")
    Call<BooleanDTO> deleteReminder(@Header("Cookie") String cookie, @Query("movieID") String movieID);
}
