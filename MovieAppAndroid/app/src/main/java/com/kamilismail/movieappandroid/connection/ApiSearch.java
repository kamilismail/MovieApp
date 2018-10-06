package com.kamilismail.movieappandroid.connection;

import com.kamilismail.movieappandroid.DTO.search_movies.SearchMovieDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiSearch {
    //serverName
    String BASE_URL = "http://192.168.0.16:8080/";

    @GET("search/movies")
    Call<SearchMovieDTO> getMovies(@Header("Cookie") String cookie, @Query("name") String name);

    @GET("search/tvshows")
    Call<SearchMovieDTO> getSeries(@Header("Cookie") String cookie, @Query("name") String name);

    @GET("search/productions")
    Call<SearchMovieDTO> getProductions(@Header("Cookie") String cookie, @Query("name") String name);
}
