package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverMovieDTO;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import com.KamilIsmail.MovieApp.service.WantToWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * @author kamilismail
 * Klasa obsługuje listę "want to watch".
 */
@RestController
@RequestMapping("want/")
public class WantToWatchController {
    @Autowired
    WantToWatchService wantService;
    @Autowired
    UserRepository userRepository;

    /**
     * Metoda zwraca listę pozycji filmowych ustawionych jako "want to watch" przez danego użytkownika.
     * @param principal
     * @return
     * @throws IOException
     */
    @GetMapping("getWants")
    public List<DiscoverMovieDTO> getWants(Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return wantService.getWants(userEntity.getUserId());
    }

    /**
     * Metoda dodaje daną pozycję filmową do listy "want to watch".
     * @param movieId
     * @param principal
     * @return
     */
    @PostMapping("addWant")
    public BooleanDTO addWant(@RequestParam("movieID") int movieId, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return wantService.addWant(userEntity.getUserId(), movieId);
    }

    /**
     * Metoda usuwa daną pozycję filmową z listy "want to watch".
     * @param movieID
     * @param principal
     * @return
     */
    @DeleteMapping("deleteWant")
    public BooleanDTO deleteWant(@RequestParam("movieID") int movieID, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return wantService.deleteWant(userEntity.getUserId(), movieID);
    }
}
