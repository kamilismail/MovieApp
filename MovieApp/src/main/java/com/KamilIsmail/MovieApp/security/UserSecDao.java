package com.KamilIsmail.MovieApp.security;

import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author kamilismail
 * Klasa zawierająca zapytanie do repozytorium o użytkownika wyszukanego po loginie.
 */
@Repository
@Transactional
public class UserSecDao {

    @Autowired
    UserRepository userRepository;

    /**
     * Wyszukanie użytkownika po loginie.
     * @param username
     * @return
     */
    UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).get(0);
    }
}
