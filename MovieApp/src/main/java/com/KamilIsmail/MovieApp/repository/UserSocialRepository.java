package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.UserSocialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kamilismail
 * Klasa zawierająca zapytania sql do tabeli usersocial.
 */
public interface UserSocialRepository extends JpaRepository<UserSocialEntity, Integer> {
    /**
     * Zapytanie zwraca użytwkonika ze względu na jego login.
     * @param username
     * @return
     */
    UserSocialEntity findByUsername(String username);

    /**
     * Zapytanie zwraca użytkowinika ze względu na jego id.
     * @param id
     * @return
     */
    UserSocialEntity findByUserSocialId(int id);
}
