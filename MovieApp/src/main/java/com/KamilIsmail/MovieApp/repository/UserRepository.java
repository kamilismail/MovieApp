package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author kamilismail
 * Klasa zaiwerająca zapytania sql do tabeli users.
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * Zapytanie zwracające listę użytkowników ze względu na login.
     * @param username
     * @return
     */
    List<UserEntity> findByUsername(String username);

    /**
     * Zapytanie zwracające użytkownika ze względu na jego id.
     * @param id
     * @return
     */
    UserEntity findByUserId(int id);

    /**
     * Zapytanie zwracające użytkownika ze względu na login i jego rolę.
     * @param username
     * @param role
     * @return
     */
    UserEntity findUserEntityByUsernameAndRole(String username, String role);
}
