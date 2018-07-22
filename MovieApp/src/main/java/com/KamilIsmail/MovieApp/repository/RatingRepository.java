package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.FavouritesEntity;
import com.KamilIsmail.MovieApp.entities.RatingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<RatingsEntity, Long> {
    RatingsEntity findRatingsEntityByUserId(long userId);
}
