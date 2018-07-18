package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.Constants;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.people.PersonPeople;
import info.movito.themoviedbapi.model.tv.TvSeries;
import org.springframework.stereotype.Service;

import java.io.IOException;
import static java.lang.Math.toIntExact;

@Service
public class SearchServiceImpl implements SearchService {

    @Override
    public MovieResultsPage getMovies(String production) throws IOException {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        return tmdbApi.getSearch().searchMovie(production,0,"pl",false,0);
    }

    @Override
    public TvResultsPage getTVShows(String production) throws IOException {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        return tmdbApi.getSearch().searchTv(production,"pl",0);
    }

    @Override
    public MovieDb getMovie(Long id) throws IOException {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        return tmdbApi.getMovies().getMovie(toIntExact(id),"pl");
    }

    @Override
    public TvSeries getTVShow(Long id) throws IOException {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        return tmdbApi.getTvSeries().getSeries(toIntExact(id),"pl");
    }

    @Override
    public TmdbSearch.MultiListResultsPage getProductions(String production) throws IOException {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        return tmdbApi.getSearch().searchMulti(production,"pl",0);
    }

    @Override
    public PersonPeople getPerson(Long id) throws IOException {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        return tmdbApi.getPeople().getPersonInfo(toIntExact(id));
    }
}
