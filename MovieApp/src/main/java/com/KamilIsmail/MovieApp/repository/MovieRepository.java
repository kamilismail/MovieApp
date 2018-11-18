package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kamilismail
 * Klasa zawierająca zapytania sql z tabeli movies.
 */
public interface MovieRepository extends JpaRepository<MoviesEntity, Integer> {
    /**
     * Zapytanie zwracające daną pozycję filmową.
     * @param movieID
     * @return
     */
    MoviesEntity findByMovieId(int movieID);

    /**
     * Zapytanie zwracjące daną pozycję filmową ze względu na kolumnę tmdbid.
     * @param movieID
     * @return
     */
    MoviesEntity findByTmdbId(int movieID);

    /**
     * Zapytanie zwrcające pozycję filmową ze względu na id.
     * @param movieID
     * @return
     */
    MoviesEntity findMovieEntityByMovieId(int movieID);

    /**
     * Zapytanie zwracające pozycję filmową ze względu na kolumnę tmdbid.
     * @param tmdbID
     * @return
     */
    MoviesEntity findMoviesEntityByTmdbId(int tmdbID);
}
