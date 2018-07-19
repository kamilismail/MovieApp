package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.GetMovieDTO;
import com.KamilIsmail.MovieApp.DTO.GetSeriesDTO;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.people.PersonPeople;
import info.movito.themoviedbapi.model.tv.TvSeries;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;

public interface SearchService {
    @PreAuthorize("hasAnyAuthority('admin','user')")
    MovieResultsPage getMovies(String production) throws IOException;

    @PreAuthorize("hasAnyAuthority('admin','user')")
    TvResultsPage getTVShows(String production) throws IOException;

    @PreAuthorize("hasAnyAuthority('admin','user')")
    GetMovieDTO getMovie(Long id) throws IOException;

    @PreAuthorize("hasAnyAuthority('admin','user')")
    GetSeriesDTO getTVShow(Long id) throws IOException;

    @PreAuthorize("hasAnyAuthority('admin','user')")
    TmdbSearch.MultiListResultsPage getProductions(String production) throws IOException;

    @PreAuthorize("hasAnyAuthority('admin','user')")
    PersonPeople getPerson(Long id) throws IOException;
}
