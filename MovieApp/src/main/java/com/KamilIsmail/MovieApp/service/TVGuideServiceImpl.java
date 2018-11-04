package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DTO.TVGuideMovieDTO;
import com.KamilIsmail.MovieApp.entities.MoviesEntity;
import com.KamilIsmail.MovieApp.entities.TVGuideEntity;
import com.KamilIsmail.MovieApp.entities.TvstationsEntity;
import com.KamilIsmail.MovieApp.repository.TVGuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TVGuideServiceImpl implements TVGuideService {

    @Autowired
    TVGuideRepository tvGuideRepository;

    @Cacheable(value = "tvGuide")
    @Override
    public ArrayList <TVGuideMovieDTO> getTVGuide() {
        ArrayList <TVGuideMovieDTO> getMovieDTOList = new ArrayList<>();
        List<TVGuideEntity> tvGuideEntitiesList = tvGuideRepository.findAll();
        for (TVGuideEntity tvGuideEntity : tvGuideEntitiesList) {
            MoviesEntity moviesEntity = tvGuideEntity.getMoviesByMovieId();
            TvstationsEntity tvstationsEntity = tvGuideEntity.getTvstationsByTvstationId();
            String logoPath = "";
            if(!tvstationsEntity.getLogoPath().isEmpty()) {
                logoPath = Constants.getLogoPath() + tvstationsEntity.getLogoPath();
            }
            getMovieDTOList.add(new TVGuideMovieDTO(moviesEntity.getMediaType(), moviesEntity.getTmdbId().toString(),
                    moviesEntity.getMovieName(), Constants.getPosterPath() + moviesEntity.getBackdropPath(),
                    moviesEntity.getReleaseDate(), moviesEntity.getAvarageRating(), tvstationsEntity.getName(), logoPath,
                    tvGuideEntity.getDate().toString()));
        }
        return getMovieDTOList;
    }
}
