package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.DiscoverDTO;
import com.KamilIsmail.MovieApp.DTO.recommender.RecommenderDTO;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.util.List;

/**
 * @author kamilismail
 */
public interface DiscoverService {

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    DiscoverDTO getJSON() throws IOException;

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    List<RecommenderDTO> getBestForUser(int userID);
}
