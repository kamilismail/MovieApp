package com.kamilismail.movieappandroid.DTO;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscoverDTO {

    @SerializedName("popular_movies")
    @Expose
    private ArrayList<PopularMoviesDTO> popularMovies = null;
    @SerializedName("popular_series")
    @Expose
    private ArrayList<PopularSeriesDTO> popularSeries = null;
    @SerializedName("upcoming_movies")
    @Expose
    private ArrayList<UpcomingMoviesDTO> upcomingMovies = null;
    @SerializedName("nowplaying")
    @Expose
    private ArrayList<NowPlayingDTO> nowplaying = null;

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
    public DiscoverDTO(ArrayList<PopularMoviesDTO> popularMovies, ArrayList<PopularSeriesDTO> popularSeries, ArrayList<UpcomingMoviesDTO> upcomingMovies, ArrayList<NowPlayingDTO> nowplaying) {
        super();
        this.popularMovies = popularMovies;
        this.popularSeries = popularSeries;
        this.upcomingMovies = upcomingMovies;
        this.nowplaying = nowplaying;
    }

    public ArrayList<PopularMoviesDTO> getPopularMovies() {
        return popularMovies;
    }

    public void setPopularMovies(ArrayList<PopularMoviesDTO> popularMovies) {
        this.popularMovies = popularMovies;
    }

    public ArrayList<PopularSeriesDTO> getPopularSeries() {
        return popularSeries;
    }

    public void setPopularSeries(ArrayList<PopularSeriesDTO> popularSeries) {
        this.popularSeries = popularSeries;
    }

    public ArrayList<UpcomingMoviesDTO> getUpcomingMovies() {
        return upcomingMovies;
    }

    public void setUpcomingMovies(ArrayList<UpcomingMoviesDTO> upcomingMovies) {
        this.upcomingMovies = upcomingMovies;
    }

    public ArrayList<NowPlayingDTO> getNowplaying() {
        return nowplaying;
    }

    public void setNowplaying(ArrayList<NowPlayingDTO> nowplaying) {
        this.nowplaying = nowplaying;
    }

}