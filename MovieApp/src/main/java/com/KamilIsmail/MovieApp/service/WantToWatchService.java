package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverMovieDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.util.List;

/**
 * @author kamilismail
 */
public interface WantToWatchService {
    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    List<DiscoverMovieDTO> getWants(int userId) throws IOException;

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    BooleanDTO addWant(int userId, int movieID);

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    BooleanDTO deleteWant(int userId, int movieID);
}
