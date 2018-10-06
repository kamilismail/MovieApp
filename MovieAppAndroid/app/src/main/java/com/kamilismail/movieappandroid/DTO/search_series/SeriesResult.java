package com.kamilismail.movieappandroid.DTO.search_series;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeriesResult {

    @SerializedName("mediaType")
    @Expose
    private String mediaType;
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
    @SerializedName("created_by")
    @Expose
    private Object createdBy;
    @SerializedName("episode_run_time")
    @Expose
    private Object episodeRunTime;
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("last_air_date")
    @Expose
    private Object lastAirDate;
    @SerializedName("genres")
    @Expose
    private Object genres;
    @SerializedName("homepage")
    @Expose
    private Object homepage;
    @SerializedName("original_name")
    @Expose
    private String originalName;
    @SerializedName("origin_country")
    @Expose
    private List<String> originCountry = null;
    @SerializedName("networks")
    @Expose
    private Object networks;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("number_of_episodes")
    @Expose
    private Integer numberOfEpisodes;
    @SerializedName("number_of_seasons")
    @Expose
    private Integer numberOfSeasons;
    @SerializedName("seasons")
    @Expose
    private Object seasons;
    @SerializedName("recommendations")
    @Expose
    private Object recommendations;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("status")
    @Expose
    private Object status;

    /**
     * No args constructor for use in serialization
     */
    public SeriesResult() {
    }

    /**
     * @param genres
     * @param credits
     * @param originalName
     * @param backdropPath
     * @param voteCount
     * @param mediaType
     * @param id
     * @param numberOfEpisodes
     * @param originCountry
     * @param name
     * @param posterPath
     * @param voteAverage
     * @param popularity
     * @param networks
     * @param externalIds
     * @param status
     * @param lastAirDate
     * @param videos
     * @param numberOfSeasons
     * @param homepage
     * @param createdBy
     * @param overview
     * @param seasons
     * @param firstAirDate
     * @param images
     * @param recommendations
     * @param rating
     * @param episodeRunTime
     */
    public SeriesResult(String mediaType, Integer id, String name, Object credits, Object externalIds, Object images, Object videos, Object createdBy, Object episodeRunTime, String firstAirDate, Object lastAirDate, Object genres, Object homepage, String originalName, List<String> originCountry, Object networks, String overview, Double popularity, String backdropPath, String posterPath, Integer numberOfEpisodes, Integer numberOfSeasons, Object seasons, Object recommendations, Integer rating, Double voteAverage, Integer voteCount, Object status) {
        super();
        this.mediaType = mediaType;
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.externalIds = externalIds;
        this.images = images;
        this.videos = videos;
        this.createdBy = createdBy;
        this.episodeRunTime = episodeRunTime;
        this.firstAirDate = firstAirDate;
        this.lastAirDate = lastAirDate;
        this.genres = genres;
        this.homepage = homepage;
        this.originalName = originalName;
        this.originCountry = originCountry;
        this.networks = networks;
        this.overview = overview;
        this.popularity = popularity;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.recommendations = recommendations;
        this.rating = rating;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.status = status;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
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

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getEpisodeRunTime() {
        return episodeRunTime;
    }

    public void setEpisodeRunTime(Object episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public Object getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(Object lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public Object getGenres() {
        return genres;
    }

    public void setGenres(Object genres) {
        this.genres = genres;
    }

    public Object getHomepage() {
        return homepage;
    }

    public void setHomepage(Object homepage) {
        this.homepage = homepage;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public Object getNetworks() {
        return networks;
    }

    public void setNetworks(Object networks) {
        this.networks = networks;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
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

    public Integer getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(Integer numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(Integer numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public Object getSeasons() {
        return seasons;
    }

    public void setSeasons(Object seasons) {
        this.seasons = seasons;
    }

    public Object getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Object recommendations) {
        this.recommendations = recommendations;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }
}