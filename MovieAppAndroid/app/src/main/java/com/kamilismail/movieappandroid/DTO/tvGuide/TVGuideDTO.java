package com.kamilismail.movieappandroid.DTO.tvGuide;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TVGuideDTO {

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
    @SerializedName("film_id")
    @Expose
    private String filmId;
    @SerializedName("user_rating")
    @Expose
    private String userRating;

    /**
     * No args constructor for use in serialization
     *
     */
    public TVGuideDTO() {
    }

    /**
     *
     * @param result
     * @param chanel
     * @param userRating
     * @param hour
     * @param date
     * @param filmId
     */
    public TVGuideDTO(Result result, String date, String hour, String chanel, String filmId, String userRating) {
        super();
        this.result = result;
        this.date = date;
        this.hour = hour;
        this.chanel = chanel;
        this.filmId = filmId;
        this.userRating = userRating;
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

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

}

