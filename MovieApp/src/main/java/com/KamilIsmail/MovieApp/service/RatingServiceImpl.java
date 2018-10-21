package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DAO.RatingDao;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverMovieDTO;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.RatingsEntity;
import com.KamilIsmail.MovieApp.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingDao ratingDao;

    @Autowired
    RatingRepository ratingRepository;

    @Override
    public BooleanDTO setRating(int userID, int movieID, int rating) {
        return ratingDao.setRating(userID, movieID, rating);
    }

    @Override
    public List<DiscoverMovieDTO> getRatings(int userID) {

        List<RatingsEntity> favsEntitiesList = ratingRepository.findRatingsEntityByUserId(userID);
        List<DiscoverMovieDTO> ratingResults = new ArrayList<>();

        for (RatingsEntity rateList : favsEntitiesList) {
            MoviesEntity moviesEntity = rateList.getMoviesByMovieId();
            DiscoverMovieDTO result = new DiscoverMovieDTO(moviesEntity.getMediaType(), Integer.toString(moviesEntity.getTmdbId()),
                    moviesEntity.getMovieName(), moviesEntity.getBackdropPath(), moviesEntity.getReleaseDate(), rateList.getRating());

            ratingResults.add(result);
        }
        return ratingResults;
    }
}
