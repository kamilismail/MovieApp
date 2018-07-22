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

@RestController
@RequestMapping("favourite/")
public class FavouriteController {

    @Autowired
    FavouritesService favService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("getFavourites")
    public List<DiscoverMovieDTO> getFavourites(Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return favService.getFavourites((int) userEntity.getUserId());
    }

    @PostMapping("addFavourite")
    public BooleanDTO addFavourite(@RequestParam("movieID") int movieId, Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return favService.addFavourite((int) userEntity.getUserId(), movieId);
    }

    @DeleteMapping("deleteFavourite")
    public BooleanDTO deleteFavourite(@RequestParam("movieID") int movieID, Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return favService.deleteFavourite((int) userEntity.getUserId(), movieID);
    }
}
