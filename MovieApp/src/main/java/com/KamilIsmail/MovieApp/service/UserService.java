package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.GetUsernameDTO;
import com.KamilIsmail.MovieApp.DTO.UserDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {

    @PreAuthorize("hasAuthority('admin')")
    List<UserDTO> getAllUser();

    UserDTO createUser(String username, String password, String role);

    @PreAuthorize("hasAnyAuthority('admin','user')")
    BooleanDTO changeUserPassword(String username, String password, String newPassword);

    @PreAuthorize("hasAnyAuthority('admin','user')")
    BooleanDTO deleteUser(String username, String password);

    @PreAuthorize("hasAnyAuthority('admin','user')")
    GetUsernameDTO getUsername(int id);

    @PreAuthorize("hasAnyAuthority('admin','user')")
    BooleanDTO setFirebaseID(int userID, String token);
}
