package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.DTO.DiscoverDTO;
import com.KamilIsmail.MovieApp.scheduled.TVGuideController.ParseTVGuide;
import com.KamilIsmail.MovieApp.service.DiscoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("discover/")
public class DiscoverController {

    @Autowired
    DiscoverService discoverService;

    @GetMapping("")
    public DiscoverDTO getJSON(Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        return discoverService.getJSON();
    }
}
