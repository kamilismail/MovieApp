package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.MovieCommentsDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author kamilismail
 */
public interface MovieCommentsService {

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    List<MovieCommentsDTO> getComments(int movieId);

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    BooleanDTO addComment(int userId, String comment, int movieId);

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    BooleanDTO deleteComment(int userid, int movieId);
}
