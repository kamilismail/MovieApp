package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

/**
 * @author kamilismail
 * Klasa obsługująca operację pobrania, odczytu i zapisu programu tv.
 */
@RestController
@RequestMapping("parse/")
public class ParseTVGuideController {

    @Autowired
    ParseService parseService;

    /**
     * Metoda rozpoczynająca proces parsowania pliku xml z programem tv.
     * @param principal
     * @return
     */
    @PostMapping("")
    public BooleanDTO getJSON(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        return parseService.parseTVGuide();
    }
}