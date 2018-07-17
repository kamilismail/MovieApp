package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.DTO.GetUsernameDTO;
import com.KamilIsmail.MovieApp.DTO.UserDTO;
import com.KamilIsmail.MovieApp.controller.userControllerParam.CreateUserParam;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import com.KamilIsmail.MovieApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("user/")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public UserDTO getUser(Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.getUser(user.getUsername());
    }

    @GetMapping("all")
    public List<UserDTO> getAllUser(){
        return userService.getAllUser();
    }

    @PostMapping("")
    public UserDTO createUser(@Valid @RequestBody CreateUserParam param) {
        return userService.createUser(param.getUsername(),param.getPassword(), param.getRole());  //zwrot info
    }

    @PutMapping("")
    public Boolean changeUserPassword (@Valid @RequestParam(name = "password") String password, @Valid @RequestParam(name = "newPassword") String newPassword, Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.changeUserPassword(user.getUsername(), password, newPassword);
    }

    @DeleteMapping("")
    public Boolean deleteUser(@Valid @RequestParam(name = "password") String password, Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.deleteUser(user.getUsername(), password);
    }

    @GetMapping("getUsername")
    public GetUsernameDTO getUsername(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return userService.getUsername((int)userEntity.getUserId());
    }
}
