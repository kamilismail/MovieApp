package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.RemindersEntity;
import com.KamilIsmail.MovieApp.entities.TvstationsEntity;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.repository.MovieRepository;
import com.KamilIsmail.MovieApp.repository.ReminderRepository;
import com.KamilIsmail.MovieApp.repository.TvStationRepository;
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

/**
 * @author kamilismail
 * Klasa odpowiadająca za komunikację z bazą danych. Zapis dd tabeli przypomnien.
 */
@Service
public class ReminderDaoImpl implements ReminderDao {

    @Autowired
    ReminderRepository reminderRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TvStationRepository tvStationRepository;

    /**
     * Metoda odpowiada za zapis nowego filmu do tabeli przypomnien. Sprawdza czy dany film jest aktualnie emitowany
     * w tv i jeśli jest ustawia od razu odpowiednią datę emisji, jeśli nie to ustawia atrybut "to_remind".
     * Atrybut ten powoduje, że dana pozycja będzie regularnie sparwdzana czy film jest już w programie tv.
     * @param userId
     * @param movieId
     * @return
     */
    @Override
    public BooleanDTO addReminder(int userId, int movieId) {
        MoviesEntity movieEntity = movieRepository.findByTmdbId(movieId);
        UserEntity userEntity = userRepository.findByUserId(userId);

        String stationName = "to_remind";
        String logoPath = "";
        String date = "";
        String time = "";
        Broadcast broadcastResult = null;
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        MovieDb tmdbResult = tmdbApi.getMovies().getMovie(toIntExact(movieId), Constants.getLanguage());
        FilmwebApi fa = new FilmwebApi();
        FilmSearchResult filmResult = fa.findFilm(tmdbResult.getTitle(), Integer.parseInt(tmdbResult.getReleaseDate().substring(0, 4))).get(0);
        List<Broadcast> broadcasts = null;
        try {
            broadcasts = fa.getBroadcasts(filmResult.getId(), 0, 20);
            if (!broadcasts.isEmpty()) {
                for (Broadcast broadcast: broadcasts) {
                    if(broadcast.getTime().getHour() >= 18) {
                        broadcastResult = broadcast;
                        break;
                    }
                }
                if (broadcastResult == null)
                    broadcastResult = broadcasts.get(0);
                Long chanelID = broadcastResult.getChannelId();
                List<TVChannel> tvChannels = fa.getTvChannels();
                for (TVChannel tvChannel : tvChannels) {
                    if (tvChannel.getId() == chanelID) {
                        stationName = tvChannel.getName();
                        logoPath = tvChannel.getLogo(Size.SMALL).getPath();
                        date = broadcastResult.getDate().toString();
                        time = broadcastResult.getTime().toString();
                        break;
                    }
                }
            }
        } catch (FilmwebException e) {
            e.printStackTrace();
        }
        if (movieEntity == null) {

            movieEntity = new MoviesEntity(tmdbResult.getTitle(),toIntExact(filmResult.getId()),tmdbResult.getId(),
                    tmdbResult.getPosterPath(), tmdbResult.getReleaseDate(),tmdbResult.getBackdropPath(),
                    tmdbResult.getMediaType().toString(), String.valueOf(tmdbResult.getVoteAverage()),tmdbResult.getOverview());
            movieRepository.save(movieEntity);
        }
        TvstationsEntity tvstationsEntity = null;
        List<TvstationsEntity> stationsList = null;
        if (!stationName.equals("to_remind"))
            stationsList = tvStationRepository.findTvstationsEntitiesByName(stationName);

        if (stationsList != null) {
            if (stationsList.size() < 1) {
                tvstationsEntity = new TvstationsEntity(stationName, logoPath);
                tvStationRepository.save(tvstationsEntity);
            } else {
                tvstationsEntity = stationsList.get(0);
            }
        } else {
            tvstationsEntity = new TvstationsEntity(stationName, logoPath);
            tvStationRepository.save(tvstationsEntity);
        }

        Timestamp sqlDate;
        if (!date.isEmpty() && !time.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date parsedDate = dateFormat.parse(date + ' ' + time);
                sqlDate = new java.sql.Timestamp(parsedDate.getTime());
            } catch (Exception e) {
                return (new BooleanDTO(false));
            }
        } else {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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

    /**
     * Metoda usuwa ustawione przypomnienie.
     * @param userId
     * @param movieId
     * @return
     */
    @Override
    public BooleanDTO deleteReminder(int userId, int movieId) {
        MoviesEntity movieEntity = movieRepository.findByTmdbId(movieId);
        RemindersEntity remindersEntity;
        if (movieEntity != null) {
            remindersEntity = reminderRepository.findRemindersEntityByUserIdAndMovieId(userId, movieEntity.getMovieId());
        } else return new BooleanDTO(false);
        if (remindersEntity != null) {
            reminderRepository.delete(remindersEntity);
            return new BooleanDTO(true);
        } else
            return new BooleanDTO(false);
    }

    /**
     * W przypadku odnalezienia danego filmu w programie tv, metoda ustawia dla danej pozycji datę emisji.
     * @param tvStationId
     * @param reminderId
     * @param movieId
     * @return
     */
    @Override
    public BooleanDTO changeReminder(int tvStationId, int reminderId, int movieId) {
        RemindersEntity remindersEntity = reminderRepository.findRemindersEntityByReminderId(reminderId);
        TvstationsEntity tvstationsEntity = tvStationRepository.findTvstationsEntityByTvstationId(tvStationId);
        FilmwebApi fa = new FilmwebApi();
        String stationName= "";
        String logoPath = "";
        String date = "";
        String time = "";
        Broadcast broadcastResult = null;
        try {
            List<Broadcast> broadcasts = fa.getBroadcasts((long) movieId, 0, 20);
            if (!broadcasts.isEmpty()) {
                for (Broadcast broadcast: broadcasts) {
                    if(broadcast.getTime().getHour() >= 18) {
                        broadcastResult = broadcast;
                        break;
                    }
                }
                if (broadcastResult == null)
                    broadcastResult = broadcasts.get(0);
                Long chanelID = broadcastResult.getChannelId();
                List<TVChannel> tvChannels = fa.getTvChannels();
                for (TVChannel tvChannel : tvChannels) {
                    if (tvChannel.getId() == chanelID) {
                        stationName = tvChannel.getName();
                        logoPath = tvChannel.getLogo(Size.SMALL).getPath();
                        date = broadcastResult.getDate().toString();
                        time = broadcastResult.getTime().toString();
                        break;
                    }
                }
            }
        } catch (FilmwebException e) {
            return (new BooleanDTO(false));
        }
        Timestamp sqlDate;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date parsedDate = dateFormat.parse(date + ' ' + time);
            sqlDate = new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            return (new BooleanDTO(false));
        }

        remindersEntity.setData(sqlDate);
        reminderRepository.save(remindersEntity);

        tvstationsEntity.setName(stationName);
        tvstationsEntity.setLogoPath(logoPath);
        tvStationRepository.save(tvstationsEntity);

        return new BooleanDTO(true);
    }
}
