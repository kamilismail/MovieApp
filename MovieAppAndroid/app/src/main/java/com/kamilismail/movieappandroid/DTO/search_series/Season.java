package com.kamilismail.movieappandroid.DTO.search_series;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Season {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("credits")
    @Expose
    private Object credits;
    @SerializedName("external_ids")
    @Expose
    private Object externalIds;
    @SerializedName("images")
    @Expose
    private Object images;
    @SerializedName("videos")
    @Expose
    private Object videos;
    @SerializedName("air_date")
    @Expose
    private String airDate;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("season_number")
    @Expose
    private Integer seasonNumber;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("episodes")
    @Expose
    private Object episodes;

    /**
     * No args constructor for use in serialization
     *
     */
    public Season() {
    }

    /**
     *
     * @param airDate
     * @param id
     * @param externalIds
     * @param overview
     * @param posterPath
     * @param videos
     * @param name
     * @param seasonNumber
     * @param images
     * @param credits
     * @param episodes
     */
    public Season(Integer id, String name, Object credits, Object externalIds, Object images, Object videos, String airDate, String posterPath, Integer seasonNumber, String overview, Object episodes) {
        super();
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.externalIds = externalIds;
        this.images = images;
        this.videos = videos;
        this.airDate = airDate;
        this.posterPath = posterPath;
        this.seasonNumber = seasonNumber;
        this.overview = overview;
        this.episodes = episodes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getCredits() {
        return credits;
    }

    public void setCredits(Object credits) {
        this.credits = credits;
    }

    public Object getExternalIds() {
        return externalIds;
    }

    public void setExternalIds(Object externalIds) {
        this.externalIds = externalIds;
    }

    public Object getImages() {
        return images;
    }

    public void setImages(Object images) {
        this.images = images;
    }

    public Object getVideos() {
        return videos;
    }

    public void setVideos(Object videos) {
        this.videos = videos;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Object getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Object episodes) {
        this.episodes = episodes;
    }

}