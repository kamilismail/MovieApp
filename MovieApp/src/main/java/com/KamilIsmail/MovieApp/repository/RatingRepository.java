package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.RatingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author kamilismail
 * Klasa zawierająca zapytania sql z tabelą ratings.
 */
public interface RatingRepository extends JpaRepository<RatingsEntity, Long> {
    /**
     * Zapytania zwracające listę pozycji ze względu na użytkownika.
     * @param userId
     * @return
     */
    List<RatingsEntity> findRatingsEntityByUserId(long userId);

    /**
     * Zapytanie zwracające pozycję ze względu na pozycję filmową.
     * @param movieId
     * @return
     */
    RatingsEntity findRatingsEntityByMovieId(int movieId);

    /**
     * Zapytanie zwracające pozycję ze względu na użytkwonika i pozycję filmową.
     * @param userId
     * @param movieId
     * @return
     */
    RatingsEntity findRatingsEntityByUserByUserIdAndMovieId(long userId, int movieId);
}
