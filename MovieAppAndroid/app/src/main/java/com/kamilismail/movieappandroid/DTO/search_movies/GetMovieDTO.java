package com.kamilismail.movieappandroid.DTO.search_movies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetMovieDTO {

    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("avarage_rating")
    @Expose
    private String avarageRating;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("hour")
    @Expose
    private String hour;
    @SerializedName("chanel")
    @Expose
    private String chanel;
    @SerializedName("logo_path")
    @Expose
    private String logoPath;
    @SerializedName("film_id")
    @Expose
    private String filmID;
    @SerializedName("user_rating")
    @Expose
    private String userRating;
    @SerializedName("user_want_to_watch")
    @Expose
    private Boolean userWantToWatch;
    @SerializedName("user_fav")
    @Expose
    private Boolean userFav;
    @SerializedName("user_reminder")
    @Expose
    private Boolean userReminder;
    @SerializedName("comments")
    @Expose
    private ArrayList<MovieCommentsDTO> movieCommentsDTOS;
    @SerializedName("user_comment")
    @Expose
    private Boolean userComment;

    /**
     * No args constructor for use in serialization
     */
    public GetMovieDTO() {
    }

    public GetMovieDTO(String mediaType, String avarageRating, String overview, String backdropPath,
                       String posterPath, String title, String releaseDate, String date, String hour,
                       String chanel, String logoPath, String filmID, String userRating, Boolean userWantToWatch,
                       Boolean userFav, Boolean userReminder, ArrayList<MovieCommentsDTO> movieCommentsDTOS,
                       Boolean userComment) {
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
        this.userWantToWatch = userWantToWatch;
        this.userFav = userFav;
        this.userReminder = userReminder;
        this.movieCommentsDTOS = movieCommentsDTOS;
        this.userComment = userComment;
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

    public Boolean getUserWantToWatch() {
        return userWantToWatch;
    }

    public void setUserWantToWatch(Boolean userWantToWatch) {
        this.userWantToWatch = userWantToWatch;
    }

    public Boolean getUserFav() {
        return userFav;
    }

    public void setUserFav(Boolean userFav) {
        this.userFav = userFav;
    }

    public Boolean getUserReminder() {
        return userReminder;
    }

    public void setUserReminder(Boolean userReminder) {
        this.userReminder = userReminder;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public ArrayList<MovieCommentsDTO> getMovieCommentsDTOS() {
        return movieCommentsDTOS;
    }

    public void setMovieCommentsDTOS(ArrayList<MovieCommentsDTO> movieCommentsDTOS) {
        this.movieCommentsDTOS = movieCommentsDTOS;
    }

    public Boolean getUserComment() {
        return userComment;
    }

    public void setUserComment(Boolean userComment) {
        this.userComment = userComment;
    }
}
