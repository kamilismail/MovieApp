package com.KamilIsmail.MovieApp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class DiscoverDTO {

    @JsonProperty("popular_movies")
    private List<DiscoverMovieDTO> popularMovies;

    @JsonProperty("popular_series")
    private List<DiscoverSeriesDTO> popularSeries;

    @JsonProperty("upcoming_movies")
    private List<DiscoverMovieDTO> upcoming;

    @JsonProperty("nowplaying")
    private List<DiscoverMovieDTO> nowplaying;

    public DiscoverDTO(List<DiscoverMovieDTO> upcoming, List<DiscoverMovieDTO> nowplaying, List<DiscoverMovieDTO> popularMovies,
                       List<DiscoverSeriesDTO> popularSeries) {
        this.upcoming = new ArrayList<>();
        this.nowplaying = new ArrayList<>();
        this.popularMovies = new ArrayList<>();
        this.popularSeries = new ArrayList<>();
        this.upcoming = upcoming;
        this.nowplaying = nowplaying;
        this.popularMovies = popularMovies;
        this.popularSeries = popularSeries;
    }

    public List<DiscoverMovieDTO> getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(List<DiscoverMovieDTO> upcoming) {
        this.upcoming = upcoming;
    }

    public List<DiscoverMovieDTO> getNowplaying() {
        return nowplaying;
    }

    public void setNowplaying(List<DiscoverMovieDTO> nowplaying) {
        this.nowplaying = nowplaying;
    }

    public List<DiscoverMovieDTO> getPopularMovies() {
        return popularMovies;
    }

    public void setPopularMovies(List<DiscoverMovieDTO> popularMovies) {
        this.popularMovies = popularMovies;
    }

    public List<DiscoverSeriesDTO> getPopularSeries() {
        return popularSeries;
    }

    public void setPopularSeries(List<DiscoverSeriesDTO> popularSeries) {
        this.popularSeries = popularSeries;
    }
}
