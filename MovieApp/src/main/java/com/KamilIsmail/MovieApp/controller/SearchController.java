package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.service.SearchService;
import com.oracle.tools.packager.Log;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("search/")
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("movies")
    public MovieResultsPage getMovies(@RequestParam("name") String production) throws IOException {
        return searchService.getMovies(production.replaceAll("_"," "));
    }

    @GetMapping("tvshows")
    public TvResultsPage getTVShows(@RequestParam("name") String production) throws IOException {
        /*
        FilmwebApi fa = new FilmwebApi();
        try {
            List<Broadcast> broadcasts = fa.getBroadcasts((long) 450178, 0, 20); //jakis id filmu filmwebowy
            Log.debug(broadcasts.get(0).getTime().toString());
        } catch (FilmwebException e) {
            e.printStackTrace();
        }
*/
        return searchService.getTVShows(production.replaceAll("_"," "));
    }

    @GetMapping("movie")
    public MovieDb getMovie(@RequestParam("id") Long id) throws IOException {
        return searchService.getMovie(id);
    }

    @GetMapping("tvshow")
    public TvSeries getTVShow(@RequestParam("id") Long id) throws IOException {
        return searchService.getTVShow(id);
    }

    @GetMapping("productions")
    public TmdbSearch.MultiListResultsPage getProductions(@RequestParam("name") String production) throws IOException {
        return searchService.getProductions(production.replaceAll("_"," "));
    }

    @GetMapping("person")
    public PersonPeople getPerson(@RequestParam("id") Long id) throws IOException {
        return searchService.getPerson(id);
    }
}