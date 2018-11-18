package com.KamilIsmail.MovieApp.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author kamilismail
 * Encja tabeli movie_comments. Zawiera komenatrze użytkowników.
 */
@Entity
@GenericGenerator(name = "seq11", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "org.hibernate.id.enhanced.SequenceStyleGenerator", value = "moviecomments_id_seq"))
@Table(name = "movie_comments", schema = "public", catalog = "d55rc0894f06nc")
public class MovieCommentsEntity {
    private int movieCommentsId;
    private int movieId;
    private int userId;
    private String comment;
    private UserEntity userByUserId;
    private MoviesEntity moviesByMovieId;

    @Id
    @GeneratedValue(generator = "seq11")
    @Column(name = "moviecommentsid")
    public int getMovieCommentsId() {
        return movieCommentsId;
    }

    public void setMovieCommentsId(int movieCommentsId) {
        this.movieCommentsId = movieCommentsId;
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
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public MovieCommentsEntity(int movieId, int userId, String comment) {
        this.movieId = movieId;
        this.userId = userId;
        this.comment = comment;
    }

    public MovieCommentsEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieCommentsEntity that = (MovieCommentsEntity) o;
        return movieCommentsId == that.movieCommentsId &&
                movieId == that.movieId &&
                userId == that.userId &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(userByUserId, that.userByUserId) &&
                Objects.equals(moviesByMovieId, that.moviesByMovieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieCommentsId, movieId, userId, comment, userByUserId, moviesByMovieId);
    }
}
