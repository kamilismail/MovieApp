package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.TvstationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TvSatationRepository extends JpaRepository<TvstationsEntity, Long> {
    List<TvstationsEntity> findTvstationsEntitiesByName(String name);
}
