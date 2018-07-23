package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.RemindersEntity;
import com.KamilIsmail.MovieApp.entities.TvstationsEntity;
import com.KamilIsmail.MovieApp.entities.UserEntity;
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

        String stationName = "";
        String logoPath = "";
        String date = "";
        String time = "";
        Constants constants = new Constants();
        if (movieEntity == null) {
            TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
            MovieDb tmdbResult = tmdbApi.getMovies().getMovie(toIntExact(movieId), "pl");
            FilmwebApi fa = new FilmwebApi();
            FilmSearchResult filmResult = fa.findFilm(tmdbResult.getTitle(), Integer.parseInt(tmdbResult.getReleaseDate().substring(0, 4))).get(0);
            List<Broadcast> broadcasts = null;
            try {
                broadcasts = fa.getBroadcasts(filmResult.getId(), 0, 20);
                if (!broadcasts.isEmpty()) {
                    Long chanelID = broadcasts.get(0).getChannelId();
                    List<TVChannel> tvChannels = fa.getTvChannels();
                    for (TVChannel tvChannel : tvChannels) {
                        if (tvChannel.getId() == chanelID) {
                            stationName = tvChannel.getName();
                            logoPath = tvChannel.getLogo(Size.SMALL).getPath();
                            date = broadcasts.get(0).getDate().toString();
                            time = broadcasts.get(0).getTime().toString();
                            break;
                        }
                    }
                }
            } catch (FilmwebException e) {
                e.printStackTrace();
            }
            movieEntity = new MoviesEntity();
            movieEntity.setMovieName(tmdbResult.getTitle());
            movieEntity.setTmdbId(tmdbResult.getId());
            movieEntity.setFilmwebId(toIntExact(filmResult.getId()));
            movieEntity.setPosterPath(tmdbResult.getPosterPath());
            movieEntity.setReleaseDate(tmdbResult.getReleaseDate());
            movieRepository.save(movieEntity);
        } else {
            TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
            MovieDb tmdbResult = tmdbApi.getMovies().getMovie(toIntExact(movieId), "pl");
            FilmwebApi fa = new FilmwebApi();
            FilmSearchResult filmResult = fa.findFilm(tmdbResult.getTitle(), Integer.parseInt(tmdbResult.getReleaseDate().substring(0, 4))).get(0);
            List<Broadcast> broadcasts = null;
            try {
                broadcasts = fa.getBroadcasts(filmResult.getId(), 0, 20);
                if (!broadcasts.isEmpty()) {
                    Long chanelID = broadcasts.get(0).getChannelId();
                    List<TVChannel> tvChannels = fa.getTvChannels();
                    for (TVChannel tvChannel : tvChannels) {
                        if (tvChannel.getId() == chanelID) {
                            stationName = tvChannel.getName();
                            logoPath = tvChannel.getLogo(Size.SMALL).getPath();
                            date = broadcasts.get(0).getDate().toString();
                            time = broadcasts.get(0).getTime().toString();
                            break;
                        }
                    }
                }
            } catch (FilmwebException e) {
                e.printStackTrace();
            }
        }
        TvstationsEntity tvstationsEntity = null;
        List <TvstationsEntity> stationsList = tvSatationRepository.findTvstationsEntitiesByName(stationName);

        if (stationsList.size() < 1) {
            tvstationsEntity = new TvstationsEntity();
            tvstationsEntity.setName(stationName);
            tvstationsEntity.setLogoPath(logoPath);
            //tvstationsEntity.setRemindersByTvstationId();
            tvSatationRepository.save(tvstationsEntity);
        } else {
            tvstationsEntity = stationsList.get(0);
        }
        RemindersEntity remindersEntity = new RemindersEntity();
        remindersEntity.setMovieId(movieEntity.getMovieId());
        remindersEntity.setUserId(userEntity.getUserId());
        Timestamp sqlDate;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date parsedDate = dateFormat.parse(date + ' ' + time);
            sqlDate = new java.sql.Timestamp(parsedDate.getTime());
        } catch(Exception e) {
            return (new BooleanDTO(false));
        }
        remindersEntity.setData(sqlDate);
        remindersEntity.setTvstationId(tvstationsEntity.getTvstationId());
        remindersEntity.setMoviesByMovieId(movieEntity);
        remindersEntity.setTvstationsByTvstationId(tvstationsEntity);
        remindersEntity.setUserByUserId(userEntity);
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
}
