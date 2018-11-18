package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.TVGuideEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author kamilismail
 * Klasa zawierająca zapytania sql do tabeli tvguide.
 */
public interface TVGuideRepository extends JpaRepository<TVGuideEntity, Long> {
    /**
     * Zapytanie zawrca pozycję ze względu na id.
     * @param tvguideid
     * @return
     */
    TVGuideEntity findTVGuideEntityByTvguideId(int tvguideid);

    /**
     * Zapytanie zwraca listę pozycji ze względu na id.
     * @param id
     * @return
     */
    List<TVGuideEntity> findTVGuideEntitiesByTvguideId(int id);

    /**
     * Zapytanie zwraca listę pozycji ze względu na pozycję filmową.
     * @param id
     * @return
     */
    List<TVGuideEntity> findTVGuideEntitiesByMovieId(int id);

    /**
     * Zapytanie zawraca listę pozycji ze względu pozycję stacji tv.
     * @param id
     * @return
     */
    List<TVGuideEntity> findTVGuideEntitiesByTvstationId(int id);
}
