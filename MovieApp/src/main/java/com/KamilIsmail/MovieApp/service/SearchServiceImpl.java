package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DTO.GetMovieDTO;
import com.KamilIsmail.MovieApp.DTO.GetSeriesDTO;
import com.KamilIsmail.MovieApp.DTO.MovieCommentsDTO;
import com.KamilIsmail.MovieApp.entities.MovieCommentsEntity;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.RatingsEntity;
import com.KamilIsmail.MovieApp.entities.TVGuideEntity;
import com.KamilIsmail.MovieApp.repository.*;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.people.PersonPeople;
import info.movito.themoviedbapi.model.tv.TvSeries;
import info.talacha.filmweb.api.FilmwebApi;
import info.talacha.filmweb.connection.FilmwebException;
import info.talacha.filmweb.models.Broadcast;
import info.talacha.filmweb.models.Size;
import info.talacha.filmweb.models.TVChannel;
import info.talacha.filmweb.search.models.FilmSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    WantToWatchRepository wantToWatchRepository;

    @Autowired
    FavouriteRepository favouriteRepository;

    @Autowired
    ReminderRepository reminderRepository;

    @Autowired
    TVGuideRepository tvGuideRepository;

    @Autowired
    TvStationRepository tvStationRepository;

    @Autowired
    MovieCommentsRepository movieCommentsRepository;

    @Cacheable(value = "getMovies")
    @Override
    public MovieResultsPage getMovies(String production) {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        return tmdbApi.getSearch().searchMovie(production, 0, Constants.getLanguage(), false, 0);
    }

    @Cacheable(value = "tvShows")
    @Override
    public TvResultsPage getTVShows(String production) {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        return tmdbApi.getSearch().searchTv(production, Constants.getLanguage(), 0);
    }

    @Override
    public GetMovieDTO getMovie(Long id, Long userID) {
        Constants constants = new Constants();
        TmdbApi tmdbApi;
        MovieDb tmdbResult = null;
        List<Broadcast> broadcasts = null;
        String chanel = "";
        String logoPath = "";
        FilmSearchResult filmResult = null;
        FilmwebApi fa = new FilmwebApi();
        MoviesEntity moviesEntity = movieRepository.findMoviesEntityByTmdbId(id.intValue());

        if (moviesEntity != null) {
            try {
                broadcasts = fa.getBroadcasts(Long.valueOf(moviesEntity.getFilmwebId()), 0, 20);
            } catch (FilmwebException e) {
                e.printStackTrace();
            }
        } else {
            tmdbApi = new TmdbApi(constants.getTmdbAPI());
            tmdbResult = tmdbApi.getMovies().getMovie(toIntExact(id), Constants.getLanguage());
            try {
                filmResult = fa.findFilm(tmdbResult.getTitle(), Integer.parseInt(tmdbResult.getReleaseDate().substring(0, 4))).get(0);
                broadcasts = fa.getBroadcasts(filmResult.getId(), 0, 20);
            } catch (FilmwebException e) {
                e.printStackTrace();
            }
        }
        Broadcast broadcastResult = null;
        try {
            if (!broadcasts.isEmpty()) {
                for (Broadcast broadcast: broadcasts) {
                    if(broadcast.getTime().getHour() >= 18) {
                        broadcastResult = broadcast;
                        break;
                    }
                }
                if (broadcastResult == null)
                    broadcastResult = broadcasts.get(0);
                Long chanelID = broadcastResult.getChannelId();
                List<TVChannel> tvChannels = fa.getTvChannels();
                for (TVChannel tvChannel : tvChannels) {
                    if (tvChannel.getId() == chanelID) {
                        chanel = tvChannel.getName();
                        logoPath = tvChannel.getLogo(Size.SMALL).getPath();
                        break;
                    }
                }
            }
        } catch (FilmwebException e) {
            e.printStackTrace();
        }

        List<RatingsEntity> ratingsEntity = ratingRepository.findRatingsEntityByUserId(userID);
        String ratingResult = "";
        if (ratingsEntity != null) {
            for (RatingsEntity rate : ratingsEntity) {
                if (rate.getMoviesByMovieId().getTmdbId() == toIntExact(id)) {
                    ratingResult = rate.getRating();
                }
            }
        }

        boolean wantToWatch = false, fav = false, reminder = false, userComment = false;
        Integer movieID = null;
        if (moviesEntity != null)
            movieID = moviesEntity.getMovieId();
        ArrayList<MovieCommentsDTO> movieCommentsList = new ArrayList<>();
        if (movieID != null) {
            if (wantToWatchRepository.findWanttowatchEntityByMovieIdAndUserId(movieID, userID.intValue()) != null)
                wantToWatch = true;

            if (favouriteRepository.findFavouritesEntityByUserIdAndMovieId(userID.intValue(), movieID) != null)
                fav = true;

            if(reminderRepository.findRemindersEntityByUserIdAndMovieId(userID.intValue(), movieID) != null)
                reminder = true;

            if(movieCommentsRepository.findMovieCommentsEntityByUserIdAndMovieId(userID.intValue(), movieID) != null)
                userComment = true;

            List<MovieCommentsEntity> movieCommentsEntities = movieCommentsRepository.findMovieCommentsEntitiesByMovieId(movieID);
            for (MovieCommentsEntity movieCommentsEntity : movieCommentsEntities) {
                if (movieCommentsEntity.getUserByUserId().getRole().toLowerCase().equals("facebook"))
                    movieCommentsList.add(new MovieCommentsDTO(movieCommentsEntity.getUserByUserId().getUserSocialByUserSocialId().getUsername(), movieCommentsEntity.getComment().replaceAll("_", " ")));
                else
                    movieCommentsList.add(new MovieCommentsDTO(movieCommentsEntity.getUserByUserId().getUsername(), movieCommentsEntity.getComment().replaceAll("_", " ")));
                }
        }

        if (moviesEntity == null) {
            if (broadcasts.isEmpty()) {
                return (new GetMovieDTO(tmdbResult.getMediaType().toString(), String.valueOf(tmdbResult.getVoteAverage()),
                        tmdbResult.getOverview(), tmdbResult.getBackdropPath(), tmdbResult.getPosterPath(), tmdbResult.getTitle(),
                        tmdbResult.getReleaseDate(), ratingResult, wantToWatch, fav, reminder, String.valueOf(tmdbResult.getId()),
                        movieCommentsList, userComment));
            }
        } else {
            if (broadcasts.isEmpty()) {
                return (new GetMovieDTO(moviesEntity.getMediaType(), moviesEntity.getAvarageRating(), moviesEntity.getOverview(),
                        moviesEntity.getBackdropPath(), moviesEntity.getPosterPath(), moviesEntity.getMovieName(),
                        moviesEntity.getReleaseDate(), ratingResult, wantToWatch, fav, reminder, moviesEntity.getTmdbId().toString(),
                        movieCommentsList, userComment));
            }
        }

        if (moviesEntity == null) {
            return (new GetMovieDTO(tmdbResult.getMediaType().toString(), String.valueOf(tmdbResult.getVoteAverage()),
                    tmdbResult.getOverview(), tmdbResult.getBackdropPath(), tmdbResult.getPosterPath(), tmdbResult.getTitle(),
                    tmdbResult.getReleaseDate(), broadcastResult.getDate().toString(), broadcastResult.getTime().toString(),
                    chanel, String.valueOf(tmdbResult.getId()), ratingResult, wantToWatch, fav, reminder, Constants.getLogoPath() + logoPath,
                    movieCommentsList, userComment));
        } else {
            return (new GetMovieDTO(moviesEntity.getMediaType(), moviesEntity.getAvarageRating(), moviesEntity.getOverview(),
                    moviesEntity.getBackdropPath(), moviesEntity.getPosterPath(), moviesEntity.getMovieName(),
                    moviesEntity.getReleaseDate(), broadcastResult.getDate().toString(), broadcastResult.getTime().toString(),
                    chanel, moviesEntity.getTmdbId().toString(), ratingResult, wantToWatch, fav, reminder, Constants.getLogoPath() + logoPath,
                    movieCommentsList, userComment));
        }
    }

    @Override
    public GetSeriesDTO getTVShow(Long id) {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        TvSeries tmdbResult = tmdbApi.getTvSeries().getSeries(toIntExact(id), Constants.getLanguage());
        FilmwebApi fa = new FilmwebApi();
        List<Broadcast> broadcasts = null;
        String chanel = "";
        FilmSearchResult filmResult = null;
        try {
            filmResult = fa.findSeries(tmdbResult.getName()).get(0);
            broadcasts = fa.getBroadcasts(filmResult.getId(), 0, 20);
            if (!broadcasts.isEmpty()) {
                Long chanelID = broadcasts.get(0).getChannelId();
                List<TVChannel> tvChannels = fa.getTvChannels();
                for (TVChannel tvChannel : tvChannels) {
                    if (tvChannel.getId() == chanelID) {
                        chanel = tvChannel.getName();
                        break;
                    }
                }
            }
        } catch (FilmwebException e) {
            e.printStackTrace();
        }
        if (broadcasts.isEmpty()) {
            return (new GetSeriesDTO(tmdbResult));
        }
        return (new GetSeriesDTO(tmdbResult, broadcasts.get(0).getDate().toString(), broadcasts.get(0).getTime().toString(),
                chanel, broadcasts.get(0).getDescription(), filmResult.getId().toString()));
    }

    @Override
    public TmdbSearch.MultiListResultsPage getProductions(String production) throws IOException {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        return tmdbApi.getSearch().searchMulti(production, Constants.getLanguage(), 0);
    }

    @Override
    public PersonPeople getPerson(Long id) throws IOException {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        return tmdbApi.getPeople().getPersonInfo(toIntExact(id));
    }
}
