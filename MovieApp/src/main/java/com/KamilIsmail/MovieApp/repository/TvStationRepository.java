package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.TvstationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author kamilismail
 * Klasa zawierająca zapytania sql do tabeli tvstations.
 */
public interface TvStationRepository extends JpaRepository<TvstationsEntity, Long> {
    /**
     * Zapytanie zwaracające listę pozycji ze względu na nazwę stacji.
     * @param name
     * @return
     */
    List<TvstationsEntity> findTvstationsEntitiesByName(String name);

    /**
     * Zapytanie zwracające pozycji ze względu na id.
     * @param id
     * @return
     */
    TvstationsEntity findTvstationsEntityByTvstationId(int id);
}
