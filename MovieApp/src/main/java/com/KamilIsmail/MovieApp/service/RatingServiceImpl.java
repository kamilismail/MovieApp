package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.Constants;
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
import java.util.stream.Collectors;

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
        return ratingRepository.findRatingsEntityByUserId(userID).stream()
                .map(p -> new DiscoverMovieDTO(p.getMoviesByMovieId().getMediaType(),
                    Integer.toString(p.getMoviesByMovieId().getTmdbId()), p.getMoviesByMovieId().getMovieName(),
                    Constants.getPosterPath() + p.getMoviesByMovieId().getBackdropPath(), p.getMoviesByMovieId().getReleaseDate(),
                    p.getRating()))
                .collect(Collectors.toList());
    }
}
