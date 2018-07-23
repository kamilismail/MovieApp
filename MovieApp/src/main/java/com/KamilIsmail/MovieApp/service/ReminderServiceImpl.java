package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DAO.ReminderDao;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.ReminderDTO;
import com.KamilIsmail.MovieApp.entities.RemindersEntity;
import com.KamilIsmail.MovieApp.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    ReminderRepository reminderRepository;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    ReminderDao reminderDao;

    @Override
    public List<ReminderDTO> getReminders(int userId) throws IOException {
        List<RemindersEntity> reminderEntitiesList = reminderRepository.findRemindersEntitiesByUserId(userId);
        List<ReminderDTO> reminderResults = new ArrayList<>();

        for (RemindersEntity reminderList : reminderEntitiesList) {
            ReminderDTO result = new ReminderDTO("MOVIE",
                    Integer.toString(reminderList.getMoviesByMovieId().getTmdbId()), reminderList.getMoviesByMovieId().getMovieName(),
                    reminderList.getMoviesByMovieId().getPosterPath(), reminderList.getData().toString());
            reminderResults.add(result);
        }
        return reminderResults;
    }

    @Override
    public BooleanDTO addReminder(int userId, int movieID) {
        try {
            List<RemindersEntity> reminderEntitiesList = reminderRepository.findRemindersEntitiesByUserId(userId);
            for (RemindersEntity reminderList : reminderEntitiesList) {
                if (reminderList.getMoviesByMovieId().getTmdbId() == movieID)
                    return new BooleanDTO(false);
            }
            return reminderDao.addReminder(userId, movieID);

        } catch (Exception e) {
            return new BooleanDTO(false);
        }
    }

    @Override
    public BooleanDTO deleteReminder(int userId, int movieID) {
        return reminderDao.deleteReminder(userId, movieID);
    }
}
