package com.KamilIsmail.MovieApp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kamilismail
 */
public class DiscoverSeriesDTO {
    @JsonProperty("mediaType")
    private String mediaType;

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("poster_path")
    private String poster_path;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("rating")
    private String rating;

    public DiscoverSeriesDTO(String id, String title, String poster_path) {
        this.mediaType = "tv";
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.rating = "";
    }

    public DiscoverSeriesDTO(String id, String title, String poster_path, String rating, String releaseDate) {
        this.mediaType = "tv";
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.rating = rating;
        this.releaseDate = releaseDate;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
