package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverMovieDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.util.List;

public interface RatingService {
    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    BooleanDTO setRating(int userID, int movieID, int rating) throws IOException;

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    List<DiscoverMovieDTO> getRatings(int userID) throws IOException;
}
