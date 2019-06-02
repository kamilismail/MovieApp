package com.KamilIsmail.MovieApp.DTO.recommender;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.movito.themoviedbapi.model.MovieDb;

import java.util.ArrayList;
import java.util.List;

public class RecommenderDTO {

    @JsonProperty("title")
    private String title;
    @JsonProperty("movie_id")
    private int id;
    @JsonProperty("genres")
    private List<String> genresList;
    @JsonProperty("actors")
    private List<String> actorsList;
    @JsonProperty("director")
    private String director;

    private String tags;
    @JsonProperty("score")
    private Double score;

    List<Long> occurences;
    @JsonProperty("movie_details")
    MovieDb movieDb;

    public RecommenderDTO() {
        genresList = new ArrayList<>();
        actorsList = new ArrayList<>();
        occurences = new ArrayList<>();
    }

    public RecommenderDTO(String title, int id, List<String> genresList, List<String> actorsList, String director) {
        this.title = title;
        this.id = id;
        this.genresList = genresList;
        this.actorsList = actorsList;
        this.director = director;
    }

    public RecommenderDTO(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public List<String> getGenresList() {
        return genresList;
    }

    public List<String> getActorsList() {
        return actorsList;
    }

    public String getDirector() {
        return director;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGenresList(List<String> genresList) {
        this.genresList = genresList;
    }

    public void setActorsList(List<String> actorsList) {
        this.actorsList = actorsList;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<Long> getOccurences() {
        return occurences;
    }

    public void setOccurences(List<Long> occurences) {
        this.occurences = occurences;
    }

    public MovieDb getMovieDb() {
        return movieDb;
    }

    public void setMovieDb(MovieDb movieDb) {
        this.movieDb = movieDb;
    }
}
