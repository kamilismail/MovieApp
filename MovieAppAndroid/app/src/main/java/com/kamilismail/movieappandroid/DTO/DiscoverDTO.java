package com.kamilismail.movieappandroid.DTO;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscoverDTO {

    @SerializedName("popular_movies")
    @Expose
    private List<PopularMoviesDTO> popularMovies = null;
    @SerializedName("popular_series")
    @Expose
    private List<PopularSeriesDTO> popularSeries = null;
    @SerializedName("upcoming_movies")
    @Expose
    private List<UpcomingMoviesDTO> upcomingMovies = null;
    @SerializedName("nowplaying")
    @Expose
    private List<NowPlayingDTO> nowplaying = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public DiscoverDTO() {
    }

    /**
     *
     * @param popularMovies
     * @param nowplaying
     * @param upcomingMovies
     * @param popularSeries
     */
    public DiscoverDTO(List<PopularMoviesDTO> popularMovies, List<PopularSeriesDTO> popularSeries, List<UpcomingMoviesDTO> upcomingMovies, List<NowPlayingDTO> nowplaying) {
        super();
        this.popularMovies = popularMovies;
        this.popularSeries = popularSeries;
        this.upcomingMovies = upcomingMovies;
        this.nowplaying = nowplaying;
    }

    public List<PopularMoviesDTO> getPopularMovies() {
        return popularMovies;
    }

    public void setPopularMovies(List<PopularMoviesDTO> popularMovies) {
        this.popularMovies = popularMovies;
    }

    public List<PopularSeriesDTO> getPopularSeries() {
        return popularSeries;
    }

    public void setPopularSeries(List<PopularSeriesDTO> popularSeries) {
        this.popularSeries = popularSeries;
    }

    public List<UpcomingMoviesDTO> getUpcomingMovies() {
        return upcomingMovies;
    }

    public void setUpcomingMovies(List<UpcomingMoviesDTO> upcomingMovies) {
        this.upcomingMovies = upcomingMovies;
    }

    public List<NowPlayingDTO> getNowplaying() {
        return nowplaying;
    }

    public void setNowplaying(List<NowPlayingDTO> nowplaying) {
        this.nowplaying = nowplaying;
    }

}