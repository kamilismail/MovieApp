package com.KamilIsmail.MovieApp.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author kamilismail
 * Encja tabeli "favourites" zawierającj pozycje ustawione przez użytkowników jako ulubione.
 */
@Entity
@GenericGenerator(name = "seq1", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "favourite_id_seq", value = "favourite_id_seq"))
@Table(name = "favourites", schema = "public", catalog = "d55rc0894f06nc")
public class FavouritesEntity {
    private int favId;
    private Integer userId;
    private Integer movieId;
    private UserEntity userByUserId;
    private MoviesEntity moviesByMovieId;

    @Id
    @GeneratedValue(generator = "seq1")
    @Column(name = "favid")
    public int getFavId() {
        return favId;
    }

    public void setFavId(int favId) {
        this.favId = favId;
    }

    @Basic
    @Column(name = "userid", nullable = false, insertable = false, updatable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "movieid", nullable = false, insertable = false, updatable = false)
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public FavouritesEntity(Integer userId, Integer movieId, UserEntity userByUserId, MoviesEntity moviesByMovieId) {
        this.userId = userId;
        this.movieId = movieId;
        this.userByUserId = userByUserId;
        this.moviesByMovieId = moviesByMovieId;
    }

    public FavouritesEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavouritesEntity that = (FavouritesEntity) o;
        return favId == that.favId &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(movieId, that.movieId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(favId, userId, movieId);
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
