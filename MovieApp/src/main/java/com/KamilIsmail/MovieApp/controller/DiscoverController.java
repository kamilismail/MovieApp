package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.DTO.DiscoverDTO;
import com.KamilIsmail.MovieApp.service.DiscoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("discover/")
public class DiscoverController {

    @Autowired
    DiscoverService discoverService;

    @GetMapping("")
    public DiscoverDTO getJSON() throws IOException {
        return discoverService.getJSON();
    }
}
