package com.KamilIsmail.MovieApp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReminderDTO {

    @JsonProperty("mediaType")
    private String mediaType;

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("poster_path")
    private String poster_path;

    @JsonProperty("on_tv_date")
    private String onTVDate;

    @JsonProperty("station")
    private String station;

    @JsonProperty("logo_path")
    private String logoPath;

    @JsonProperty("release_date")
    private String release_date;

    @JsonProperty("rating")
    private String rating;

    public ReminderDTO(String mediaType, String id, String title, String poster_path, String onTvDate, String station,
                       String logoPath, String release_date, String rating) {
        this.mediaType = mediaType;
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.onTVDate = onTvDate;
        this.station = station;
        this.logoPath = logoPath;
        this.release_date = release_date;
        this.rating = rating;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOnTVDate() {
        return onTVDate;
    }

    public void setOnTVDate(String onTVDate) {
        this.onTVDate = onTVDate;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
