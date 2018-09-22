package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.TVGuideEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TVGuideRepository extends JpaRepository<TVGuideEntity, Long> {
    TVGuideEntity findTVGuideEntityByTvguideId(int tvguideid);
    List<TVGuideEntity> findTVGuideEntitiesByTvguideId(int id);
    List<TVGuideEntity> findTVGuideEntitiesByMovieId(int id);
    List<TVGuideEntity> findTVGuideEntitiesByTvstationId(int id);
}
