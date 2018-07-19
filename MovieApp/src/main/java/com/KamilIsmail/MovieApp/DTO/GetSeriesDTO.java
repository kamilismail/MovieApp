package com.KamilIsmail.MovieApp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.movito.themoviedbapi.model.tv.TvSeries;

public class GetSeriesDTO {

    private TvSeries result;

    @JsonProperty("date")
    private String date;

    @JsonProperty("hour")
    private String hour;

    @JsonProperty("chanel")
    private String chanel;

    @JsonProperty("episode")
    private String episode;

    @JsonProperty("film_id")
    private String filmID;

    //@JsonProperty("chanel_pic")
    //private String chanelPic;

    public GetSeriesDTO(TvSeries result, String date, String hour, String chanel, String episode, String filmID) {
        this.result = result;
        this.date = date;
        this.hour = hour;
        this.chanel = chanel;
        this.episode = episode;
        this.filmID = filmID;
    }

    public GetSeriesDTO(TvSeries result) {
        this.result = result;
        this.date = "";
        this.hour = "";
        this.chanel = "";
        this.episode = "";
        this.filmID = "";
    }

    public TvSeries getResult() {
        return result;
    }

    public void setResult(TvSeries result) {
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

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getFilmID() {
        return filmID;
    }

    public void setFilmID(String filmID) {
        this.filmID = filmID;
    }
}
