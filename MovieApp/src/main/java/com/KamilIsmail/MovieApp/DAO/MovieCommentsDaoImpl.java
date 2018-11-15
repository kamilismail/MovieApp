package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.MovieCommentsDTO;
import com.KamilIsmail.MovieApp.entities.MovieCommentsEntity;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.repository.MovieCommentsRepository;
import com.KamilIsmail.MovieApp.repository.MovieRepository;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import info.talacha.filmweb.api.FilmwebApi;
import info.talacha.filmweb.search.models.FilmSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Math.toIntExact;

@Service
public class MovieCommentsDaoImpl implements MovieCommentsDao {

    @Autowired
    MovieCommentsRepository movieCommentsRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<MovieCommentsDTO> getComments(int movieId) {
        MoviesEntity movieEntity = movieRepository.findByTmdbId(movieId);
        ArrayList<MovieCommentsDTO> movieCommentsDTOS = new ArrayList<>();
        if (movieEntity == null)
            return movieCommentsDTOS;
        List<MovieCommentsEntity> movieCommentsEntities = movieCommentsRepository.findMovieCommentsEntitiesByMovieId(movieEntity.getMovieId());
        for (MovieCommentsEntity movieCommentsEntity : movieCommentsEntities)
            movieCommentsDTOS.add(new MovieCommentsDTO(movieCommentsEntity.getUserByUserId().getUsername(), movieCommentsEntity.getComment()));
        return movieCommentsDTOS;
    }

    @Override
    public BooleanDTO addComment(int userId, String comment, int movieId) {
        MoviesEntity movieEntity = movieRepository.findByTmdbId(movieId);
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
        MovieCommentsEntity movieCommentsEntity = new MovieCommentsEntity(movieId, userId, comment);
        movieCommentsEntity.setMoviesByMovieId(movieRepository.findByMovieId(movieId));
        movieCommentsEntity.setUserByUserId(userRepository.findByUserId(userId));
        movieCommentsRepository.save(movieCommentsEntity);
        return new BooleanDTO(true);
    }

    @Override
    public BooleanDTO deleteComment(int userid, int movieId) {
        movieCommentsRepository.delete(movieCommentsRepository.findMovieCommentsEntityByUserIdAndMovieId(userid,movieId));
        return new BooleanDTO(true);
    }
}
