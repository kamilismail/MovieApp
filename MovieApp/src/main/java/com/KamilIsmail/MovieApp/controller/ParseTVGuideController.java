package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverDTO;
import com.KamilIsmail.MovieApp.scheduled.TVGuideController.ParseTVGuide;
import com.KamilIsmail.MovieApp.service.DiscoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("parse/")
public class ParseTVGuideController {

    @GetMapping("")
    public BooleanDTO getJSON() throws IOException {
        ParseTVGuide tvGuide = new ParseTVGuide();
        tvGuide.run();
        return new BooleanDTO(true);
    }
}