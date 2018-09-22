package com.KamilIsmail.MovieApp.DAO;

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

import java.sql.Timestamp;
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
        FilmwebApi fa = new FilmwebApi();
        if (movieEntity == null) {

            FilmSearchResult filmResult = fa.findFilm(movieBean.getMovieDb().getTitle(), Integer.parseInt(movieBean.getMovieDb().getReleaseDate().substring(0, 4))).get(0);
            movieEntity = new MoviesEntity();
            movieEntity.setMovieName(movieBean.getMovieDb().getTitle());
            movieEntity.setTmdbId(movieBean.getMovieDb().getId());
            movieEntity.setFilmwebId(toIntExact(filmResult.getId()));
            movieEntity.setPosterPath(movieBean.getMovieDb().getPosterPath());
            movieEntity.setReleaseDate(movieBean.getMovieDb().getReleaseDate());
            movieRepository.save(movieEntity);
        }
        List <TvstationsEntity> tvstationsEntityList = tvSatationRepository.findTvstationsEntitiesByName(movieBean.getChanel());
        TvstationsEntity tvstationsEntity;
        if (tvstationsEntityList.size() < 1) {
            tvstationsEntity = new TvstationsEntity();
            List<Broadcast> broadcasts = null;
            try {
                broadcasts = fa.getBroadcasts(movieEntity.getFilmwebId().longValue(), 0, 20);
                if (!broadcasts.isEmpty()) {
                    Long chanelID = broadcasts.get(0).getChannelId();
                    List<TVChannel> tvChannels = fa.getTvChannels();
                    for (TVChannel tvChannel : tvChannels) {
                        if (tvChannel.getId() == chanelID) {
                            tvstationsEntity.setName(tvChannel.getName());
                            tvstationsEntity.setLogoPath(tvChannel.getLogo(Size.SMALL).getPath());
                            tvSatationRepository.save(tvstationsEntity);
                            break;
                        }
                    }
                }
            } catch (FilmwebException e) {
                e.printStackTrace();
            }
        } else
            tvstationsEntity = tvstationsEntityList.get(0);

        TVGuideEntity tvGuideEntity = new TVGuideEntity();
        tvGuideEntity.setDate(movieBean.parseDateToTimestamp());
        tvGuideEntity.setMovieId(movieEntity.getMovieId());
        tvGuideEntity.setMoviesByMovieId(movieEntity);
        tvGuideEntity.setTvstationId(tvstationsEntity.getTvstationId());
        tvGuideEntity.setTvstationsByTvstationId(tvstationsEntity);
        tvGuideRepository.save(tvGuideEntity);
        return (new BooleanDTO(true));
    }
}
