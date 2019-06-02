package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.DTO.DiscoverDTO;
import com.KamilIsmail.MovieApp.DTO.recommender.RecommenderDTO;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import com.KamilIsmail.MovieApp.service.DiscoverService;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;


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
    @Autowired
    UserRepository userRepository;

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

    @GetMapping("bestforuser")
    public List<RecommenderDTO> getBestForUser(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return discoverService.getBestForUser(userEntity.getUserId());
    }
}
