package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.RatingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<RatingsEntity, Long> {
    List<RatingsEntity> findRatingsEntityByUserId(long userId);

    RatingsEntity findRatingsEntityByMovieId(int movieId);

    RatingsEntity findRatingsEntityByUserByUserIdAndMovieId(long userId, int movieId);
}
