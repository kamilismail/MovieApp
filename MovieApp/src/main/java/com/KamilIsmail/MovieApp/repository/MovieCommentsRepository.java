package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.MovieCommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author kamilismail
 * Zapytania sql z tabelą moviecomments
 */
public interface MovieCommentsRepository extends JpaRepository<MovieCommentsEntity, Long> {

    /**
     * Zapytanie zwracjące listę pozycji z tabeli ze względu na pozycję filmową.
     * @param movieId
     * @return
     */
    List<MovieCommentsEntity> findMovieCommentsEntitiesByMovieId(int movieId);

    /**
     * Zapytanie zwracające pozycję z tabeli ze względu na id użytkownika i pozycje filmową.
     * @param userId
     * @param movieId
     * @return
     */
    MovieCommentsEntity findMovieCommentsEntityByUserIdAndMovieId(int userId, int movieId);

    /**
     * Zapytanie zwracjące listę komentarzy danego użytkownika.
     * @param userId
     * @return
     */
    List<MovieCommentsEntity> findMovieCommentsEntitiesByUserId(int userId);
}
