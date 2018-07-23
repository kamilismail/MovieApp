package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.WanttowatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WantToWatchRepository extends JpaRepository<WanttowatchEntity, Long> {
    List<WanttowatchEntity> findWanttowatchEntityByUserId(int userid);

    List<WanttowatchEntity> findByMovieId(int movieId);
}
