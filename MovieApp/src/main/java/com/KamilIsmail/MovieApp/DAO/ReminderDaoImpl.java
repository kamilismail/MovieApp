package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.RemindersEntity;
import com.KamilIsmail.MovieApp.entities.TvstationsEntity;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.helpers.BroadcastsHelper;
import com.KamilIsmail.MovieApp.repository.MovieRepository;
import com.KamilIsmail.MovieApp.repository.ReminderRepository;
import com.KamilIsmail.MovieApp.repository.TvSatationRepository;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import info.talacha.filmweb.api.FilmwebApi;
import info.talacha.filmweb.connection.FilmwebException;
import info.talacha.filmweb.models.Broadcast;
import info.talacha.filmweb.models.Size;
import info.talacha.filmweb.models.TVChannel;
import info.talacha.filmweb.search.models.FilmSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.Math.toIntExact;

@Service
public class ReminderDaoImpl implements ReminderDao {

    @Autowired
    ReminderRepository reminderRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TvSatationRepository tvSatationRepository;

    @Override
    public BooleanDTO addReminder(int userId, int movieId) {
        MoviesEntity movieEntity = movieRepository.findByTmdbId(movieId);
        UserEntity userEntity = userRepository.findByUserId(userId);

        String stationName = "to_remind";
        String logoPath = "";
        String date = "";
        String time = "";
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        MovieDb tmdbResult = tmdbApi.getMovies().getMovie(toIntExact(movieId), "pl");

        BroadcastsHelper broadcastsHelper = new BroadcastsHelper(tmdbResult.getTitle(), tmdbResult.getReleaseDate());
        if (broadcastsHelper.getBroadcast()) {
            stationName = broadcastsHelper.getStationName();
            logoPath = broadcastsHelper.getLogoPath();
            date = broadcastsHelper.getDate();
            time = broadcastsHelper.getTime();
        } else {
            return new BooleanDTO(false);
        }
        if (movieEntity == null) {

            movieEntity = new MoviesEntity(tmdbResult.getTitle(),toIntExact(broadcastsHelper.getFilmwebID()),tmdbResult.getId(),
                    tmdbResult.getPosterPath(), tmdbResult.getReleaseDate(),tmdbResult.getBackdropPath(),
                    tmdbResult.getMediaType().toString(), String.valueOf(tmdbResult.getVoteAverage()),tmdbResult.getOverview());
            movieRepository.save(movieEntity);
        }
        TvstationsEntity tvstationsEntity = null;
        List<TvstationsEntity> stationsList = null;
        if (!stationName.equals("to_remind"))
            stationsList = tvSatationRepository.findTvstationsEntitiesByName(stationName);

        if (stationsList != null) {
            if (stationsList.size() < 1) {
                tvstationsEntity = new TvstationsEntity(stationName, logoPath);
                tvSatationRepository.save(tvstationsEntity);
            } else {
                tvstationsEntity = stationsList.get(0);
            }
        } else {
            tvstationsEntity = new TvstationsEntity(stationName, logoPath);
            tvSatationRepository.save(tvstationsEntity);
        }

        Timestamp sqlDate;
        if (!date.isEmpty() && !time.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Date parsedDate = dateFormat.parse(date + ' ' + time);
                sqlDate = new java.sql.Timestamp(parsedDate.getTime());
            } catch (Exception e) {
                return (new BooleanDTO(false));
            }
        } else {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Date parsedDate = dateFormat.parse("9999-12-31" + ' ' + "00:00");
                sqlDate = new java.sql.Timestamp(parsedDate.getTime());
            } catch (Exception e) {
                return (new BooleanDTO(false));
            }
        }
        RemindersEntity remindersEntity = new RemindersEntity(userEntity.getUserId(), movieEntity.getMovieId(),
                tvstationsEntity.getTvstationId(), false, sqlDate, userEntity, movieEntity, tvstationsEntity);
        reminderRepository.save(remindersEntity);

        movieEntity.setRemindersByMovieId(reminderRepository.findRemindersEntitiesByUserId(userId));
        movieRepository.save(movieEntity);
        userEntity.setRemindersByUserId(reminderRepository.findRemindersEntitiesByUserId(userId));
        userRepository.save(userEntity);

        return (new BooleanDTO(true));
    }

    @Override
    public BooleanDTO deleteReminder(int userId, int movieId) {
        List<RemindersEntity> reminderEntitiesList = reminderRepository.findRemindersEntitiesByUserId(userId);
        for (RemindersEntity reminderList : reminderEntitiesList) {
            if (reminderList.getMoviesByMovieId().getTmdbId() == movieId) {
                reminderRepository.delete(reminderList);
                return new BooleanDTO(true);
            }
        }
        return new BooleanDTO(false);
    }

    @Override
    public BooleanDTO changeReminder(int tvStationId, int reminderId, int movieId) {
        RemindersEntity remindersEntity = reminderRepository.findRemindersEntityByReminderId(reminderId);
        TvstationsEntity tvstationsEntity = tvSatationRepository.findTvstationsEntityByTvstationId(tvStationId);
        FilmwebApi fa = new FilmwebApi();
        String stationName;
        String logoPath;
        String date;
        String time;

        BroadcastsHelper broadcastsHelper = new BroadcastsHelper(movieId);
        if (broadcastsHelper.getBroadcastById()) {
            stationName = broadcastsHelper.getStationName();
            logoPath = broadcastsHelper.getLogoPath();
            date = broadcastsHelper.getDate();
            time = broadcastsHelper.getTime();
        } else {
            return new BooleanDTO(false);
        }

        Timestamp sqlDate;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date parsedDate = dateFormat.parse(date + ' ' + time);
            sqlDate = new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            return (new BooleanDTO(false));
        }

        remindersEntity.setData(sqlDate);
        reminderRepository.save(remindersEntity);

        tvstationsEntity.setName(stationName);
        tvstationsEntity.setLogoPath(logoPath);
        tvSatationRepository.save(tvstationsEntity);

        return new BooleanDTO(true);
    }
}
