package com.KamilIsmail.MovieApp.scheduled;

import com.KamilIsmail.MovieApp.DAO.ReminderDao;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.RemindersEntity;
import com.KamilIsmail.MovieApp.entities.TvstationsEntity;
import com.KamilIsmail.MovieApp.repository.ReminderRepository;
import com.KamilIsmail.MovieApp.repository.TvStationRepository;
import com.KamilIsmail.MovieApp.service.ParseService;
import info.talacha.filmweb.api.FilmwebApi;
import info.talacha.filmweb.connection.FilmwebException;
import info.talacha.filmweb.models.Broadcast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author kamilismail
 * Klasa rozpoczynająca proces pobierania nowego programu tv.
 */
@Component
public class TVGuideScheduler {
    private static final Logger log = LoggerFactory.getLogger(TVGuideScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    TvStationRepository tvStationRepository;
    @Autowired
    ReminderRepository reminderRepository;
    @Autowired
    ReminderDao reminderDao;
    @Autowired
    ParseService parseService;

    /**
     * Każdego dnia o godzienie 8:00 następuje pobranie nowego programu tv.
     */
    @Scheduled(cron = "0 0 8 * * *") //everyday at 8am
    public void checkTVGuide() {
        //parsowanie nowego programu tv
        log.info("Starting parsing new tv guide", dateFormat.format(new Date()));
        if (parseService.parseTVGuide().getResult())
            log.info("Successfully parsed tv guide", dateFormat.format(new Date()));
        else
            log.info("Something went wrong while parsing tv guide", dateFormat.format(new Date()));

        log.info("Checking tv guide at: " + dateFormat.format(new Date()), dateFormat.format(new Date()));
        List<TvstationsEntity> tvstationsEntities = tvStationRepository.findAll();
        FilmwebApi fa = new FilmwebApi();
        for (TvstationsEntity tvstationsEntity : tvstationsEntities) {
            if (tvstationsEntity.getName().equals("to_remind")) {
                RemindersEntity remindersEntity = reminderRepository.findRemindersEntityByTvstationId(tvstationsEntity.getTvstationId());
                MoviesEntity moviesEntity = remindersEntity.getMoviesByMovieId();
                try {
                    List<Broadcast> broadcasts = fa.getBroadcasts((long) moviesEntity.getFilmwebId(), 0, 20);
                    if (!broadcasts.isEmpty()) {
                        reminderDao.changeReminder(tvstationsEntity.getTvstationId(), remindersEntity.getReminderId(), moviesEntity.getFilmwebId());
                    }
                } catch (FilmwebException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
