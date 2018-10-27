package com.KamilIsmail.MovieApp.repository;

import com.KamilIsmail.MovieApp.entities.UserSocialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSocialRepository extends JpaRepository<UserSocialEntity, Integer> {
    UserSocialEntity findByUsername(String username);
    UserSocialEntity findByUserSocialId(int id);
}
