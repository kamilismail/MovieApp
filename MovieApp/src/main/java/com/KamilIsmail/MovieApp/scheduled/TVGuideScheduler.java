package com.KamilIsmail.MovieApp.scheduled;

import com.KamilIsmail.MovieApp.DAO.ReminderDao;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.RemindersEntity;
import com.KamilIsmail.MovieApp.entities.TvstationsEntity;
import com.KamilIsmail.MovieApp.repository.ReminderRepository;
import com.KamilIsmail.MovieApp.repository.TvSatationRepository;
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

@Component
public class TVGuideScheduler {
    private static final Logger log = LoggerFactory.getLogger(TVGuideScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    @Autowired
    TvSatationRepository tvSatationRepository;
    @Autowired
    ReminderRepository reminderRepository;
    @Autowired
    ReminderDao reminderDao;

    @Scheduled(cron = "0 0 8 * * *") //everyday at 8am
    public void checkTVGuide() {
        log.info("Checking tv guide at: " + dateFormat.format(new Date()), dateFormat.format(new Date()));

        List<TvstationsEntity> tvstationsEntities = tvSatationRepository.findAll();

        for (TvstationsEntity tvstationsEntity : tvstationsEntities) {
            if (tvstationsEntity.getName().equals("")) {
                RemindersEntity remindersEntity = reminderRepository.findRemindersEntitiesByTvstationId(tvstationsEntity.getTvstationId()).get(0);
                MoviesEntity moviesEntity = remindersEntity.getMoviesByMovieId();
                FilmwebApi fa = new FilmwebApi();
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
