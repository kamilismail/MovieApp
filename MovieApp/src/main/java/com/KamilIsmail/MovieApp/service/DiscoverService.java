package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.DiscoverDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;

/**
 * @author kamilismail
 */
public interface DiscoverService {

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    DiscoverDTO getJSON() throws IOException;
}
