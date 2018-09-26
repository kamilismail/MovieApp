package com.kamilismail.movieappandroid.connection;

import com.kamilismail.movieappandroid.DTO.UserDTO;
import com.kamilismail.movieappandroid.R;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Api {

    //serverName
    String BASE_URL = "http://192.168.0.16:8080/";

    @GET("user/getUsername")
    Call<UserDTO> getUser(@Header("Authorization") String credentials);
}
