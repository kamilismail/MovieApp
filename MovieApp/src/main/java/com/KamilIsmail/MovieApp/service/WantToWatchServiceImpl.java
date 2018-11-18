package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DAO.WantToWatchDao;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverMovieDTO;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.WanttowatchEntity;
import com.KamilIsmail.MovieApp.repository.MovieRepository;
import com.KamilIsmail.MovieApp.repository.WantToWatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kamilismail
 * Serwis wywoływany z kontrolera.
 */
@Service
public class WantToWatchServiceImpl implements WantToWatchService {
    @Autowired
    WantToWatchRepository wantRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    WantToWatchDao wantDao;

    /**
     * Metoda zwraca listę pozycji filmowych dodanych do tabeli wanttowatch danego użytkownika.
     * @param userid
     * @return
     */
    @Override
    public List<DiscoverMovieDTO> getWants(int userid) {
        return wantRepository.findWanttowatchEntityByUserId(userid).stream()
                .map(p-> new DiscoverMovieDTO(p.getMoviesByMovieId().getMediaType(), Integer.toString(p.getMoviesByMovieId().getTmdbId()),
                    p.getMoviesByMovieId().getMovieName(), Constants.getPosterPath() + p.getMoviesByMovieId().getBackdropPath(),
                    p.getMoviesByMovieId().getReleaseDate(), p.getMoviesByMovieId().getAvarageRating()))
                .collect(Collectors.toList());
    }

    /**
     * Metoda dodająca daną pozycję filmową do listy wanttowatch.
     * @param userid
     * @param movieId
     * @return
     */
    @Override
    public BooleanDTO addWant(int userid, int movieId) {
        try {
            MoviesEntity moviesEntity = movieRepository.findByTmdbId(movieId);
            if (moviesEntity == null)
                return wantDao.addWantToWatch(userid, movieId);
            if (wantRepository.findWanttowatchEntityByMovieIdAndUserId(moviesEntity.getMovieId(), userid) != null)
                return new BooleanDTO(false);
            else
                return wantDao.addWantToWatch(userid, movieId);
        } catch (Exception e) {
            return new BooleanDTO(false);
        }
    }

    /**
     * Metoda usuwająca daną pozycję filmową z listy wanttowatch.
     * @param userid
     * @param movieId
     * @return
     */
    @Override
    public BooleanDTO deleteWant(int userid, int movieId) {
        return wantDao.deleteWantToWatch(userid, movieId);
    }
}
