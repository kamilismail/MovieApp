package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.RemindersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author kamilismail
 * Klasa zawierająca zapytania sql z tabelą reminders.
 */
public interface ReminderRepository extends JpaRepository<RemindersEntity, Long> {

    /**
     * Zapytanie zwraca listę pozycji ze względu na użytkownika.
     * @param userid
     * @return
     */
    List<RemindersEntity> findRemindersEntitiesByUserId(int userid);

    /**
     * Zapytanie zwracające listę pozycji ze względu na pozycję filmową.
     * @param movieId
     * @return
     */
    List<RemindersEntity> findRemindersEntitiesByMovieId(int movieId);

    /**
     * Zapytanie zwracjące listę pozycji ze względu stację tv.
     * @param stationId
     * @return
     */
    List<RemindersEntity> findRemindersEntitiesByTvstationId(int stationId);

    /**
     * Zapytanie zwrcające pozycję ze względu na stację tv.
     * @param stationId
     * @return
     */
    RemindersEntity findRemindersEntityByTvstationId(int stationId);

    /**
     * Zapytanie zwracające pozycję ze względu na id.
     * @param reminderId
     * @return
     */
    RemindersEntity findRemindersEntityByReminderId(int reminderId);

    /**
     * Zapytanie zwracające pozycję ze względu na użytkownika i pozycję filmową.
     * @param userID
     * @param movieID
     * @return
     */
    RemindersEntity findRemindersEntityByUserIdAndMovieId(int userID, int movieID);
}
