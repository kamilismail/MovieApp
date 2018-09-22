package com.KamilIsmail.MovieApp.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@GenericGenerator(name = "seq9", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "tvguide_id_seq", value = "tvguide_id_seq"))
@Table(name = "tvguide", schema = "public", catalog = "d55rc0894f06nc")
public class TVGuideEntity {

    private Integer tvguideId;
    private Integer movieId;
    private Integer tvstationId;
    //private Integer tmdbId;
    private Timestamp date;
    private MoviesEntity moviesByMovieId;
    private TvstationsEntity tvstationsByTvstationId;

    @Id
    @GeneratedValue(generator = "seq9")
    @Column(name = "tvguideid")
    public Integer getTvguideId() {
        return tvguideId;
    }

    public void setTvguideId(Integer tvguideId) {
        this.tvguideId = tvguideId;
    }

    @Basic
    @Column(name = "movieid")
    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    @Basic
    @Column(name = "tvstationid")
    public Integer getTvstationId() {
        return tvstationId;
    }

    public void setTvstationId(Integer tvstationId) {
        this.tvstationId = tvstationId;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @ManyToOne
    @JoinColumn(name = "movieid", referencedColumnName = "movieid")
    public MoviesEntity getMoviesByMovieId() {
        return moviesByMovieId;
    }

    public void setMoviesByMovieId(MoviesEntity moviesByMovieId) {
        this.moviesByMovieId = moviesByMovieId;
    }

    @ManyToOne
    @JoinColumn(name = "tvstationid", referencedColumnName = "tvstationid")
    public TvstationsEntity getTvstationsByTvstationId() {
        return tvstationsByTvstationId;
    }

    public void setTvstationsByTvstationId(TvstationsEntity tvstationsByTvstationId) {
        this.tvstationsByTvstationId = tvstationsByTvstationId;
    }
/*
    @Basic
    @Column(name = "tmdbid")
    public Integer getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Integer tmdbId) {
        this.tmdbId = tmdbId;
    }
    */
}
