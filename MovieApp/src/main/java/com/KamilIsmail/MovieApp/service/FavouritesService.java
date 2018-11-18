package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverMovieDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.util.List;

/**
 * @author kamilismail
 */
public interface FavouritesService {
    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    List<DiscoverMovieDTO> getFavourites(int userId) throws IOException;

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    BooleanDTO addFavourite(int userId, int movieID);

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    BooleanDTO deleteFavourite(int userId, int movieID);
}
