package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.WanttowatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author kamilismail
 * Klasa zawierająca zapytania sql do tabeli wanttowatch.
 */
public interface WantToWatchRepository extends JpaRepository<WanttowatchEntity, Long> {
    /**
     * Zapytanie zwraca listę pozycji ze względu na użytkownika.
     * @param userid
     * @return
     */
    List<WanttowatchEntity> findWanttowatchEntityByUserId(int userid);

    /**
     * Zapytanie zwraca listę pozycji ze względu na pozycję filmową.
     * @param movieId
     * @return
     */
    List<WanttowatchEntity> findByMovieId(int movieId);

    /**
     * Zapytanie zwraca pozycję ze względu na użytkownika i pozcyję filmową.
     * @param movieId
     * @param userId
     * @return
     */
    WanttowatchEntity findWanttowatchEntityByMovieIdAndUserId(int movieId, int userId);
}
