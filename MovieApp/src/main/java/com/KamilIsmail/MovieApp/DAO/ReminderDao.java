package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import org.springframework.stereotype.Repository;

/**
 * @author kamilismail
 */
@Repository
public interface ReminderDao {
    BooleanDTO addReminder(int userId, int movieId);

    BooleanDTO deleteReminder(int userId, int movieId);

    BooleanDTO changeReminder(int tvStationId, int reminderId, int movieId);
}
