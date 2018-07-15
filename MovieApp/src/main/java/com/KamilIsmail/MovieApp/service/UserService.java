package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.GetUsernameDTO;
import com.KamilIsmail.MovieApp.DTO.UserDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {
    @PreAuthorize("hasAnyAuthority('user')")
    UserDTO getUser(String username);
    @PreAuthorize("hasAuthority('admin')")
    List<UserDTO> getAllUser();
    UserDTO createUser(String username, String password, String role);
    @PreAuthorize("hasAnyAuthority('admin','user')")
    Boolean changeUserPassword(String username, String password, String newPassword);
    @PreAuthorize("hasAnyAuthority('admin','user')")
    Boolean deleteUser(String username, String password);
    @PreAuthorize("hasAnyAuthority('admin','user')")
    GetUsernameDTO getUsername(int id);
}
