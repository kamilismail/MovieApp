package com.KamilIsmail.MovieApp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class DiscoverDTO {

    @JsonProperty("popular_movies")
    private ArrayList<DiscoverMovieDTO> popularMovies;

    @JsonProperty("popular_series")
    private ArrayList<DiscoverSeriesDTO> popularSeries;

    @JsonProperty("upcoming_movies")
    private ArrayList<DiscoverMovieDTO> upcoming;

    @JsonProperty("nowplaying")
    private ArrayList<DiscoverMovieDTO> nowplaying;

    public DiscoverDTO(ArrayList<DiscoverMovieDTO> upcoming, ArrayList<DiscoverMovieDTO> nowplaying, ArrayList<DiscoverMovieDTO> popularMovies, ArrayList<DiscoverSeriesDTO> popularSeries) {
        this.upcoming = new ArrayList<DiscoverMovieDTO>();
        this.nowplaying = new ArrayList<DiscoverMovieDTO>();
        this.popularMovies = new ArrayList<DiscoverMovieDTO>();
        this.popularSeries = new ArrayList<DiscoverSeriesDTO>();
        this.upcoming = upcoming;
        this.nowplaying = nowplaying;
        this.popularMovies = popularMovies;
        this.popularSeries = popularSeries;
    }

    public ArrayList<DiscoverMovieDTO> getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(ArrayList<DiscoverMovieDTO> upcoming) {
        this.upcoming = upcoming;
    }

    public ArrayList<DiscoverMovieDTO> getNowplaying() {
        return nowplaying;
    }

    public void setNowplaying(ArrayList<DiscoverMovieDTO> nowplaying) {
        this.nowplaying = nowplaying;
    }

    public ArrayList<DiscoverMovieDTO> getPopularMovies() {
        return popularMovies;
    }

    public void setPopularMovies(ArrayList<DiscoverMovieDTO> popularMovies) {
        this.popularMovies = popularMovies;
    }

    public ArrayList<DiscoverSeriesDTO> getPopularSeries() {
        return popularSeries;
    }

    public void setPopularSeries(ArrayList<DiscoverSeriesDTO> popularSeries) {
        this.popularSeries = popularSeries;
    }
}
