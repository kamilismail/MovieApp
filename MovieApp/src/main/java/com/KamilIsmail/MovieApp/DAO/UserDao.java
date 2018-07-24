package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.GetUsernameDTO;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    UserEntity createUser(String username, String password, String role);

    BooleanDTO changeUserPassword(String username, String password, String newPassword);

    BooleanDTO deleteUser(String username, String password);

    GetUsernameDTO getUsername(int id);
}
