package com.kamilismail.movieappandroid.DTO.search_movies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("mediaType")
    @Expose
    private String mediaType;
    @SerializedName("cast")
    @Expose
    private Object cast;
    @SerializedName("crew")
    @Expose
    private Object crew;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("backdrop_path")
    @Expose
    private Object backdropPath;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("belongs_to_collection")
    @Expose
    private Object belongsToCollection;
    @SerializedName("budget")
    @Expose
    private Integer budget;
    @SerializedName("genres")
    @Expose
    private Object genres;
    @SerializedName("homepage")
    @Expose
    private Object homepage;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("imdb_id")
    @Expose
    private Object imdbId;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("production_companies")
    @Expose
    private Object productionCompanies;
    @SerializedName("production_countries")
    @Expose
    private Object productionCountries;
    @SerializedName("revenue")
    @Expose
    private Integer revenue;
    @SerializedName("runtime")
    @Expose
    private Integer runtime;
    @SerializedName("spoken_languages")
    @Expose
    private Object spokenLanguages;
    @SerializedName("tagline")
    @Expose
    private Object tagline;
    @SerializedName("rating")
    @Expose
    private Float rating;
    @SerializedName("vote_average")
    @Expose
    private Float voteAverage;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("alternative_titles")
    @Expose
    private Object alternativeTitles;
    @SerializedName("credits")
    @Expose
    private Object credits;
    @SerializedName("images")
    @Expose
    private Object images;
    @SerializedName("keywords")
    @Expose
    private Object keywords;
    @SerializedName("releases")
    @Expose
    private Object releases;
    @SerializedName("videos")
    @Expose
    private Object videos;
    @SerializedName("translations")
    @Expose
    private Object translations;
    @SerializedName("similar")
    @Expose
    private Object similar;
    @SerializedName("reviews")
    @Expose
    private Object reviews;
    @SerializedName("lists")
    @Expose
    private Object lists;

    /**
     * No args constructor for use in serialization
     *
     */
    public Result() {
    }

    /**
     *
     * @param budget
     * @param alternativeTitles
     * @param genres
     * @param spokenLanguages
     * @param crew
     * @param credits
     * @param runtime
     * @param backdropPath
     * @param voteCount
     * @param mediaType
     * @param id
     * @param title
     * @param releaseDate
     * @param posterPath
     * @param originalTitle
     * @param voteAverage
     * @param lists
     * @param similar
     * @param popularity
     * @param revenue
     * @param productionCountries
     * @param reviews
     * @param keywords
     * @param status
     * @param releases
     * @param videos
     * @param originalLanguage
     * @param adult
     * @param imdbId
     * @param homepage
     * @param belongsToCollection
     * @param cast
     * @param overview
     * @param translations
     * @param images
     * @param productionCompanies
     * @param rating
     * @param tagline
     */
    public Result(String mediaType, Object cast, Object crew, Integer id, String title, String originalTitle, Double popularity, Object backdropPath, String posterPath, String releaseDate, Boolean adult, Object belongsToCollection, Integer budget, Object genres, Object homepage, String overview, Object imdbId, String originalLanguage, Object productionCompanies, Object productionCountries, Integer revenue, Integer runtime, Object spokenLanguages, Object tagline, Float rating, Float voteAverage, Integer voteCount, Object status, Object alternativeTitles, Object credits, Object images, Object keywords, Object releases, Object videos, Object translations, Object similar, Object reviews, Object lists) {
        super();
        this.mediaType = mediaType;
        this.cast = cast;
        this.crew = crew;
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.popularity = popularity;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.adult = adult;
        this.belongsToCollection = belongsToCollection;
        this.budget = budget;
        this.genres = genres;
        this.homepage = homepage;
        this.overview = overview;
        this.imdbId = imdbId;
        this.originalLanguage = originalLanguage;
        this.productionCompanies = productionCompanies;
        this.productionCountries = productionCountries;
        this.revenue = revenue;
        this.runtime = runtime;
        this.spokenLanguages = spokenLanguages;
        this.tagline = tagline;
        this.rating = rating;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.status = status;
        this.alternativeTitles = alternativeTitles;
        this.credits = credits;
        this.images = images;
        this.keywords = keywords;
        this.releases = releases;
        this.videos = videos;
        this.translations = translations;
        this.similar = similar;
        this.reviews = reviews;
        this.lists = lists;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Object getCast() {
        return cast;
    }

    public void setCast(Object cast) {
        this.cast = cast;
    }

    public Object getCrew() {
        return crew;
    }

    public void setCrew(Object crew) {
        this.crew = crew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Object getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(Object backdropPath) {
        this.backdropPath = backdropPath;
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

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public Object getBelongsToCollection() {
        return belongsToCollection;
    }

    public void setBelongsToCollection(Object belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
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

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Object getImdbId() {
        return imdbId;
    }

    public void setImdbId(Object imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Object getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(Object productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public Object getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(Object productionCountries) {
        this.productionCountries = productionCountries;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public Object getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(Object spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public Object getTagline() {
        return tagline;
    }

    public void setTagline(Object tagline) {
        this.tagline = tagline;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
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

    public Object getAlternativeTitles() {
        return alternativeTitles;
    }

    public void setAlternativeTitles(Object alternativeTitles) {
        this.alternativeTitles = alternativeTitles;
    }

    public Object getCredits() {
        return credits;
    }

    public void setCredits(Object credits) {
        this.credits = credits;
    }

    public Object getImages() {
        return images;
    }

    public void setImages(Object images) {
        this.images = images;
    }

    public Object getKeywords() {
        return keywords;
    }

    public void setKeywords(Object keywords) {
        this.keywords = keywords;
    }

    public Object getReleases() {
        return releases;
    }

    public void setReleases(Object releases) {
        this.releases = releases;
    }

    public Object getVideos() {
        return videos;
    }

    public void setVideos(Object videos) {
        this.videos = videos;
    }

    public Object getTranslations() {
        return translations;
    }

    public void setTranslations(Object translations) {
        this.translations = translations;
    }

    public Object getSimilar() {
        return similar;
    }

    public void setSimilar(Object similar) {
        this.similar = similar;
    }

    public Object getReviews() {
        return reviews;
    }

    public void setReviews(Object reviews) {
        this.reviews = reviews;
    }

    public Object getLists() {
        return lists;
    }

    public void setLists(Object lists) {
        this.lists = lists;
    }

}
