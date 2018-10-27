package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.DTO.TVGuideMovieDTO;
import com.KamilIsmail.MovieApp.service.TVGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping("tvguide/")
public class TVGuideController {

    @Autowired
    TVGuideService tvGuideService;

    @GetMapping("")
    public ArrayList<TVGuideMovieDTO> getTVGuide(Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        return tvGuideService.getTVGuide();
    }
}
