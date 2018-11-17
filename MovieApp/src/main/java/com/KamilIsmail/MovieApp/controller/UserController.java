package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.GetUsernameDTO;
import com.KamilIsmail.MovieApp.DTO.UserDTO;
import com.KamilIsmail.MovieApp.DTO.UserPhotoDTO;
import com.KamilIsmail.MovieApp.controller.userControllerParam.ChangePswParam;
import com.KamilIsmail.MovieApp.controller.userControllerParam.CreateUserParam;
import com.KamilIsmail.MovieApp.controller.userControllerParam.DeleteUserParam;
import com.KamilIsmail.MovieApp.controller.userControllerParam.FacebookUserParam;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.helpers.GrantAuthHelper;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import com.KamilIsmail.MovieApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user/")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("all")
    public List<UserDTO> getAllUser(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.getAllUser();
    }

    @PostMapping("")
    public UserDTO createUser(@Valid @RequestBody CreateUserParam param) {
        if (!userRepository.findByUsername(param.getUsername()).isEmpty())
            return null;
        return userService.createUser(param.getUsername(), param.getPassword(), param.getRole());
    }

    @PostMapping("setFirebaseID")
    public BooleanDTO setFirebaseID(@RequestParam("firebaseID") String firebaseID, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return userService.setFirebaseID(userEntity.getUserId(), firebaseID);
    }

    @PutMapping("")
    public BooleanDTO changeUserPassword(@Valid @RequestBody ChangePswParam param, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.changeUserPassword(user.getUsername(), param.getOldPassword(), param.getNewPassword());
    }

    @DeleteMapping("")
    public BooleanDTO deleteUser(@Valid @RequestBody DeleteUserParam param, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.deleteUser(user.getUsername(), param.getPassword());
    }

    @DeleteMapping("admin")
    public BooleanDTO deleteAdminUser(@RequestParam("userid") int userId, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.deleteAdminUser(userId);
    }

    @DeleteMapping("facebook")
    public BooleanDTO deleteFacebookUser(@Valid @RequestBody DeleteUserParam param, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return userService.deleteFacebookUser(param.getPassword(), userEntity.getUserId());
    }

    @GetMapping("getUsername")
    public GetUsernameDTO getUsername(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return userService.getUsername((int) userEntity.getUserId());
    }

    @PostMapping("facebookLogin")
    public GetUsernameDTO facebookLogin(@Valid @RequestBody FacebookUserParam param) {
        GetUsernameDTO getUsernameDTO = userService.facebookLogin(param.getUsername(), param.getFacebookID(), param.getMail(), param.getRole());
        UserEntity user = userRepository.findByUsername(param.getFacebookID()).get(0);
        GrantAuthHelper grantAuthHelper = new GrantAuthHelper(user.getUsername(), user.getRole());
        UserDetails principal = grantAuthHelper.grantAuth();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return getUsernameDTO;
    }

    @GetMapping("facebookPhoto")
    public UserPhotoDTO facebookPhoto(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return userService.facebookPhoto(userEntity.getUserId());
    }

    @PostMapping("sendPhotoName")
    public BooleanDTO sendPhotoName(@RequestParam("photoName") String fileName, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return userService.setPhotoName(userEntity.getUserId(), fileName);
    }
}
