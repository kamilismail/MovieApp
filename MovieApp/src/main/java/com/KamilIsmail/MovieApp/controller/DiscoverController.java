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


/**
 * @author kamilismail
 * Klasa, w której obsługiwane jest zapytanie dotyczące pobrania list filmów popularnych, emitowanych w kinach,
 * zbliżających się premier, popularnych seriali. Każda lista zawiera max 10 elementów.
 */
@RestController
@RequestMapping("discover/")
public class DiscoverController {

    @Autowired
    DiscoverService discoverService;

    /**
     * Metoda zwracająca listy filmów.
     * @param principal
     * @return
     * @throws IOException
     */
    @GetMapping("")
    public DiscoverDTO getJSON(Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        return discoverService.getJSON();
    }
}
