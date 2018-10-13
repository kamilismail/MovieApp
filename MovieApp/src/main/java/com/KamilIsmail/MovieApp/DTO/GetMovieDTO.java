package com.KamilIsmail.MovieApp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.movito.themoviedbapi.model.MovieDb;

public class GetMovieDTO {

    private MovieDb result;

    @JsonProperty("date")
    private String date;

    @JsonProperty("hour")
    private String hour;

    @JsonProperty("chanel")
    private String chanel;

    @JsonProperty("film_id")
    private String filmID;

    @JsonProperty("user_rating")
    private String userRating;

    @JsonProperty("user_want_to_watch")
    private String wantToWatch;

    @JsonProperty("user_fav")
    private String fav;

    @JsonProperty("user_reminder")
    private String reminder;

    public GetMovieDTO(MovieDb result, String date, String hour, String chanel, String filmID, String userRating,
                       boolean wantToWatch, boolean userFav, boolean reminder) {
        this.result = result;
        this.date = date;
        this.hour = hour;
        this.chanel = chanel;
        this.filmID = filmID;
        this.userRating = userRating;
        this.wantToWatch = String.valueOf(wantToWatch);
        this.fav = String.valueOf(userFav);
        this.reminder = String.valueOf(reminder);
    }

    public GetMovieDTO(MovieDb result, String userRating) {
        this.result = result;
        this.date = "";
        this.hour = "";
        this.chanel = "";
        this.filmID = "";
        this.userRating = userRating;
    }

    public GetMovieDTO(MovieDb result, String userRating, boolean wantToWatch, boolean userFav, boolean reminder) {
        this.result = result;
        this.date = "";
        this.hour = "";
        this.chanel = "";
        this.filmID = "";
        this.userRating = userRating;
        this.wantToWatch = String.valueOf(wantToWatch);
        this.fav = String.valueOf(userFav);
        this.reminder = String.valueOf(reminder);
    }

    public MovieDb getResult() {
        return result;
    }

    public void setResult(MovieDb result) {
        this.result = result;
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

    public String getWantToWatch() {
        return wantToWatch;
    }

    public void setWantToWatch(String wantToWatch) {
        this.wantToWatch = wantToWatch;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }
}
