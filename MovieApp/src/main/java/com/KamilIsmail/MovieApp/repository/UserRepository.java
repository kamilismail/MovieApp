package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByUsername(String username);

    UserEntity findByUserId(long id);
}
