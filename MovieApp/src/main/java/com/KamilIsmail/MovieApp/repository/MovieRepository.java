package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MoviesEntity, Integer> {
    MoviesEntity findByMovieId(int movieID);

    MoviesEntity findByTmdbId(int movieID);

    MoviesEntity findMovieEntityByMovieId(int pmovieIDubId);
}
