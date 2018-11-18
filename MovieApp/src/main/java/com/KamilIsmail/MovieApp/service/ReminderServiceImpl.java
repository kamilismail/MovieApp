package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DAO.ReminderDao;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.ReminderDTO;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.repository.MovieRepository;
import com.KamilIsmail.MovieApp.repository.ReminderRepository;
import com.KamilIsmail.MovieApp.repository.TvStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kamilismail
 * Serwis wywoływany z kontrolera.
 */
@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    ReminderRepository reminderRepository;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    ReminderDao reminderDao;

    @Autowired
    TvStationRepository tvStationRepository;

    @Autowired
    MovieRepository movieRepository;

    /**
     * Metoda zwracająca listę ustawionych przypomnień dla danego użytkownika.
     * @param userId
     * @return
     */
    @Override
    public List<ReminderDTO> getReminders(int userId) {
        return reminderRepository.findRemindersEntitiesByUserId(userId).stream()
                .map(p -> new ReminderDTO(p.getMoviesByMovieId().getMediaType(), Integer.toString(p.getMoviesByMovieId().getTmdbId()),
                        p.getMoviesByMovieId().getMovieName(), Constants.getPosterPath() + p.getMoviesByMovieId().getBackdropPath(),
                        p.getData().toString(), p.getTvstationsByTvstationId().getName(), p.getTvstationsByTvstationId().getLogoPath(),
                        p.getMoviesByMovieId().getReleaseDate(), p.getMoviesByMovieId().getAvarageRating()))
                .collect(Collectors.toList());
    }

    /**
     * Metoda dodająca nowe przypomnienie o danym filmie.
     * @param userId
     * @param movieID
     * @return
     */
    @Override
    public BooleanDTO addReminder(int userId, int movieID) {
        try {
            MoviesEntity moviesEntity = movieRepository.findByTmdbId(movieID);
            if (moviesEntity == null)
                return reminderDao.addReminder(userId, movieID);
            if (reminderRepository.findRemindersEntityByUserIdAndMovieId(userId, moviesEntity.getMovieId()) != null)
                return new BooleanDTO(false);
            else
                return reminderDao.addReminder(userId, movieID);
        } catch (Exception e) {
            return new BooleanDTO(false);
        }
    }

    /**
     * Metoda usuwająca przypomnienie o danym filmie.
     * @param userId
     * @param movieID
     * @return
     */
    @Override
    public BooleanDTO deleteReminder(int userId, int movieID) {
        return reminderDao.deleteReminder(userId, movieID);
    }
}
