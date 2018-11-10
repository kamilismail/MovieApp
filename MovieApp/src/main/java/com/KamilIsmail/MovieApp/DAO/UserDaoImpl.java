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

    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(12);
        return (BCrypt.hashpw(password_plaintext, salt));
    }

    @Override
    public UserEntity createUser(String username, String password, String role) {
        PhotosEntity photosEntity = new PhotosEntity("");
        photosEntity = photoRepository.save(photosEntity);
        UserEntity userEntity = new UserEntity(username, hashPassword(password), role, photosEntity, null);
        userEntity = userRepository.save(userEntity);
        return userEntity;
    }

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

    @Override
    public BooleanDTO deleteUser(String username, String password) {
        final UserEntity userEntity = userRepository.findByUsername(username).get(0);
        final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        if (pwEncoder.matches(password, userEntity.getPassword())) {
            photoRepository.delete(userEntity.getPhotosByPhotoId());
            ratingRepository.findRatingsEntityByUserId(userEntity.getUserId()).forEach(p -> ratingRepository.delete(p));
            wantToWatchRepository.findWanttowatchEntityByUserId(userEntity.getUserId()).forEach(p -> wantToWatchRepository.delete(p));
            favouriteRepository.findFavouritesEntityByUserId(userEntity.getUserId()).forEach(p -> favouriteRepository.delete(p));
            reminderRepository.findRemindersEntitiesByUserId(userEntity.getUserId()).forEach(p -> reminderRepository.delete(p));
            if (userEntity.getUserSocialId() != null)
                userSocialRepository.delete(userSocialRepository.findByUserSocialId(userEntity.getUserSocialId()));
            userRepository.deleteById(userEntity.getUserId());
            return new BooleanDTO(true);
        } else
            return new BooleanDTO(false);
    }

    @Override
    public GetUsernameDTO getUsername(int id) {
        return new GetUsernameDTO(userRepository.findByUserId(id).getUsername());
    }

    @Override
    public BooleanDTO setFirebaseID(int id, String token) {
        final UserEntity userEntity = userRepository.findByUserId(id);
        userEntity.setNotificationId(token);
        userRepository.save(userEntity);
        return new BooleanDTO(true);
    }

    @Override
    public GetUsernameDTO getFacebookUsername(int id) {
        return new GetUsernameDTO(userSocialRepository.findByUserSocialId(id).getUsername());
    }

    @Override
    public GetUsernameDTO createFacebookUser(String username, String facebookID, String mail, String role) {
        PhotosEntity photosEntity = new PhotosEntity("https://graph.facebook.com/"+facebookID+"/picture?type=large");
        photoRepository.save(photosEntity);
        UserSocialEntity userSocialEntity = new UserSocialEntity(username, mail, facebookID);
        userSocialRepository.save(userSocialEntity);
        UserEntity userEntity = new UserEntity(facebookID, null, role, photosEntity, userSocialEntity.getUserSocialId(),
                userSocialEntity);
        userRepository.save(userEntity);
        return new GetUsernameDTO(username);
    }

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
}
