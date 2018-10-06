package com.kamilismail.movieappandroid.connection;

import com.kamilismail.movieappandroid.DTO.search_movies.Result;
import com.kamilismail.movieappandroid.DTO.search_movies.SearchMovieDTO;
import com.kamilismail.movieappandroid.DTO.search_person.PersonResult;
import com.kamilismail.movieappandroid.DTO.search_series.SeriesResult;

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

    @GET("search/movie")
    Call<Result> getMovie(@Header("Cookie") String cookie, @Query("id") String id);

    @GET("search/tvshow")
    Call<SeriesResult> getTVShow(@Header("Cookie") String cookie, @Query("id") String id);

    @GET("search/person")
    Call<PersonResult> getPerson(@Header("Cookie") String cookie, @Query("id") String id);
}
