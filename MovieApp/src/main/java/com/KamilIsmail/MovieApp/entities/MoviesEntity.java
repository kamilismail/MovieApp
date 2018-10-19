package com.KamilIsmail.MovieApp.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@GenericGenerator(name = "seq2", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "favourite_id_seq", value = "favourite_id_seq"))
@Table(name = "movies", schema = "public", catalog = "d55rc0894f06nc")
public class MoviesEntity {
    private int movieId;
    private String movieName;
    private Integer filmwebId;
    private Integer tmdbId;
    private String posterPath;
    private String releaseDate;
    private String backdropPath;
    private String mediaType;
    private String avarageRating;
    private String overview;
    private Collection<FavouritesEntity> favouritesByMovieId;
    private Collection<RemindersEntity> remindersByMovieId;
    private Collection<WanttowatchEntity> wanttowatchesByMovieId;
    private Collection<RatingsEntity> ratingsByMovieId;

    @Id
    @GeneratedValue(generator = "seq2")
    @Column(name = "movieid")
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Basic
    @Column(name = "moviename")
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    @Basic
    @Column(name = "filmwebid")
    public Integer getFilmwebId() {
        return filmwebId;
    }

    public void setFilmwebId(Integer filmwebId) {
        this.filmwebId = filmwebId;
    }

    @Basic
    @Column(name = "tmdbid")
    public Integer getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Integer tmdbId) {
        this.tmdbId = tmdbId;
    }

    @Basic
    @Column(name = "posterPath")
    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String poster_path) {
        this.posterPath = poster_path;
    }

    @Basic
    @Column(name = "releaseDate")
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String release_date) {
        this.releaseDate = release_date;
    }

    @Basic
    @Column(name="backdropPath")
    public String getBackdropPath() {
        return backdropPath;
    }
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @Basic
    @Column(name="mediaType")
    public String getMediaType() {
        return mediaType;
    }
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    @Basic
    @Column(name="avarageRating")
    public String getAvarageRating() {
        return avarageRating;
    }

    public void setAvarageRating(String avarageRating) {
        this.avarageRating = avarageRating;
    }

    @Basic
    @Column(name="overview")
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public MoviesEntity() {
    }

    public MoviesEntity(String movieName, Integer filmwebId, Integer tmdbId, String posterPath,
                        String releaseDate, String backdropPath, String mediaType, String avarageRating, String overview) {
        this.movieName = movieName;
        this.filmwebId = filmwebId;
        this.tmdbId = tmdbId;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.backdropPath = backdropPath;
        this.mediaType = mediaType;
        this.avarageRating = avarageRating;
        this.overview = overview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoviesEntity that = (MoviesEntity) o;
        return movieId == that.movieId &&
                Objects.equals(movieName, that.movieName) &&
                Objects.equals(filmwebId, that.filmwebId) &&
                Objects.equals(posterPath, that.posterPath) &&
                Objects.equals(releaseDate, that.releaseDate) &&
                Objects.equals(tmdbId, that.tmdbId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(movieId, movieName, filmwebId, tmdbId);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "moviesByMovieId")
    public Collection<FavouritesEntity> getFavouritesByMovieId() {
        return favouritesByMovieId;
    }

    public void setFavouritesByMovieId(Collection<FavouritesEntity> favouritesByMovieId) {
        this.favouritesByMovieId = favouritesByMovieId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "moviesByMovieId")
    public Collection<RemindersEntity> getRemindersByMovieId() {
        return remindersByMovieId;
    }

    public void setRemindersByMovieId(Collection<RemindersEntity> remindersByMovieId) {
        this.remindersByMovieId = remindersByMovieId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "moviesByMovieId")
    public Collection<WanttowatchEntity> getWanttowatchesByMovieId() {
        return wanttowatchesByMovieId;
    }

    public void setWanttowatchesByMovieId(Collection<WanttowatchEntity> wanttowatchesByMovieId) {
        this.wanttowatchesByMovieId = wanttowatchesByMovieId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "moviesByMovieId")
    public Collection<RatingsEntity> getRatingsByMovieId() {
        return ratingsByMovieId;
    }

    public void setRatingsByMovieId(Collection<RatingsEntity> ratingsByMovieId) {
        this.ratingsByMovieId = ratingsByMovieId;
    }
}
