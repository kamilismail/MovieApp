package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.DiscoverDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;

public interface DiscoverService {

    @PreAuthorize("hasAnyAuthority('admin','user')")
    DiscoverDTO getJSON() throws IOException;
}
