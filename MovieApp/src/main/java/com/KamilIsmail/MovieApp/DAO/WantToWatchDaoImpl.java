package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.entities.WanttowatchEntity;
import com.KamilIsmail.MovieApp.repository.MovieRepository;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import com.KamilIsmail.MovieApp.repository.WantToWatchRepository;
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
 * Klasa odpowiadjąca za komunikację z tabelą "want to watch".
 */
@Service
public class WantToWatchDaoImpl implements WantToWatchDao {
    @Autowired
    WantToWatchRepository wantRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Metoda dodająca nową pozycję do tabeli.
     * @param userId
     * @param movieId
     * @return
     */
    @Override
    public BooleanDTO addWantToWatch(int userId, int movieId) {
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
            movieEntity.setMovieName(tmdbResult.getTitle());
            movieRepository.save(movieEntity);
        }

        WanttowatchEntity wantEntity = new WanttowatchEntity(userEntity.getUserId(),movieEntity.getMovieId(),
                userEntity, movieEntity);
        wantRepository.save(wantEntity);

        movieEntity.setWanttowatchesByMovieId(wantRepository.findWanttowatchEntityByUserId(userId));
        movieRepository.save(movieEntity);

        userEntity.setWanttowatchesByUserId(wantRepository.findWanttowatchEntityByUserId(userId));
        userRepository.save(userEntity);

        return (new BooleanDTO(true));
    }

    /**
     * Metoda usuwająca daną pozycję z tabeli.
     * @param userId
     * @param movieId
     * @return
     */
    @Override
    public BooleanDTO deleteWantToWatch(int userId, int movieId) {
        MoviesEntity movieEntity = movieRepository.findByTmdbId(movieId);
        WanttowatchEntity wanttowatchEntity;
        if (movieEntity != null) {
            wanttowatchEntity = wantRepository.findWanttowatchEntityByMovieIdAndUserId(movieEntity.getMovieId(), userId);
        } else return new BooleanDTO(false);
        if (wanttowatchEntity != null) {
            wantRepository.delete(wanttowatchEntity);
            return new BooleanDTO(true);
        } else
            return new BooleanDTO(false);
    }
}
