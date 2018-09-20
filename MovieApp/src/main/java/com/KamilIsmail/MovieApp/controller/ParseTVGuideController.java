package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverDTO;
import com.KamilIsmail.MovieApp.scheduled.TVGuideController.ParseTVGuide;
import com.KamilIsmail.MovieApp.service.DiscoverService;
import com.KamilIsmail.MovieApp.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("parse/")
public class ParseTVGuideController {

    @Autowired
    ParseService parseService;

    @GetMapping("")
    public BooleanDTO getJSON() throws IOException {
        return parseService.parseTVGuide();
    }
}