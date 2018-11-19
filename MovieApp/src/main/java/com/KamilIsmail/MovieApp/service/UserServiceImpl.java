package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DAO.UserDao;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.GetUsernameDTO;
import com.KamilIsmail.MovieApp.DTO.UserDTO;
import com.KamilIsmail.MovieApp.DTO.UserPhotoDTO;
import com.KamilIsmail.MovieApp.entities.PhotosEntity;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kamilismail
 * Serwis wywoływany z kontrolera.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDao userDao;

    /**
     * Metoda zwraca wszystkich użytkowników w systemie.
     * @return
     */
    @Override
    public List<UserDTO> getAllUser() {
        return userRepository.findAll().stream().map(p -> new UserDTO(p.getUserId(), p.getUsername(), p.getRole())).collect(Collectors.toList());
    }

    /**
     * Metoda tworząca nowrgo użytkownika.
     * @param username
     * @param password
     * @param role
     * @return
     */
    @Override
    public GetUsernameDTO createUser(String username, String password, String role) {
        UserEntity userEntity = userDao.createUser(username, password, role);
        return new GetUsernameDTO(userEntity.getUsername());
    }

    /**
     * Metoda zmieniająca hasło użytkownika.
     * @param username
     * @param password
     * @param newPassword
     * @return
     */
    @Override
    public BooleanDTO changeUserPassword(String username, String password, String newPassword) {
        return userDao.changeUserPassword(username, password, newPassword);
    }

    /**
     * Metoda usuwająca konto użytkownika.
     * @param username
     * @param password
     * @return
     */
    @Override
    public BooleanDTO deleteUser(String username, String password) {
        return userDao.deleteUser(username, password);
    }

    /**
     * Metoda zwracająca login użytwkownika.
     * @param id
     * @return
     */
    @Override
    public GetUsernameDTO getUsername(int id) {
        return userDao.getUsername(id);
    }

    /**
     * Metoda ustawiająca danemu użytkownikowi token firebase.
     * @param userID
     * @param token
     * @return
     */
    @Override
    public BooleanDTO setFirebaseID(int userID, String token) {
        return userDao.setFirebaseID(userID, token);
    }

    /**
     * Metoda tworząca nowekonto lub logująca użytkownika logującego się za pomocą Facebooka. Zwraca login użytkownika.
     * @param username
     * @param facebookID
     * @param mail
     * @param role
     * @return
     */
    @Override
    public GetUsernameDTO facebookLogin(String username, String facebookID, String mail, String role) {
        UserEntity userEntity = userRepository.findUserEntityByUsernameAndRole(facebookID, role);
        if (userEntity == null) {
            return userDao.createFacebookUser(username, facebookID, mail, role);
        } else
            return userDao.getFacebookUsername(userEntity.getUserSocialId());
    }

    /**
     * Metoda zwraca link do zdjęcia profilowego.
     * @param userID
     * @return
     */
    @Override
    public UserPhotoDTO facebookPhoto(int userID) {
        PhotosEntity photosEntity = userRepository.findByUserId(userID).getPhotosByPhotoId();
        return new UserPhotoDTO(photosEntity.getPath());
    }

    /**
     * Metoda zapisująca link do zdjęcia profilowego.
     * @param userID
     * @param fileName
     * @return
     */
    @Override
    public BooleanDTO setPhotoName(int userID, String fileName) {
        return userDao.setPhotoName(userID, fileName);
    }

    /**
     * Metoda pozwalająca na usunięcie dowolnego konta administratorowi
     * @param userId
     * @return
     */
    @Override
    public BooleanDTO deleteAdminUser(int userId) {
        return userDao.deleteAdminUser(userId);
    }

    /**
     * Metoda usuwająca konto użytkownikowi zalogowanemu przez Facebooka.
     * @param mail
     * @param userId
     * @return
     */
    @Override
    public BooleanDTO deleteFacebookUser(String mail, int userId) {
        return userDao.deleteFacebookUser(mail, userId);
    }
}
