package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.TVGuideMovieDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author kamilismail
 */
public interface TVGuideService {
    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    ArrayList<TVGuideMovieDTO> getTVGuide() throws IOException;
}
