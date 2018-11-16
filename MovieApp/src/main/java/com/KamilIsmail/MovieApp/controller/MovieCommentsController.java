package com.KamilIsmail.MovieApp.controller;


import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.MovieCommentsDTO;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import com.KamilIsmail.MovieApp.service.MovieCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("moviecomments")
public class MovieCommentsController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieCommentsService movieCommentsService;

    @GetMapping("")
    public List<MovieCommentsDTO> getComments (@RequestParam("movie_id") int movieId, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        return movieCommentsService.getComments(movieId);
    }

    @PostMapping("")
    public BooleanDTO addComment (@RequestParam("movie_id") int movieId, @RequestParam("comment") String comment, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return movieCommentsService.addComment(userEntity.getUserId(), comment, movieId);
    }

    @DeleteMapping("")
    public BooleanDTO deleteComment (@RequestParam("movie_id") int movieId, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return movieCommentsService.deleteComment(userEntity.getUserId(), movieId);
    }

}
