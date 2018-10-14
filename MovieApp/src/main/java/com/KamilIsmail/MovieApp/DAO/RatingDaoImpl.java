package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.RatingsEntity;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.repository.MovieRepository;
import com.KamilIsmail.MovieApp.repository.RatingRepository;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import info.talacha.filmweb.api.FilmwebApi;
import info.talacha.filmweb.search.models.FilmSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.toIntExact;

@Service
public class RatingDaoImpl implements RatingDao {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public BooleanDTO setRating(int userId, int movieId, int rating) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        MoviesEntity movieEntity = movieRepository.findByTmdbId(movieId);

        //dodanie filmu do tabeli fimów
        if (movieEntity == null) {
            Constants constants = new Constants();
            TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
            MovieDb tmdbResult = tmdbApi.getMovies().getMovie(toIntExact(movieId), "pl");
            FilmwebApi fa = new FilmwebApi();
            FilmSearchResult filmResult = fa.findFilm(tmdbResult.getTitle(), Integer.parseInt(tmdbResult.getReleaseDate().substring(0, 4))).get(0);
            movieEntity = new MoviesEntity();
            movieEntity.setMovieName(tmdbResult.getTitle());
            movieEntity.setTmdbId(tmdbResult.getId());
            movieEntity.setFilmwebId(toIntExact(filmResult.getId()));
            movieEntity.setPosterPath(tmdbResult.getPosterPath());
            movieEntity.setReleaseDate(tmdbResult.getReleaseDate());
            movieEntity.setBackdropPath(tmdbResult.getBackdropPath());
            movieEntity.setMediaType(tmdbResult.getMediaType().toString());
            movieEntity.setAvarageRating(String.valueOf(tmdbResult.getVoteAverage()));
            movieEntity.setOverview(tmdbResult.getOverview());
            movieRepository.save(movieEntity);
        }

        List<RatingsEntity> ratingsEntityList = ratingRepository.findRatingsEntityByUserId(userId);
        RatingsEntity ratingEntity = null;
        for (RatingsEntity ratingList : ratingsEntityList) {
            if (ratingList.getMoviesByMovieId().getTmdbId() == movieId) {
                ratingEntity = ratingList;
            }
        }

        if (ratingEntity == null) { //utworzenie nowej encji
            RatingsEntity newRating = new RatingsEntity();
            newRating.setRating(Integer.toString(rating));
            newRating.setMovieId(movieId);
            newRating.setUserId(userId);
            newRating.setMoviesByMovieId(movieEntity);
            newRating.setUserByUserId(userEntity);
            ratingRepository.save(newRating);

            movieEntity.setRatingsByMovieId(ratingRepository.findRatingsEntityByUserId(userId));
            movieRepository.save(movieEntity);

            userEntity.setRatingsByUserId(ratingRepository.findRatingsEntityByUserId(userId));
            userRepository.save(userEntity);
        } else {
            ratingEntity.setRating(Integer.toString(rating));
            ratingRepository.save(ratingEntity);
        }
        return new BooleanDTO(true);
    }
}
