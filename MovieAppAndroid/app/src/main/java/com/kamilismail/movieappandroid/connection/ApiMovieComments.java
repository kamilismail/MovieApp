package com.kamilismail.movieappandroid.connection;

import com.kamilismail.movieappandroid.DTO.BooleanDTO;
import com.kamilismail.movieappandroid.DTO.search_movies.MovieCommentsDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiMovieComments {

    @GET("moviecomments")
    Call<ArrayList<MovieCommentsDTO>> getComments (@Header("Cookie") String cookie, @Query("movie_id") String movieID);

    @POST("moviecomments")
    Call<BooleanDTO> addComment(@Header("Cookie") String cookie, @Query("movie_id") String movieID, @Query("comment") String comment);

    @DELETE("moviecomments")
    Call<BooleanDTO> deleteComment (@Header("Cookie") String cookie, @Query("movie_id") String movieID);
}
