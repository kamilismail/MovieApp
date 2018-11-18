package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.TVGuideEntity;
import com.KamilIsmail.MovieApp.entities.TvstationsEntity;
import com.KamilIsmail.MovieApp.repository.MovieRepository;
import com.KamilIsmail.MovieApp.repository.TVGuideRepository;
import com.KamilIsmail.MovieApp.repository.TvStationRepository;
import com.KamilIsmail.MovieApp.scheduled.TVGuideBean.MovieBean;
import info.talacha.filmweb.api.FilmwebApi;
import info.talacha.filmweb.connection.FilmwebException;
import info.talacha.filmweb.models.Broadcast;
import info.talacha.filmweb.models.Size;
import info.talacha.filmweb.models.TVChannel;
import info.talacha.filmweb.search.models.FilmSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.toIntExact;

/**
 * @author kamilismail
 * Klasa odpowiada za kounikację z bazą dancyh. Zapis do tabeli progamu tv.
 */
@Service
public class TVGuideDaoImpl implements TVGuideDao {

    @Autowired
    TVGuideRepository tvGuideRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    TvStationRepository tvStationRepository;

    /**
     * Metoda zapisuje nowy program tv do tabeli. Sprawdza czy dany film na pewno jest emitowany w tv.
     * @param movieBean
     * @return
     */
    @Override
    public BooleanDTO addTVGuide(MovieBean movieBean) {
        MoviesEntity movieEntity = movieRepository.findByTmdbId(movieBean.getMovieDb().getId());
        String stationName = "";
        String logoPath = "";
        String date = "";
        String time = "";
        Broadcast broadcastResult = null;
        if (movieEntity == null) {
            FilmSearchResult filmResult;
            try {
                FilmwebApi fa = new FilmwebApi();
                filmResult = fa.findFilm(movieBean.getMovieDb().getTitle(), Integer.parseInt(movieBean.getMovieDb().getReleaseDate().substring(0, 4))).get(0);
                List<Broadcast> broadcasts;
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
            } catch (Exception e) {
                return new BooleanDTO(false);
            }

            movieEntity = new MoviesEntity(movieBean.getMovieDb().getTitle(), toIntExact(filmResult.getId()), movieBean.getMovieDb().getId(),
                    movieBean.getMovieDb().getPosterPath(), movieBean.getMovieDb().getReleaseDate(), movieBean.getMovieDb().getBackdropPath(),
                    movieBean.getMovieDb().getMediaType().toString(), String.valueOf(movieBean.getMovieDb().getVoteAverage()),
                    movieBean.getMovieDb().getOverview());
            movieRepository.save(movieEntity);
        } else {
            FilmwebApi fa = new FilmwebApi();
            FilmSearchResult filmResult = fa.findFilm(movieBean.getMovieDb().getTitle(), Integer.parseInt(movieBean.getMovieDb().getReleaseDate().substring(0, 4))).get(0);
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
                return new BooleanDTO(false);
            }
        }
        if (stationName.equals(""))
            stationName = movieBean.getChanel();
        TvstationsEntity tvstationsEntity = null;
        List<TvstationsEntity> stationsList = tvStationRepository.findTvstationsEntitiesByName(stationName);

        if (stationsList.isEmpty()) {
            tvstationsEntity = new TvstationsEntity(stationName, logoPath);
            tvStationRepository.save(tvstationsEntity);
        } else {
            tvstationsEntity = stationsList.get(0);
        }
        TVGuideEntity tvGuideEntity = new TVGuideEntity(movieEntity.getMovieId(), tvstationsEntity.getTvstationId(),
                movieBean.parseDateToTimestamp(), movieEntity, tvstationsEntity);
        tvGuideRepository.save(tvGuideEntity);

        return (new BooleanDTO(true));
    }

    /**
     * Metoda usuwa cały program tv z poprzedniego dnia.
     * @return
     */
    @Override
    public boolean deleteTVGuide() {
        tvGuideRepository.findAll().forEach(p -> tvGuideRepository.delete(p));
        return true;
    }
}