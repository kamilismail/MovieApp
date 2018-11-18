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

/**
 * @author kamilismail
 * Klasa obsługująca komunikację z bazą danych. Zapisuje pozycje do tabeli ocen.
 */
@Service
public class RatingDaoImpl implements RatingDao {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Metoda ustawia ocenę użytkownika dla danego filmu.
     * @param userId
     * @param movieId
     * @param rating
     * @return
     */
    @Override
    public BooleanDTO setRating(int userId, int movieId, int rating) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        MoviesEntity movieEntity = movieRepository.findByTmdbId(movieId);

        //dodanie filmu do tabeli fimów
        if (movieEntity == null) {
            Constants constants = new Constants();
            TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
            MovieDb tmdbResult = tmdbApi.getMovies().getMovie(toIntExact(movieId), Constants.getLanguage());
            FilmwebApi fa = new FilmwebApi();
            FilmSearchResult filmResult = fa.findFilm(tmdbResult.getTitle(), Integer.parseInt(tmdbResult.getReleaseDate().substring(0, 4))).get(0);
            movieEntity = new MoviesEntity(tmdbResult.getTitle(),toIntExact(filmResult.getId()),tmdbResult.getId(),
                    tmdbResult.getPosterPath(), tmdbResult.getReleaseDate(),tmdbResult.getBackdropPath(),
                    tmdbResult.getMediaType().toString(), String.valueOf(tmdbResult.getVoteAverage()),tmdbResult.getOverview());
            movieRepository.save(movieEntity);
        }

        RatingsEntity ratingEntity = ratingRepository.findRatingsEntityByUserByUserIdAndMovieId(userId, movieEntity.getMovieId());

        if (ratingEntity == null) { //utworzenie nowej encji
            RatingsEntity newRating = new RatingsEntity(userId, movieId, Integer.toString(rating), userEntity, movieEntity);
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
