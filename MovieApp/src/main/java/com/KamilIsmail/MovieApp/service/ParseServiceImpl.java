package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.scheduled.TVGuideController.ParseTVGuide;
import org.springframework.stereotype.Service;

@Service
public class ParseServiceImpl implements ParseService{

    @Override
    public BooleanDTO parseTVGuide() {
        ParseTVGuide tvGuide = new ParseTVGuide();
        tvGuide.run();
        return new BooleanDTO(true);
    }
}
