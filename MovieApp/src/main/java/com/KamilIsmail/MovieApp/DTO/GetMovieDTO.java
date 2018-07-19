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

    public GetMovieDTO(MovieDb result, String date, String hour, String chanel, String filmID) {
        this.result = result;
        this.date = date;
        this.hour = hour;
        this.chanel = chanel;
        this.filmID = filmID;
    }

    public GetMovieDTO(MovieDb result) {
        this.result = result;
        this.date = "";
        this.hour = "";
        this.chanel = "";
        this.filmID = "";
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
}
