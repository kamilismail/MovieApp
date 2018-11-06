package com.kamilismail.movieappandroid.DTO.search_series;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeriesDetailsDTO {

    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("hour")
    @Expose
    private String hour;
    @SerializedName("chanel")
    @Expose
    private String chanel;
    @SerializedName("episode")
    @Expose
    private String episode;
    @SerializedName("film_id")
    @Expose
    private String filmId;

    /**
     * No args constructor for use in serialization
     *
     */
    public SeriesDetailsDTO() {
    }

    /**
     *
     * @param result
     * @param chanel
     * @param episode
     * @param hour
     * @param date
     * @param filmId
     */
    public SeriesDetailsDTO(Result result, String date, String hour, String chanel, String episode, String filmId) {
        super();
        this.result = result;
        this.date = date;
        this.hour = hour;
        this.chanel = chanel;
        this.episode = episode;
        this.filmId = filmId;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
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

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

}