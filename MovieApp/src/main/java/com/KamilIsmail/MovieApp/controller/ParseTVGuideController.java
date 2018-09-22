package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("parse/")
public class ParseTVGuideController {

    @Autowired
    ParseService parseService;

    @PostMapping("")
    public BooleanDTO getJSON() throws IOException {
        return parseService.parseTVGuide();
    }
}