package com.kamilismail.movieappandroid.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemindersDTO {

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
    @SerializedName("on_tv_date")
    @Expose
    private String onTvDate;
    @SerializedName("station")
    @Expose
    private String station;
    @SerializedName("logo_path")
    @Expose
    private String logoPath;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("rating")
    @Expose
    private String rating;

    /**
     * No args constructor for use in serialization
     */
    public RemindersDTO() {
    }

    /**
     * @param id
     * @param onTvDate
     * @param title
     * @param releaseDate
     * @param station
     * @param posterPath
     * @param logoPath
     * @param mediaType
     */
    public RemindersDTO(String mediaType, String id, String title, String posterPath, String onTvDate,
                        String station, String logoPath, String releaseDate, String rating) {
        super();
        this.mediaType = mediaType;
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.onTvDate = onTvDate;
        this.station = station;
        this.logoPath = logoPath;
        this.releaseDate = releaseDate;
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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOnTvDate() {
        return onTvDate;
    }

    public void setOnTvDate(String onTvDate) {
        this.onTvDate = onTvDate;
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
}
