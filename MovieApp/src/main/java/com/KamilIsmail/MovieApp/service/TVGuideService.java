package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.TVGuideMovieDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.util.ArrayList;

public interface TVGuideService {
    @PreAuthorize("hasAnyAuthority('admin','user')")
    ArrayList<TVGuideMovieDTO> getTVGuide() throws IOException;
}
