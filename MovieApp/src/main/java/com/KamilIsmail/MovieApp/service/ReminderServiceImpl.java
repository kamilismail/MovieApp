package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DAO.ReminderDao;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.ReminderDTO;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.RemindersEntity;
import com.KamilIsmail.MovieApp.entities.TvstationsEntity;
import com.KamilIsmail.MovieApp.repository.ReminderRepository;
import com.KamilIsmail.MovieApp.repository.TvSatationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Autowired
    TvSatationRepository tvSatationRepository;

    @Cacheable(value = "reminders", key = "#userId")
    @Override
    public List<ReminderDTO> getReminders(int userId) {
        List<RemindersEntity> reminderEntitiesList = reminderRepository.findRemindersEntitiesByUserId(userId);
        List<ReminderDTO> reminderResults = new ArrayList<>();

        for (RemindersEntity reminderList : reminderEntitiesList) {
            TvstationsEntity tvstationsEntity = reminderList.getTvstationsByTvstationId();
            MoviesEntity moviesEntity = reminderList.getMoviesByMovieId();
            ReminderDTO result = new ReminderDTO(moviesEntity.getMediaType(), Integer.toString(moviesEntity.getTmdbId()),
                    moviesEntity.getMovieName(), moviesEntity.getBackdropPath(), reminderList.getData().toString(),
                    tvstationsEntity.getName(), tvstationsEntity.getLogoPath(), moviesEntity.getReleaseDate());

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
