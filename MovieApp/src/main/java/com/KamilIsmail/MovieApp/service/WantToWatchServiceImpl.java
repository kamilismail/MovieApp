package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DAO.WantToWatchDao;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverMovieDTO;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.WanttowatchEntity;
import com.KamilIsmail.MovieApp.repository.MovieRepository;
import com.KamilIsmail.MovieApp.repository.WantToWatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WantToWatchServiceImpl implements WantToWatchService {
    @Autowired
    WantToWatchRepository wantRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    WantToWatchDao wantDao;

    @Override
    public List<DiscoverMovieDTO> getWants(int userid) {
        List<WanttowatchEntity> wantEntitiesList = wantRepository.findWanttowatchEntityByUserId(userid);
        List<DiscoverMovieDTO> wantResults = new ArrayList<>();
        for (WanttowatchEntity wantList : wantEntitiesList) {
            MoviesEntity moviesEntity = wantList.getMoviesByMovieId();
            DiscoverMovieDTO result = new DiscoverMovieDTO(moviesEntity.getMediaType(), Integer.toString(moviesEntity.getTmdbId()),
                    moviesEntity.getMovieName(), Constants.getPosterPath() + moviesEntity.getBackdropPath(),
                    moviesEntity.getReleaseDate(), moviesEntity.getAvarageRating());
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
