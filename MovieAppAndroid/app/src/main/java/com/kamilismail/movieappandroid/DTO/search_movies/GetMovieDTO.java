package com.kamilismail.movieappandroid.DTO.search_movies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMovieDTO {

    @SerializedName("result")
    @Expose
    private Result results = null;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("hour")
    @Expose
    private String hour;
    @SerializedName("chanel")
    @Expose
    private String chanel;
    @SerializedName("film_id")
    @Expose
    private String filmID;
    @SerializedName("user_rating")
    @Expose
    private String userRating;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetMovieDTO() {
    }

    public GetMovieDTO(Result results, String date, String hour, String chanel, String filmID, String userRating) {
        this.results = results;
        this.date = date;
        this.hour = hour;
        this.chanel = chanel;
        this.filmID = filmID;
        this.userRating = userRating;
    }

    public Result getResults() {
        return results;
    }

    public void setResults(Result results) {
        this.results = results;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }

    public String getFilmID() {
        return filmID;
    }

    public void setFilmID(String filmID) {
        this.filmID = filmID;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }
}
