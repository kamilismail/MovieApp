package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.DTO.GetUsernameDTO;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    UserEntity createUser(String username, String password, String role);
    Boolean changeUserPassword(String username, String password, String newPassword);
    Boolean deleteUser(String username, String password);
    GetUsernameDTO getUsername(int id);
}
