package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.TVGuideEntity;
import com.KamilIsmail.MovieApp.entities.TvstationsEntity;
import com.KamilIsmail.MovieApp.repository.MovieRepository;
import com.KamilIsmail.MovieApp.repository.TVGuideRepository;
import com.KamilIsmail.MovieApp.repository.TvSatationRepository;
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

@Service
public class TVGuideDaoImpl implements TVGuideDao {

    @Autowired
    TVGuideRepository tvGuideRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    TvSatationRepository tvSatationRepository;

    @Override
    public BooleanDTO addTVGuide(MovieBean movieBean) {
        MoviesEntity movieEntity = movieRepository.findByTmdbId(movieBean.getMovieDb().getId());
        String stationName = "";
        String logoPath = "";
        String date = "";
        String time = "";
        if (movieEntity == null) {
            FilmSearchResult filmResult = null;
            try {
                FilmwebApi fa = new FilmwebApi();
                filmResult = fa.findFilm(movieBean.getMovieDb().getTitle(), Integer.parseInt(movieBean.getMovieDb().getReleaseDate().substring(0, 4))).get(0);
                List<Broadcast> broadcasts = null;
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
            } catch (Exception e) {
                return new BooleanDTO(false);
            }
            movieEntity = new MoviesEntity();
            movieEntity.setMovieName(movieBean.getMovieDb().getTitle());
            movieEntity.setTmdbId(movieBean.getMovieDb().getId());
            movieEntity.setFilmwebId(toIntExact(filmResult.getId()));
            movieEntity.setPosterPath(movieBean.getMovieDb().getBackdropPath());
            movieEntity.setReleaseDate(movieBean.getMovieDb().getReleaseDate());
            movieRepository.save(movieEntity);
        } else {
            FilmwebApi fa = new FilmwebApi();
            FilmSearchResult filmResult = fa.findFilm(movieBean.getMovieDb().getTitle(), Integer.parseInt(movieBean.getMovieDb().getReleaseDate().substring(0, 4))).get(0);
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
                return new BooleanDTO(false);
            }
        }
        if (stationName.equals(""))
            stationName = movieBean.getChanel();
        TvstationsEntity tvstationsEntity = null;
        List<TvstationsEntity> stationsList = tvSatationRepository.findTvstationsEntitiesByName(stationName);

        if (stationsList.size() < 1) {
            tvstationsEntity = new TvstationsEntity();
            tvstationsEntity.setName(stationName);
            tvstationsEntity.setLogoPath(logoPath);
            tvSatationRepository.save(tvstationsEntity);
        } else {
            tvstationsEntity = stationsList.get(0);
        }
        TVGuideEntity tvGuideEntity = new TVGuideEntity();
        tvGuideEntity.setMovieId(movieEntity.getMovieId());
        tvGuideEntity.setDate(movieBean.parseDateToTimestamp());
        tvGuideEntity.setTvstationId(tvstationsEntity.getTvstationId());
        tvGuideEntity.setMoviesByMovieId(movieEntity);
        tvGuideEntity.setTvstationsByTvstationId(tvstationsEntity);
        tvGuideRepository.save(tvGuideEntity);

        return (new BooleanDTO(true));
    }

    @Override
    public boolean deleteTVGuide() {
        List<TVGuideEntity> tvGuideEntityList = tvGuideRepository.findAll();
        for (TVGuideEntity tvGuideEntity : tvGuideEntityList)
                tvGuideRepository.delete(tvGuideEntity);
        return true;
    }
}
