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
    private Collection<FavouritesEntity> favouritesByMovieId;
    private Collection<RemindersEntity> remindersByMovieId;
    private Collection<WanttowatchEntity> wanttowatchesByMovieId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoviesEntity that = (MoviesEntity) o;
        return movieId == that.movieId &&
                Objects.equals(movieName, that.movieName) &&
                Objects.equals(filmwebId, that.filmwebId) &&
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
}
