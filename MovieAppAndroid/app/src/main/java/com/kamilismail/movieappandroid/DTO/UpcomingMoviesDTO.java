package com.kamilismail.movieappandroid.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpcomingMoviesDTO {
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

    /**
     * No args constructor for use in serialization
     *
     */
    public UpcomingMoviesDTO() {
    }

    /**
     *
     * @param id
     * @param title
     * @param releaseDate
     * @param posterPath
     * @param mediaType
     */
    public UpcomingMoviesDTO(String mediaType, String id, String title, String posterPath, String releaseDate) {
        super();
        this.mediaType = mediaType;
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
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
}
