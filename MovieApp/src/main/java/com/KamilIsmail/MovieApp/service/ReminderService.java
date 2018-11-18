package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.ReminderDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.util.List;

/**
 * @author kamilismail
 */
public interface ReminderService {
    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    List<ReminderDTO> getReminders(int userId) throws IOException;

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    BooleanDTO addReminder(int userId, int movieID);

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    BooleanDTO deleteReminder(int userId, int movieID);
}
