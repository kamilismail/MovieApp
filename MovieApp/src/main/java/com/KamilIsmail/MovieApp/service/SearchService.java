package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.GetMovieDTO;
import com.KamilIsmail.MovieApp.DTO.GetSeriesDTO;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.people.PersonPeople;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;

public interface SearchService {
    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    MovieResultsPage getMovies(String production) throws IOException;

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    TvResultsPage getTVShows(String production) throws IOException;

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    GetMovieDTO getMovie(Long id, Long userID) throws IOException;

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    GetSeriesDTO getTVShow(Long id) throws IOException;

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    TmdbSearch.MultiListResultsPage getProductions(String production) throws IOException;

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    PersonPeople getPerson(Long id) throws IOException;
}
