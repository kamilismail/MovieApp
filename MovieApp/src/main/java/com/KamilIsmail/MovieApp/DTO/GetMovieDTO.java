package com.KamilIsmail.MovieApp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.movito.themoviedbapi.model.MovieDb;

public class GetMovieDTO {

    @JsonProperty("media_type")
    private String mediaType;

    @JsonProperty("avarage_rating")
    private String avarageRating;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("title")
    private String title;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("date")
    private String date;

    @JsonProperty("hour")
    private String hour;

    @JsonProperty("chanel")
    private String chanel;

    @JsonProperty("logo_path")
    private String logoPath;

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

    public GetMovieDTO(String mediaType, String avarageRating, String overview, String backdropPath, String posterPath,
                       String title, String releaseDate, String date, String hour, String chanel, String filmID,
                       String userRating, boolean wantToWatch, boolean userFav, boolean reminder, String logoPath) {
        this.mediaType = mediaType;
        this.avarageRating = avarageRating;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
        this.date = date;
        this.hour = hour;
        this.chanel = chanel;
        this.logoPath = logoPath;
        this.filmID = filmID;
        this.userRating = userRating;
        this.wantToWatch = String.valueOf(wantToWatch);
        this.fav = String.valueOf(userFav);
        this.reminder = String.valueOf(reminder);
    }

    public GetMovieDTO(String mediaType, String avarageRating, String overview, String backdropPath, String posterPath,
                       String title, String releaseDate, String userRating) {
        this.mediaType = mediaType;
        this.avarageRating = avarageRating;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
        this.date = "";
        this.hour = "";
        this.chanel = "";
        this.filmID = "";
        this.userRating = userRating;
    }

    public GetMovieDTO(String mediaType, String avarageRating, String overview, String backdropPath, String posterPath,
                       String title, String releaseDate, String userRating, boolean wantToWatch, boolean userFav,
                       boolean reminder) {
        this.mediaType = mediaType;
        this.avarageRating = avarageRating;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
        this.date = "";
        this.hour = "";
        this.chanel = "";
        this.filmID = "";
        this.userRating = userRating;
        this.wantToWatch = String.valueOf(wantToWatch);
        this.fav = String.valueOf(userFav);
        this.reminder = String.valueOf(reminder);
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getAvarageRating() {
        return avarageRating;
    }

    public void setAvarageRating(String avarageRating) {
        this.avarageRating = avarageRating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
}
