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

/**
 * @author kamilismail
 * Klasa obsługujące zapytania dotyczące użytkownika.
 */
@RestController
@RequestMapping("user/")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    /**
     * Metoda zwraca wszystkich użytkowników zarejestrowanych w systemie.
     * @param principal
     * @return
     */
    @GetMapping("all")
    public List<UserDTO> getAllUser(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.getAllUser();
    }

    /**
     * Metoda tworzy nowego użytkownika.
     * @param param
     * @return
     */
    @PostMapping("")
    public UserDTO createUser(@Valid @RequestBody CreateUserParam param) {
        if (!userRepository.findByUsername(param.getUsername()).isEmpty())
            return null;
        return userService.createUser(param.getUsername(), param.getPassword(), param.getRole());
    }

    /**
     * Metoda zapisuje nowey token firebase dla danego użytkownika. Token służy do wysyłania powiadomień na telefon
     * użytkownika.
     * @param firebaseID
     * @param principal
     * @return
     */
    @PostMapping("setFirebaseID")
    public BooleanDTO setFirebaseID(@RequestParam("firebaseID") String firebaseID, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return userService.setFirebaseID(userEntity.getUserId(), firebaseID);
    }

    /**
     * Metoda obsługuje zmienę hasła użytkownika. Przyjmuje jsona zawierającego stare oraz nowe hasło.
     * @param param
     * @param principal
     * @return
     */
    @PutMapping("")
    public BooleanDTO changeUserPassword(@Valid @RequestBody ChangePswParam param, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.changeUserPassword(user.getUsername(), param.getOldPassword(), param.getNewPassword());
    }

    /**
     * Metoda usuwa konto użytkownika.
     * @param param
     * @param principal
     * @return
     */
    @DeleteMapping("")
    public BooleanDTO deleteUser(@Valid @RequestBody DeleteUserParam param, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.deleteUser(user.getUsername(), param.getPassword());
    }

    /**
     * Metoda pozwala administartorowi na usuwanie dowolnego konta użytkownika.
     * @param userId
     * @param principal
     * @return
     */
    @DeleteMapping("admin")
    public BooleanDTO deleteAdminUser(@RequestParam("userid") int userId, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        return userService.deleteAdminUser(userId);
    }

    /**
     * Metoda obsługuje usuwanie konta stworzonego przez użytkownika zalogowanego przez Facebooka.
     * @param param
     * @param principal
     * @return
     */
    @DeleteMapping("facebook")
    public BooleanDTO deleteFacebookUser(@Valid @RequestBody DeleteUserParam param, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return userService.deleteFacebookUser(param.getPassword(), userEntity.getUserId());
    }

    /**
     * Metoda zwraca nazwę użytkownika.
     * @param principal
     * @return
     */
    @GetMapping("getUsername")
    public GetUsernameDTO getUsername(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return userService.getUsername((int) userEntity.getUserId());
    }

    /**
     * Metoda w zależności czy użytkwonik logujący się przez Facebooka posiada już zapisane konto lub nie, tworzy je
     * i zwraca nazwę loginu lub zwraca nazwę konta już utworzonego.
     * @param param
     * @return
     */
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

    /**
     * Metoda zwraca link do zdjęcia profilowego użytkownika.
     * @param principal
     * @return
     */
    @GetMapping("facebookPhoto")
    public UserPhotoDTO facebookPhoto(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return userService.facebookPhoto(userEntity.getUserId());
    }

    /**
     * Metoda zapisuje link do zdjęcia profilowego danego użytkownika.
     * @param fileName
     * @param principal
     * @return
     */
    @PostMapping("sendPhotoName")
    public BooleanDTO sendPhotoName(@RequestParam("photoName") String fileName, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return userService.setPhotoName(userEntity.getUserId(), fileName);
    }
}
