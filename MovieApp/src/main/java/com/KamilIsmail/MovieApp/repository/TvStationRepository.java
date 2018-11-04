package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.TvstationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TvStationRepository extends JpaRepository<TvstationsEntity, Long> {
    List<TvstationsEntity> findTvstationsEntitiesByName(String name);

    TvstationsEntity findTvstationsEntityByTvstationId(int id);
}
