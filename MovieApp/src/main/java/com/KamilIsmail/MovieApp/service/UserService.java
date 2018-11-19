package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.GetUsernameDTO;
import com.KamilIsmail.MovieApp.DTO.UserDTO;
import com.KamilIsmail.MovieApp.DTO.UserPhotoDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author kamilismail
 */
public interface UserService {

    @PreAuthorize("hasAuthority('admin')")
    List<UserDTO> getAllUser();

    GetUsernameDTO createUser(String username, String password, String role);

    @PreAuthorize("hasAnyAuthority('admin','user')")
    BooleanDTO changeUserPassword(String username, String password, String newPassword);

    @PreAuthorize("hasAnyAuthority('admin','user')")
    BooleanDTO deleteUser(String username, String password);

    @PreAuthorize("hasAnyAuthority('admin','user')")
    GetUsernameDTO getUsername(int id);

    @PreAuthorize("hasAnyAuthority('admin','user', 'facebook')")
    BooleanDTO setFirebaseID(int userID, String token);

    GetUsernameDTO facebookLogin(String username, String facebookID, String mail, String role);

    @PreAuthorize("hasAnyAuthority('admin','facebook', 'user')")
    UserPhotoDTO facebookPhoto(int userID);

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    BooleanDTO setPhotoName(int userID, String fileName);

    @PreAuthorize("hasAnyAuthority('admin')")
    BooleanDTO deleteAdminUser(int userId);

    @PreAuthorize("hasAnyAuthority('facebook')")
    BooleanDTO deleteFacebookUser(String mail, int userId);
}
