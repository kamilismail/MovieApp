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

    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(12);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);
        return (hashed_password);
    }

    @Override
    public UserEntity createUser(String username, String password, String role) {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(hashPassword(password));
        userEntity.setRole(role);
        PhotosEntity photosEntity = new PhotosEntity();
        photosEntity.setPath("");
        photosEntity = photoRepository.save(photosEntity);
        userEntity.setPhotosByPhotoId(photosEntity);
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
            PhotosEntity photosEntity = userEntity.getPhotosByPhotoId();
            photoRepository.delete(photosEntity);

            List<RatingsEntity> ratingsList = ratingRepository.findRatingsEntityByUserId(userEntity.getUserId());
            for (RatingsEntity rating : ratingsList)
                ratingRepository.delete(rating);

            List<WanttowatchEntity> wanttowatchList = wantToWatchRepository.findWanttowatchEntityByUserId(userEntity.getUserId());
            for (WanttowatchEntity want : wanttowatchList)
                wantToWatchRepository.delete(want);

            List<FavouritesEntity> favList = favouriteRepository.findFavouritesEntityByUserId(userEntity.getUserId());
            for (FavouritesEntity favouritesEntity : favList)
                favouriteRepository.delete(favouritesEntity);

            List<RemindersEntity> remindersEntityList = reminderRepository.findRemindersEntitiesByUserId(userEntity.getUserId());
            for (RemindersEntity remindersEntity : remindersEntityList)
                reminderRepository.delete(remindersEntity);

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
}
