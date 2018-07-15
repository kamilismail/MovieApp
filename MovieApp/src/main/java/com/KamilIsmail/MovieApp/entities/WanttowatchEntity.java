package com.KamilIsmail.MovieApp.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@GenericGenerator(name = "seq8", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "wanttowatch_id_seq", value = "wanttowatch_id_seq"))
@Table(name = "wanttowatch", schema = "public", catalog = "d55rc0894f06nc")
public class WanttowatchEntity {
    private int wantId;
    private Integer userId;
    private Integer movieId;
    private UserEntity userByUserId;
    private MoviesEntity moviesByMovieId;

    @Id
    @GeneratedValue(generator = "seq8")
    @Column(name = "wantid")
    public int getWantId() {
        return wantId;
    }

    public void setWantId(int wantId) {
        this.wantId = wantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WanttowatchEntity that = (WanttowatchEntity) o;
        return wantId == that.wantId &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(movieId, that.movieId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(wantId, userId, movieId);
    }

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid", insertable = false, updatable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "movieid", referencedColumnName = "movieid", insertable = false, updatable = false)
    public MoviesEntity getMoviesByMovieId() {
        return moviesByMovieId;
    }

    public void setMoviesByMovieId(MoviesEntity moviesByMovieId) {
        this.moviesByMovieId = moviesByMovieId;
    }
}
