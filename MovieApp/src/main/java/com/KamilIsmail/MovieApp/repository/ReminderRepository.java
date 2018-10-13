package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.RemindersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReminderRepository extends JpaRepository<RemindersEntity, Long> {

    List<RemindersEntity> findRemindersEntitiesByUserId(int userid);

    List<RemindersEntity> findRemindersEntitiesByMovieId(int movieId);

    List<RemindersEntity> findRemindersEntitiesByTvstationId(int stationId);

    RemindersEntity findRemindersEntityByReminderId(int reminderId);

    RemindersEntity findRemindersEntityByUserIdAndMovieId(int userID, int movieID);
}
