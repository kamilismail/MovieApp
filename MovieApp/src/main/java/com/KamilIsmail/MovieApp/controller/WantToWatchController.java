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

@RestController
@RequestMapping("want/")
public class WantToWatchController {
    @Autowired
    WantToWatchService wantService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("getWants")
    public List<DiscoverMovieDTO> getWants(Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return wantService.getWants((int) userEntity.getUserId());
    }

    @PostMapping("addWant")
    public BooleanDTO addWant(@RequestParam("movieID") int movieId, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return wantService.addWant((int) userEntity.getUserId(), movieId);
    }

    @DeleteMapping("deleteWant")
    public BooleanDTO deleteWant(@RequestParam("movieID") int movieID, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return wantService.deleteWant((int) userEntity.getUserId(), movieID);
    }
}
