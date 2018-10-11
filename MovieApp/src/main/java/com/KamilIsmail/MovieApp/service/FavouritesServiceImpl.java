package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DAO.FavouriteDao;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverMovieDTO;
import com.KamilIsmail.MovieApp.entities.FavouritesEntity;
import com.KamilIsmail.MovieApp.repository.FavouriteRepository;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<DiscoverMovieDTO> getFavourites(int userid) throws IOException {
        List<FavouritesEntity> favsEntitiesList = favsRepository.findFavouritesEntityByUserId(userid);
        List<DiscoverMovieDTO> favResults = new ArrayList<>();
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        for (FavouritesEntity favList : favsEntitiesList) {
            MovieDb tmdbResult = tmdbApi.getMovies().getMovie(toIntExact(favList.getMoviesByMovieId().getTmdbId()), "pl");
            DiscoverMovieDTO result = new DiscoverMovieDTO("MOVIE", Integer.toString(favList.getMoviesByMovieId().getTmdbId()),
                    favList.getMoviesByMovieId().getMovieName(), favList.getMoviesByMovieId().getPosterPath(),
                    favList.getMoviesByMovieId().getReleaseDate(), String.valueOf(tmdbResult.getVoteAverage()));
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
