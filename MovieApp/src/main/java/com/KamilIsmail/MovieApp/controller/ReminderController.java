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

@RestController
@RequestMapping("reminder/")
public class ReminderController {

    @Autowired
    ReminderService reminderService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("getReminders")
    public List<ReminderDTO> getReminders(Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return reminderService.getReminders((int) userEntity.getUserId());
    }

    @PostMapping("addReminder")
    public BooleanDTO addReminder(@RequestParam("movieID") int movieId, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return reminderService.addReminder((int) userEntity.getUserId(), movieId);
    }

    @DeleteMapping("deleteReminder")
    public BooleanDTO deleteReminder(@RequestParam("movieID") int movieID, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);
        return reminderService.deleteReminder((int) userEntity.getUserId(), movieID);
    }
}
