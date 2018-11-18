package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.FavouritesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author kamilismail
 * Zapytania sql z tabelą favourites.
 */
public interface FavouriteRepository extends JpaRepository<FavouritesEntity, Long> {

    /**
     * Pobranie listy ulubionych dla danego użytkownika.
     * @param userid
     * @return
     */
    List<FavouritesEntity> findFavouritesEntityByUserId(int userid);

    /**
     * Pobranie listy ulubionych dla danego filmu.
     * @param movieId
     * @return
     */
    List<FavouritesEntity> findByMovieId(int movieId);

    /**
     * Pobranie pozycji z tabeli ulubionych ze względu na użytkownika u pozycję filmową.
     * @param userid
     * @param movieid
     * @return
     */
    FavouritesEntity findFavouritesEntityByUserIdAndMovieId(int userid, int movieid);
}
