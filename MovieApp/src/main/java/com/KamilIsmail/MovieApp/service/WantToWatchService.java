package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverMovieDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.util.List;

public interface WantToWatchService {
    @PreAuthorize("hasAnyAuthority('admin','user')")
    List<DiscoverMovieDTO> getFavourites(int userId) throws IOException;
    @PreAuthorize("hasAnyAuthority('admin','user')")
    BooleanDTO addFavourite(int userId, int movieID);
    @PreAuthorize("hasAnyAuthority('admin','user')")
    BooleanDTO deleteFavourite(int userId, int movieID);
}
