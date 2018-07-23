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

@Service
public class WantToWatchDaoImpl implements WantToWatchDao {
    @Autowired
    WantToWatchRepository wantRepository;

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
            MovieDb tmdbResult = tmdbApi.getMovies().getMovie(toIntExact(movieId), "pl");
            FilmwebApi fa = new FilmwebApi();
            FilmSearchResult filmResult = fa.findFilm(tmdbResult.getTitle(), Integer.parseInt(tmdbResult.getReleaseDate().substring(0, 4))).get(0);
            movieEntity = new MoviesEntity();
            movieEntity.setMovieName(tmdbResult.getTitle());
            movieEntity.setTmdbId(tmdbResult.getId());
            movieEntity.setFilmwebId(toIntExact(filmResult.getId()));
            movieEntity.setPosterPath(tmdbResult.getPosterPath());
            movieEntity.setReleaseDate(tmdbResult.getReleaseDate());
            movieRepository.save(movieEntity);
        }

        WanttowatchEntity wantEntity = new WanttowatchEntity();
        wantEntity.setMovieId(movieEntity.getMovieId());
        wantEntity.setUserId(userEntity.getUserId());
        wantEntity.setMoviesByMovieId(movieEntity);
        wantEntity.setUserByUserId(userEntity);
        wantRepository.save(wantEntity);

        movieEntity.setWanttowatchesByMovieId(wantRepository.findWanttowatchEntityByUserId(userId));
        movieRepository.save(movieEntity);

        userEntity.setWanttowatchesByUserId(wantRepository.findWanttowatchEntityByUserId(userId));
        userRepository.save(userEntity);

        return (new BooleanDTO(true));
    }

    @Override
    public BooleanDTO deleteFavourite(int userId, int pubId) {
        List<WanttowatchEntity> favsEntitiesList = wantRepository.findWanttowatchEntityByUserId(userId);
        for (WanttowatchEntity favList : favsEntitiesList) {
            if (favList.getMoviesByMovieId().getTmdbId() == pubId) {
                wantRepository.delete(favList);
                return new BooleanDTO(true);
            }
        }
        return new BooleanDTO(false);
    }
}
