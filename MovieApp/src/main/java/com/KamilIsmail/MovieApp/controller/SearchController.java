package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.DTO.GetMovieDTO;
import com.KamilIsmail.MovieApp.DTO.GetSeriesDTO;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import com.KamilIsmail.MovieApp.service.SearchService;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.people.PersonPeople;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("search/")
public class SearchController {

    @Autowired
    SearchService searchService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("movies")
    public MovieResultsPage getMovies(@RequestParam("name") String production, Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        return searchService.getMovies(production.replaceAll("_", " "));
    }

    @GetMapping("tvshows")
    public TvResultsPage getTVShows(@RequestParam("name") String production, Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        return searchService.getTVShows(production.replaceAll("_", " "));
    }

    @GetMapping("movie")
    public GetMovieDTO getMovie(@RequestParam("id") Long id, Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return searchService.getMovie(id, (long) userEntity.getUserId());
    }

    @GetMapping("tvshow")
    public GetSeriesDTO getTVShow(@RequestParam("id") Long id, Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        return searchService.getTVShow(id);
    }

    @GetMapping("productions")
    public TmdbSearch.MultiListResultsPage getProductions(@RequestParam("name") String production, Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        return searchService.getProductions(production.replaceAll("_", " "));
    }

    @GetMapping("person")
    public PersonPeople getPerson(@RequestParam("id") Long id, Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        return searchService.getPerson(id);
    }
}