package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.Constants;
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
import java.util.stream.Collectors;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    ReminderRepository reminderRepository;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    ReminderDao reminderDao;

    @Autowired
    TvSatationRepository tvSatationRepository;

    @Override
    public List<ReminderDTO> getReminders(int userId) {
        return reminderRepository.findRemindersEntitiesByUserId(userId).stream()
                .map(p -> new ReminderDTO(p.getMoviesByMovieId().getMediaType(), Integer.toString(p.getMoviesByMovieId().getTmdbId()),
                        p.getMoviesByMovieId().getMovieName(), Constants.getPosterPath() + p.getMoviesByMovieId().getBackdropPath(),
                        p.getData().toString(), p.getTvstationsByTvstationId().getName(), p.getTvstationsByTvstationId().getLogoPath(),
                        p.getMoviesByMovieId().getReleaseDate(), p.getMoviesByMovieId().getAvarageRating()))
                .collect(Collectors.toList());
    }

    @Override
    public BooleanDTO addReminder(int userId, int movieID) {
        try {
            if (reminderRepository.findRemindersEntityByUserIdAndMovieId(userId, movieID) != null)
                return new BooleanDTO(false);
            else
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
