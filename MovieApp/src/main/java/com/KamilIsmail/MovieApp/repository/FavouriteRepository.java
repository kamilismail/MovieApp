package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.FavouritesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<FavouritesEntity, Long> {

    List<FavouritesEntity> findFavouritesEntityByUserId(int userid);
    List<FavouritesEntity> findByMovieId(int movieId);
}
