package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DAO.WantToWatchDao;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverMovieDTO;
import com.KamilIsmail.MovieApp.entities.WanttowatchEntity;
import com.KamilIsmail.MovieApp.repository.WantToWatchRepository;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

@Service
public class WantToWatchServiceImpl implements WantToWatchService {
    @Autowired
    WantToWatchRepository wantRepository;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    WantToWatchDao wantDao;

    @Override
    public List<DiscoverMovieDTO> getWants(int userid) throws IOException {
        List<WanttowatchEntity> wantEntitiesList = wantRepository.findWanttowatchEntityByUserId(userid);
        List<DiscoverMovieDTO> wantResults = new ArrayList<>();
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        for (WanttowatchEntity wantList : wantEntitiesList) {
            MovieDb tmdbResult = tmdbApi.getMovies().getMovie(toIntExact(wantList.getMoviesByMovieId().getTmdbId()), "pl");
            DiscoverMovieDTO result = new DiscoverMovieDTO("MOVIE", Integer.toString(wantList.getMoviesByMovieId().getTmdbId()),
                    wantList.getMoviesByMovieId().getMovieName(), Constants.getPosterPath() + wantList.getMoviesByMovieId().getPosterPath(),
                    wantList.getMoviesByMovieId().getReleaseDate(), String.valueOf(tmdbResult.getVoteAverage()));
            wantResults.add(result);
        }
        return wantResults;
    }

    @Override
    public BooleanDTO addWant(int userid, int movieId) {
        try {
            List<WanttowatchEntity> favsEntitiesList = wantRepository.findWanttowatchEntityByUserId(userid);
            for (WanttowatchEntity favList : favsEntitiesList) {
                if (favList.getMoviesByMovieId().getMovieId() == movieId)
                    return new BooleanDTO(false);
            }
            return wantDao.addFavourite(userid, movieId);

        } catch (Exception e) {
            return new BooleanDTO(false);
        }
    }

    @Override
    public BooleanDTO deleteWant(int userid, int movieId) {
        return wantDao.deleteFavourite(userid, movieId);
    }
}
