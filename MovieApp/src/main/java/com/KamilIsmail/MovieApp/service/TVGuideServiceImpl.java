package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DTO.GetMovieDTO;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.TVGuideEntity;
import com.KamilIsmail.MovieApp.entities.TvstationsEntity;
import com.KamilIsmail.MovieApp.repository.MovieRepository;
import com.KamilIsmail.MovieApp.repository.TVGuideRepository;
import com.KamilIsmail.MovieApp.repository.TvSatationRepository;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

@Service
public class TVGuideServiceImpl implements TVGuideService {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    TVGuideRepository tvGuideRepository;
    @Autowired
    TvSatationRepository tvSatationRepository;

    @Override
    public ArrayList <GetMovieDTO> getTVGuide() {
        ArrayList <GetMovieDTO> getMovieDTOList = new ArrayList<>();
        List<TVGuideEntity> tvGuideEntitiesList = tvGuideRepository.findAll();
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        for (TVGuideEntity tvGuideEntity : tvGuideEntitiesList) {
            MoviesEntity moviesEntity = movieRepository.findMovieEntityByMovieId(tvGuideEntity.getMovieId());
            MovieDb tmdbResult = tmdbApi.getMovies().getMovie(toIntExact(moviesEntity.getTmdbId()), "pl");
            TvstationsEntity tvstationsEntity = tvSatationRepository.findTvstationsEntityByTvstationId(tvGuideEntity.getTvstationId());
            getMovieDTOList.add(new GetMovieDTO(tmdbResult, tvGuideEntity.getDate().toString(), "",
                    tvstationsEntity.getName(), moviesEntity.getFilmwebId().toString(), String.valueOf(tmdbResult.getVoteAverage())));
        }
        return getMovieDTOList;
    }
}
