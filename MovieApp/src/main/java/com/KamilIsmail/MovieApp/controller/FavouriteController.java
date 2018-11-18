package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverMovieDTO;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import com.KamilIsmail.MovieApp.service.FavouritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * @author kamilismail
 * Klasa odpowiadająca za obsługę zapytań dotyczących pozycji ulubionych.
 */
@RestController
@RequestMapping("favourite/")
public class FavouriteController {

    @Autowired
    FavouritesService favService;
    @Autowired
    UserRepository userRepository;

    /**
     * Metoda zwraca listę ulubionych filmów dla danego użytkownika.
     * @param principal
     * @return
     * @throws IOException
     */
    @GetMapping("getFavourites")
    public List<DiscoverMovieDTO> getFavourites(Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return favService.getFavourites((int) userEntity.getUserId());
    }

    /**
     * Metoda zwraca true lub false - w zależności od powodzenia operacji. Odpowiada za dodanie danej pozycji filmowej
     * do listy ulubionych dla danego użytkownika.
     * @param movieId
     * @param principal
     * @return
     */
    @PostMapping("addFavourite")
    public BooleanDTO addFavourite(@RequestParam("movieID") int movieId, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return favService.addFavourite(userEntity.getUserId(), movieId);
    }

    /**
     * Metoda zwraca true lub false - wzależności od powodzenia operacji. Odpowaida za usunięcie danego filmu z listy
     * ulubionych danego użytkownika.
     * @param movieID
     * @param principal
     * @return
     */
    @DeleteMapping("deleteFavourite")
    public BooleanDTO deleteFavourite(@RequestParam("movieID") int movieID, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return favService.deleteFavourite(userEntity.getUserId(), movieID);
    }
}
