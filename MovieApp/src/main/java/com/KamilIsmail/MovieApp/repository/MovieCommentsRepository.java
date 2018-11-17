package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.MovieCommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieCommentsRepository extends JpaRepository<MovieCommentsEntity, Long> {

    List<MovieCommentsEntity> findMovieCommentsEntitiesByMovieId(int movieId);

    MovieCommentsEntity findMovieCommentsEntityByUserIdAndMovieId(int userId, int movieId);

    List<MovieCommentsEntity> findMovieCommentsEntitiesByUserId(int userId);
}
