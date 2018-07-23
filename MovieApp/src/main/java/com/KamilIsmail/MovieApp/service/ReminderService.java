package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.ReminderDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.util.List;

public interface ReminderService {
    @PreAuthorize("hasAnyAuthority('admin','user')")
    List<ReminderDTO> getReminders(int userId) throws IOException;

    @PreAuthorize("hasAnyAuthority('admin','user')")
    BooleanDTO addReminder(int userId, int movieID);

    @PreAuthorize("hasAnyAuthority('admin','user')")
    BooleanDTO deleteReminder(int userId, int movieID);
}
