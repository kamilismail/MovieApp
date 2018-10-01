package com.KamilIsmail.MovieApp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiscoverMovieDTO {
    @JsonProperty("mediaType")
    private String mediaType;

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("poster_path")
    private String poster_path;

    @JsonProperty("release_date")
    private String release_date;

    @JsonProperty("rating")
    private String rating;

    public DiscoverMovieDTO(String mediaType, String id, String title, String poster_path, String release_date) {
        this.mediaType = mediaType;
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.rating = "";
    }

    public DiscoverMovieDTO(String mediaType, String id, String title, String poster_path, String release_date, String rating) {
        this.mediaType = mediaType;
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
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
