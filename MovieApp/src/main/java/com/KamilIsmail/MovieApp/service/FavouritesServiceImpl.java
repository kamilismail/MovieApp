package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DAO.FavouriteDao;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverMovieDTO;
import com.KamilIsmail.MovieApp.entities.FavouritesEntity;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.repository.FavouriteRepository;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

@Service
public class FavouritesServiceImpl implements FavouritesService {
    @Autowired
    FavouriteRepository favsRepository;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    FavouriteDao favsDao;

    @Cacheable(value = "favourites", key = "#userid")
    @Override
    public List<DiscoverMovieDTO> getFavourites(int userid) {
        List<FavouritesEntity> favsEntitiesList = favsRepository.findFavouritesEntityByUserId(userid);
        List<DiscoverMovieDTO> favResults = new ArrayList<>();
        for (FavouritesEntity favList : favsEntitiesList) {
            MoviesEntity moviesEntity = favList.getMoviesByMovieId();
            DiscoverMovieDTO result = new DiscoverMovieDTO(moviesEntity.getMediaType(), Integer.toString(moviesEntity.getTmdbId()),
                    moviesEntity.getMovieName(), Constants.getPosterPath() + moviesEntity.getBackdropPath(),
                    moviesEntity.getReleaseDate(), String.valueOf(moviesEntity.getAvarageRating()));
            favResults.add(result);
        }
        return favResults;
    }

    @Override
    public BooleanDTO addFavourite(int userid, int movieId) {
        try {
            List<FavouritesEntity> favsEntitiesList = favsRepository.findFavouritesEntityByUserId(userid);
            for (FavouritesEntity favList : favsEntitiesList) {
                if (favList.getMoviesByMovieId().getTmdbId() == movieId)
                    return new BooleanDTO(false);
            }
            return favsDao.addFavourite(userid, movieId);

        } catch (Exception e) {
            return new BooleanDTO(false);
        }
    }

    @Override
    public BooleanDTO deleteFavourite(int userid, int movieId) {
        return favsDao.deleteFavourite(userid, movieId);
    }

}
