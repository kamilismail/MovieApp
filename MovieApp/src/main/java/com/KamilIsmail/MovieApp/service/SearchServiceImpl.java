package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DTO.GetMovieDTO;
import com.KamilIsmail.MovieApp.DTO.GetSeriesDTO;
import com.KamilIsmail.MovieApp.entities.RatingsEntity;
import com.KamilIsmail.MovieApp.entities.WanttowatchEntity;
import com.KamilIsmail.MovieApp.repository.FavouriteRepository;
import com.KamilIsmail.MovieApp.repository.MovieRepository;
import com.KamilIsmail.MovieApp.repository.RatingRepository;
import com.KamilIsmail.MovieApp.repository.WantToWatchRepository;
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
import info.talacha.filmweb.models.TVChannel;
import info.talacha.filmweb.search.models.FilmSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Override
    public MovieResultsPage getMovies(String production) throws IOException {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        return tmdbApi.getSearch().searchMovie(production, 0, "pl", false, 0);
    }

    @Override
    public TvResultsPage getTVShows(String production) throws IOException {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        return tmdbApi.getSearch().searchTv(production, "pl", 0);
    }

    @Override
    public GetMovieDTO getMovie(Long id, Long userID) throws IOException {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        MovieDb tmdbResult = tmdbApi.getMovies().getMovie(toIntExact(id), "pl");
        FilmwebApi fa = new FilmwebApi();
        List<Broadcast> broadcasts = null;
        String chanel = "";
        FilmSearchResult filmResult = null;
        try {
            filmResult = fa.findFilm(tmdbResult.getTitle(), Integer.parseInt(tmdbResult.getReleaseDate().substring(0, 4))).get(0);
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

        List<RatingsEntity> ratingsEntity = ratingRepository.findRatingsEntityByUserId(userID);
        String ratingResult = "";
        if (ratingsEntity != null) {
            for (RatingsEntity rate : ratingsEntity) {
                if (rate.getMoviesByMovieId().getTmdbId() == toIntExact(id)) {
                    ratingResult = rate.getRating();
                }
            }
        }

        boolean wantToWatch, fav;
        int movieID = movieRepository.findByTmdbId(id.intValue()).getMovieId();
        if (wantToWatchRepository.findWanttowatchEntityByMovieIdAndUserId(movieID, userID.intValue()) != null)
            wantToWatch = true;
        else
            wantToWatch = false;

        if (favouriteRepository.findFavouritesEntityByUserIdAndAndMovieId(movieID, id.intValue()) != null)
            fav = true;
        else
            fav = false;

        if (broadcasts.isEmpty()) {
            return (new GetMovieDTO(tmdbResult, ratingResult, wantToWatch, fav));
        }

        return (new GetMovieDTO(tmdbResult, broadcasts.get(0).getDate().toString(), broadcasts.get(0).getTime().toString(),
                chanel, filmResult.getId().toString(), ratingResult, wantToWatch, fav));
    }

    @Override
    public GetSeriesDTO getTVShow(Long id) throws IOException {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        TvSeries tmdbResult = tmdbApi.getTvSeries().getSeries(toIntExact(id), "pl");
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
        return tmdbApi.getSearch().searchMulti(production, "pl", 0);
    }

    @Override
    public PersonPeople getPerson(Long id) throws IOException {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        return tmdbApi.getPeople().getPersonInfo(toIntExact(id));
    }
}
