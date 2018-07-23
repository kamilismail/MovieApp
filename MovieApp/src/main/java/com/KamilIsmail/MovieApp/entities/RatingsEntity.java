package com.KamilIsmail.MovieApp.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@GenericGenerator(name = "seq4", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "rating_id_seq", value = "rating_id_seq"))
@Table(name = "ratings", schema = "public", catalog = "d55rc0894f06nc")
public class RatingsEntity {
    private int ratingId;
    private long userId;
    private Integer movieId;
    private String rating;
    private UserEntity userByUserId;
    private MoviesEntity moviesByMovieId;

    @Id
    @GeneratedValue(generator = "seq4")
    @Column(name = "ratingid")
    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    @Basic
    @Column(name = "rating")
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "userid", insertable = false, updatable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingsEntity that = (RatingsEntity) o;
        return ratingId == that.ratingId &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(movieId, that.movieId) &&
                Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ratingId, userId, movieId, rating);
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
}
