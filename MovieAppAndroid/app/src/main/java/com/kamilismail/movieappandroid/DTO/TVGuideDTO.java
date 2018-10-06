package com.kamilismail.movieappandroid.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TVGuideDTO {
    @SerializedName("mediaType")
    @Expose
    private String mediaType;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("chanel")
    @Expose
    private String chanel;
    @SerializedName("logo_path")
    @Expose
    private String logoPath;
    @SerializedName("hour")
    @Expose
    private String hour;

    /**
     * No args constructor for use in serialization
     */
    public TVGuideDTO() {
    }

    /**
     * @param id
     * @param chanel
     * @param title
     * @param releaseDate
     * @param posterPath
     * @param hour
     * @param rating
     * @param logoPath
     * @param mediaType
     */
    public TVGuideDTO(String mediaType, String id, String title, String posterPath, String releaseDate, String rating, String chanel, String logoPath, String hour) {
        super();
        this.mediaType = mediaType;
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.chanel = chanel;
        this.logoPath = logoPath;
        this.hour = hour;
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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}