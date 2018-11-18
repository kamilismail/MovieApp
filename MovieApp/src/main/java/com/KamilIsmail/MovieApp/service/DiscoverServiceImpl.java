package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DTO.DiscoverDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverMovieDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverSeriesDTO;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.tv.TvSeries;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kamilismail
 * Serwis wywoływany z kontrolera.
 */
@Service
public class DiscoverServiceImpl implements DiscoverService {

    /**
     * Metoda zwracajaca w jsonie liste popularnych filmow, popularnych seriali, aktualnie granych filmow w kinach oraz
     * tych nadchodzacych.
     *
     * @return
     */
    @Cacheable(value = "discover")
    @Override
    public DiscoverDTO getJSON() {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());

        List<DiscoverMovieDTO> upcomingList = tmdbApi.getMovies().getUpcoming(Constants.getLanguage(), 1, "")
                .getResults().stream().map(p -> new DiscoverMovieDTO(p.getMediaType().toString(), Integer.toString(p.getId()),
                        p.getTitle(), Constants.getPosterPath() + p.getBackdropPath(), p.getReleaseDate(),
                        String.valueOf(p.getVoteAverage())))
                .collect(Collectors.toList());

        List<DiscoverMovieDTO> nowPlayingList = tmdbApi.getMovies().getNowPlayingMovies(Constants.getLanguage(), 1, "")
                .getResults().stream().map(p -> new DiscoverMovieDTO(p.getMediaType().toString(), Integer.toString(p.getId()),
                        p.getTitle(), Constants.getPosterPath() + p.getBackdropPath(), p.getReleaseDate(),
                        String.valueOf(p.getVoteAverage())))
                .collect(Collectors.toList());

        List<DiscoverMovieDTO> popularMoviesList = tmdbApi.getMovies().getPopularMovies(Constants.getLanguage(), 1)
                .getResults().stream().map(p -> new DiscoverMovieDTO(p.getMediaType().toString(), Integer.toString(p.getId()),
                        p.getTitle(), Constants.getPosterPath() + p.getBackdropPath(),
                        p.getReleaseDate(), String.valueOf(p.getVoteAverage())))
                .collect(Collectors.toList());


        List<DiscoverSeriesDTO> popularSeriesList =  tmdbApi.getTvSeries().getPopular(Constants.getLanguage(), 1)
                .getResults().stream().map(p -> new DiscoverSeriesDTO(Integer.toString(p.getId()), p.getName(),
                        Constants.getPosterPath() + p.getBackdropPath(), String.valueOf(p.getVoteAverage()),
                        p.getFirstAirDate()))
                .collect(Collectors.toList());

        return (new DiscoverDTO(upcomingList, nowPlayingList, popularMoviesList, popularSeriesList));
    }
}
