package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.DTO.GetMovieDTO;
import com.KamilIsmail.MovieApp.DTO.TVGuideMovieDTO;
import com.KamilIsmail.MovieApp.service.TVGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("tvguide/")
public class TVGuideController {

    @Autowired
    TVGuideService tvGuideService;

    @GetMapping("")
    public ArrayList<TVGuideMovieDTO> getTVGuide() throws IOException {
        return tvGuideService.getTVGuide();
    }
}
