package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.GetUsernameDTO;
import com.KamilIsmail.MovieApp.entities.*;
import com.KamilIsmail.MovieApp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kamilismail
 * Klasa odpowiadająca za obsługę połączenia z bazą danych. Komunikacja z tabelami dotyczącymi użytkowników.
 */
@Service
public class UserDaoImpl implements UserDao {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    WantToWatchRepository wantToWatchRepository;
    @Autowired
    FavouriteRepository favouriteRepository;
    @Autowired
    ReminderRepository reminderRepository;
    @Autowired
    UserSocialRepository userSocialRepository;
    @Autowired
    MovieCommentsRepository movieCommentsRepository;

    /**
     * Metoda hashująca przekazay String.
     * @param password_plaintext
     * @return
     */
    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(12);
        return (BCrypt.hashpw(password_plaintext, salt));
    }

    /**
     * Metoda tworząca użytkownika.
     * @param username
     * @param password
     * @param role
     * @return
     */
    @Override
    public UserEntity createUser(String username, String password, String role) {
        PhotosEntity photosEntity = new PhotosEntity("");
        photosEntity = photoRepository.save(photosEntity);
        UserEntity userEntity = new UserEntity(username, hashPassword(password), role, photosEntity, null);
        userEntity = userRepository.save(userEntity);
        return userEntity;
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
        final UserEntity userEntity = userRepository.findByUsername(username).get(0);
        final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        if (pwEncoder.matches(password, userEntity.getPassword())) {
            userEntity.setPassword(hashPassword(newPassword));
            userRepository.save(userEntity);
            return new BooleanDTO(true);
        } else
            return new BooleanDTO(false);
    }

    /**
     * Metoda usuwająca konto użytkownika. Usuwa wszystkie powiązane z nim dane.
     * @param username
     * @param password
     * @return
     */
    @Override
    public BooleanDTO deleteUser(String username, String password) {
        final UserEntity userEntity = userRepository.findByUsername(username).get(0);
        final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        if (pwEncoder.matches(password, userEntity.getPassword())) {
            reminderRepository.findRemindersEntitiesByUserId(userEntity.getUserId()).forEach(p -> reminderRepository.delete(p));
            favouriteRepository.findFavouritesEntityByUserId(userEntity.getUserId()).forEach(p -> favouriteRepository.delete(p));
            movieCommentsRepository.findMovieCommentsEntitiesByUserId(userEntity.getUserId()).forEach(p -> movieCommentsRepository.delete(p));
            ratingRepository.findRatingsEntityByUserId(userEntity.getUserId()).forEach(p -> ratingRepository.delete(p));
            wantToWatchRepository.findWanttowatchEntityByUserId(userEntity.getUserId()).forEach(p -> wantToWatchRepository.delete(p));
            userRepository.deleteById(userEntity.getUserId());
            if (userEntity.getUserSocialId() != null)
                userSocialRepository.delete(userSocialRepository.findByUserSocialId(userEntity.getUserSocialId()));
            photoRepository.delete(userEntity.getPhotosByPhotoId());
            return new BooleanDTO(true);
        } else
            return new BooleanDTO(false);
    }

    /**
     * Metoda zwracająca nazwę użytkownika.
     * @param id
     * @return
     */
    @Override
    public GetUsernameDTO getUsername(int id) {
        return new GetUsernameDTO(userRepository.findByUserId(id).getUsername());
    }

    /**
     * Metoda zapisują nowy token firebase, służacy do wysyłania powiadomień na telefon użytkownika.
     * @param id
     * @param token
     * @return
     */
    @Override
    public BooleanDTO setFirebaseID(int id, String token) {
        final UserEntity userEntity = userRepository.findByUserId(id);
        userEntity.setNotificationId(token);
        userRepository.save(userEntity);
        return new BooleanDTO(true);
    }

    /**
     * Metoda zwracająca nazwę użytkownika zalogowanego przez Facebooka.
     * @param id
     * @return
     */
    @Override
    public GetUsernameDTO getFacebookUsername(int id) {
        return new GetUsernameDTO(userSocialRepository.findByUserSocialId(id).getUsername());
    }

    /**
     * Metoda tworzy konto użytkownikowi zalogowanemu przez Facebooka.
     * @param username
     * @param facebookID
     * @param mail
     * @param role
     * @return
     */
    @Override
    public GetUsernameDTO createFacebookUser(String username, String facebookID, String mail, String role) {
        PhotosEntity photosEntity = new PhotosEntity("https://graph.facebook.com/"+facebookID+"/picture?type=large");
        photoRepository.save(photosEntity);
        UserSocialEntity userSocialEntity = new UserSocialEntity(username, hashPassword(mail), facebookID);
        userSocialRepository.save(userSocialEntity);
        UserEntity userEntity = new UserEntity(facebookID, null, role, photosEntity, userSocialEntity.getUserSocialId(),
                userSocialEntity);
        userRepository.save(userEntity);
        return new GetUsernameDTO(username);
    }

    /**
     * Metoda zapiusują url zdjęcia profilowego użytkownika.
     * @param userID
     * @param fileName
     * @return
     */
    @Override
    public BooleanDTO setPhotoName(int userID, String fileName) {
        PhotosEntity photosEntity = userRepository.findByUserId(userID).getPhotosByPhotoId();
        if (photosEntity == null)
            photoRepository.save(new PhotosEntity(fileName));
        else {
            photosEntity.setPath(fileName);
            photoRepository.save(photosEntity);
        }
        return new BooleanDTO(true);
    }

    /**
     * Metoda pozwalająca administratorowi usunięcie jakiegokolwiek konta.
     * @param userId
     * @return
     */
    @Override
    public BooleanDTO deleteAdminUser(int userId) {
        final UserEntity userEntity = userRepository.findByUserId(userId);
        reminderRepository.findRemindersEntitiesByUserId(userEntity.getUserId()).forEach(p -> reminderRepository.delete(p));
        favouriteRepository.findFavouritesEntityByUserId(userEntity.getUserId()).forEach(p -> favouriteRepository.delete(p));
        movieCommentsRepository.findMovieCommentsEntitiesByUserId(userEntity.getUserId()).forEach(p -> movieCommentsRepository.delete(p));
        ratingRepository.findRatingsEntityByUserId(userEntity.getUserId()).forEach(p -> ratingRepository.delete(p));
        wantToWatchRepository.findWanttowatchEntityByUserId(userEntity.getUserId()).forEach(p -> wantToWatchRepository.delete(p));
        userRepository.deleteById(userEntity.getUserId());
        if (userEntity.getUserSocialId() != null)
            userSocialRepository.delete(userSocialRepository.findByUserSocialId(userEntity.getUserSocialId()));
        photoRepository.delete(userEntity.getPhotosByPhotoId());
        return new BooleanDTO(true);
    }

    /**
     * Metoda usuwająca konto użytkownika zalogowanego przez Facebooka.
     * @param mail
     * @param userId
     * @return
     */
    @Override
    public BooleanDTO deleteFacebookUser(String mail, int userId) {
        final UserEntity userEntity = userRepository.findByUserId(userId);
        final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        if (pwEncoder.matches(mail, userEntity.getUserSocialByUserSocialId().getMail())) {
            reminderRepository.findRemindersEntitiesByUserId(userEntity.getUserId()).forEach(p -> reminderRepository.delete(p));
            favouriteRepository.findFavouritesEntityByUserId(userEntity.getUserId()).forEach(p -> favouriteRepository.delete(p));
            movieCommentsRepository.findMovieCommentsEntitiesByUserId(userEntity.getUserId()).forEach(p -> movieCommentsRepository.delete(p));
            ratingRepository.findRatingsEntityByUserId(userEntity.getUserId()).forEach(p -> ratingRepository.delete(p));
            wantToWatchRepository.findWanttowatchEntityByUserId(userEntity.getUserId()).forEach(p -> wantToWatchRepository.delete(p));
            userRepository.deleteById(userEntity.getUserId());
            if (userEntity.getUserSocialId() != null)
                userSocialRepository.delete(userSocialRepository.findByUserSocialId(userEntity.getUserSocialId()));
            photoRepository.delete(userEntity.getPhotosByPhotoId());
            return new BooleanDTO(true);
        } else
            return new BooleanDTO(false);
    }
}
