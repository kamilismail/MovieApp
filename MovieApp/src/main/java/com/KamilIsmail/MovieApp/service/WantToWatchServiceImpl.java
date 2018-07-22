package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DAO.WantToWatchDao;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverMovieDTO;
import com.KamilIsmail.MovieApp.entities.WanttowatchEntity;
import com.KamilIsmail.MovieApp.repository.WantToWatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WantToWatchServiceImpl implements WantToWatchService {
    @Autowired
    WantToWatchRepository wantRepository;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    WantToWatchDao wantDao;

    @Override
    public List<DiscoverMovieDTO> getFavourites(int userid) throws IOException {
        List<WanttowatchEntity> wantEntitiesList = wantRepository.findWanttowatchEntityByUserId(userid);
        List<DiscoverMovieDTO> wantResults = new ArrayList<>();

        for(WanttowatchEntity wantList : wantEntitiesList){
            DiscoverMovieDTO result = new DiscoverMovieDTO("MOVIE", Integer.toString(wantList.getMoviesByMovieId().getTmdbId()),wantList.getMoviesByMovieId().getMovieName(), wantList.getMoviesByMovieId().getPosterPath(),wantList.getMoviesByMovieId().getReleaseDate());
            wantResults.add(result);
        }
        return wantResults;
    }

    @Override
    public BooleanDTO addFavourite(int userid, int movieId) {
        try {
            List<WanttowatchEntity> favsEntitiesList = wantRepository.findWanttowatchEntityByUserId(userid);
            for(WanttowatchEntity favList : favsEntitiesList){
                if(favList.getMoviesByMovieId().getMovieId() == movieId)
                    return new BooleanDTO(false);
            }
            return wantDao.addFavourite(userid, movieId);

        } catch (Exception e) {
            return new BooleanDTO(false);
        }
    }

    @Override
    public BooleanDTO deleteFavourite(int userid, int movieId) {
        return wantDao.deleteFavourite(userid,movieId);
    }
}
