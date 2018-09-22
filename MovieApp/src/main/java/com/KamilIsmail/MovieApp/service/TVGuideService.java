package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.GetMovieDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.util.ArrayList;

public interface TVGuideService {
    @PreAuthorize("hasAnyAuthority('admin','user')")
    ArrayList<GetMovieDTO> getTVGuide() throws IOException;
}
