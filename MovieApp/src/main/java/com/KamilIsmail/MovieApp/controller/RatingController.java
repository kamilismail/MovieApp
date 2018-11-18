package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverMovieDTO;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import com.KamilIsmail.MovieApp.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * @author kamilismail
 * Klasa obsługująca procesy dotyczące oceny filmu.
 */
@RestController
@RequestMapping("rating/")
public class RatingController {
    @Autowired
    RatingService ratingService;
    @Autowired
    UserRepository userRepository;

    /**
     * Metoda ustawia ocenę filmu przez użytkownika.
     * @param movieID
     * @param rating
     * @param principal
     * @return
     * @throws IOException
     */
    @PostMapping("")
    public BooleanDTO setRating(@RequestParam("movieID") int movieID, @RequestParam("rating") int rating, Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return ratingService.setRating(userEntity.getUserId(), movieID, rating);
    }

    /**
     * Metoda zwraca listę wszystkich wystawionych ocen przez danego użytkownika.
     * @param principal
     * @return
     * @throws IOException
     */
    @GetMapping("")
    public List<DiscoverMovieDTO> getRatings(Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return ratingService.getRatings(userEntity.getUserId());
    }
}
