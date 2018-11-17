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

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDao userDao;

    @Override
    public List<UserDTO> getAllUser() {
        return userRepository.findAll().stream().map(p -> new UserDTO(p.getUserId(), p.getUsername(), p.getRole())).collect(Collectors.toList());
    }

    @Override
    public UserDTO createUser(String username, String password, String role) {
        UserEntity userEntity = userDao.createUser(username, password, role);
        return new UserDTO(userEntity.getUserId(), userEntity.getUsername(), userEntity.getRole());
    }

    @Override
    public BooleanDTO changeUserPassword(String username, String password, String newPassword) {
        return userDao.changeUserPassword(username, password, newPassword);
    }

    @Override
    public BooleanDTO deleteUser(String username, String password) {
        return userDao.deleteUser(username, password);
    }

    @Override
    public GetUsernameDTO getUsername(int id) {
        return userDao.getUsername(id);
    }

    @Override
    public BooleanDTO setFirebaseID(int userID, String token) {
        return userDao.setFirebaseID(userID, token);
    }

    @Override
    public GetUsernameDTO facebookLogin(String username, String facebookID, String mail, String role) {
        UserEntity userEntity = userRepository.findUserEntityByUsernameAndRole(facebookID, role);
        if (userEntity == null) {
            return userDao.createFacebookUser(username, facebookID, mail, role);
        } else
            return userDao.getFacebookUsername(userEntity.getUserSocialId());
    }

    @Override
    public UserPhotoDTO facebookPhoto(int userID) {
        PhotosEntity photosEntity = userRepository.findByUserId(userID).getPhotosByPhotoId();
        return new UserPhotoDTO(photosEntity.getPath());
    }

    @Override
    public BooleanDTO setPhotoName(int userID, String fileName) {
        return userDao.setPhotoName(userID, fileName);
    }

    @Override
    public BooleanDTO deleteAdminUser(int userId) {
        return userDao.deleteAdminUser(userId);
    }

    @Override
    public BooleanDTO deleteFacebookUser(String mail, int userId) {
        return userDao.deleteFacebookUser(mail, userId);
    }
}
