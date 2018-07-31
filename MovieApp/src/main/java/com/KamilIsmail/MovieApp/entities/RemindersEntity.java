package com.KamilIsmail.MovieApp.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@GenericGenerator(name = "seq5", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "reminder_id_seq", value = "reminder_id_seq"))
@Table(name = "reminders", schema = "public", catalog = "d55rc0894f06nc")
public class RemindersEntity {
    private int reminderId;
    private Integer userId;
    private Integer movieId;
    private Integer tvstationId;
    private Boolean reminded;
    private Timestamp data;
    private UserEntity userByUserId;
    private MoviesEntity moviesByMovieId;
    private TvstationsEntity tvstationsByTvstationId;

    @Id
    @GeneratedValue(generator = "seq5")
    @Column(name = "reminderid")
    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    @Basic
    @Column(name = "data")
    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    @Basic
    @Column(name = "userid", insertable = false, updatable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "movieid", insertable = false, updatable = false)
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Basic
    @Column(name = "tvstationid", insertable = false, updatable = false)
    public int getTvstationId() {
        return tvstationId;
    }

    public void setTvstationId(int tvstationId) {
        this.tvstationId = tvstationId;
    }

    @Basic
    @Column(name = "reminded")
    public Boolean getReminded() {
        return reminded;
    }

    public void setReminded(Boolean reminded) {
        this.reminded = reminded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemindersEntity that = (RemindersEntity) o;
        return reminderId == that.reminderId &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(movieId, that.movieId) &&
                Objects.equals(tvstationId, that.tvstationId) &&
                Objects.equals(reminded, that.reminded) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {

        return Objects.hash(reminderId, userId, movieId, tvstationId, data, reminded);
    }

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
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
}
