package com.KamilIsmail.MovieApp.controller;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.ReminderDTO;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import com.KamilIsmail.MovieApp.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * @author kamilismail
 * Klasa obsługująca wszystkie zapytania dotyczące przypomnień.
 */
@RestController
@RequestMapping("reminder/")
public class ReminderController {

    @Autowired
    ReminderService reminderService;
    @Autowired
    UserRepository userRepository;

    /**
     * Metoda zwraca listę wsyztskich ustawionych przez użytkownika przypomnień.
     * @param principal
     * @return
     * @throws IOException
     */
    @GetMapping("getReminders")
    public List<ReminderDTO> getReminders(Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return reminderService.getReminders((int) userEntity.getUserId());
    }

    /**
     * Metoda obsługuje dodawanie danej pozycji filmowej dla danego użytkownika.
     * @param movieId
     * @param principal
     * @return
     */
    @PostMapping("addReminder")
    public BooleanDTO addReminder(@RequestParam("movieID") int movieId, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return reminderService.addReminder((int) userEntity.getUserId(), movieId);
    }

    /**
     * Metoda usuwa przypomnienie danej pozycji filmowej przez użytkownika.
     * @param movieID
     * @param principal
     * @return
     */
    @DeleteMapping("deleteReminder")
    public BooleanDTO deleteReminder(@RequestParam("movieID") int movieID, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return reminderService.deleteReminder((int) userEntity.getUserId(), movieID);
    }
}
