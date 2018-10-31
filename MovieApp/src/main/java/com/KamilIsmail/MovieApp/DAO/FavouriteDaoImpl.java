package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.entities.FavouritesEntity;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.repository.FavouriteRepository;
import com.KamilIsmail.MovieApp.repository.MovieRepository;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import info.talacha.filmweb.api.FilmwebApi;
import info.talacha.filmweb.search.models.FilmSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

@Service
public class FavouriteDaoImpl implements FavouriteDao {

    @Autowired
    FavouriteRepository favRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public BooleanDTO addFavourite(int userId, int movieId) {
        MoviesEntity movieEntity = movieRepository.findByTmdbId(movieId);
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (movieEntity == null) {
            Constants constants = new Constants();
            TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
            MovieDb tmdbResult = tmdbApi.getMovies().getMovie(toIntExact(movieId), Constants.getLanguage());
            FilmwebApi fa = new FilmwebApi();
            FilmSearchResult filmResult = fa.findFilm(tmdbResult.getTitle(), Integer.parseInt(tmdbResult.getReleaseDate().substring(0, 4))).get(0);
            movieEntity = new MoviesEntity(tmdbResult.getTitle(),toIntExact(filmResult.getId()),tmdbResult.getId(),
                    tmdbResult.getPosterPath(), tmdbResult.getReleaseDate(),tmdbResult.getBackdropPath(),
                    tmdbResult.getMediaType().toString(), String.valueOf(tmdbResult.getVoteAverage()),tmdbResult.getOverview());
            movieEntity.setOverview(tmdbResult.getOverview());
            movieRepository.save(movieEntity);
        }
        FavouritesEntity favEntity = new FavouritesEntity(userEntity.getUserId(), movieEntity.getMovieId(), userEntity, movieEntity);
        favRepository.save(favEntity);
        movieEntity.setFavouritesByMovieId(favRepository.findFavouritesEntityByUserId(userId));
        movieRepository.save(movieEntity);
        userEntity.setFavouritesByUserId(favRepository.findFavouritesEntityByUserId(userId));
        userRepository.save(userEntity);

        return (new BooleanDTO(true));
    }

    @Override
    public BooleanDTO deleteFavourite(int userId, int movieId) {
        FavouritesEntity favouritesEntity = favRepository.findFavouritesEntityByUserIdAndMovieId(userId, movieId);
        if (favouritesEntity != null) {
            favRepository.delete(favouritesEntity);
            return new BooleanDTO(true);
        } else
            return new BooleanDTO(false);
    }
}
