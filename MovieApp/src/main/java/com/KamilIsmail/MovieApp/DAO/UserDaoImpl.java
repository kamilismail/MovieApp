package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.DTO.GetUsernameDTO;
import com.KamilIsmail.MovieApp.entities.PhotosEntity;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.repository.PhotoRepository;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDaoImpl implements UserDao {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoRepository photoRepository;

    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(12);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);
        return(hashed_password);
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
    public Boolean changeUserPassword(String username, String password, String newPassword) {
        final UserEntity userEntity = userRepository.findByUsername(username).get(0);
        final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        if(pwEncoder.matches(password, userEntity.getPassword())) {
            userEntity.setPassword(hashPassword(newPassword));
            userRepository.save(userEntity);
            return true;
        }
        else
            return false;
    }

    @Override
    public Boolean deleteUser(String username, String password) {
        final UserEntity userEntity = userRepository.findByUsername(username).get(0);
        final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        if(pwEncoder.matches(password, userEntity.getPassword())) {
            userRepository.deleteById((long) userEntity.getUserId());
            return true;
        }
        else
            return false;
    }

    @Override
    public GetUsernameDTO getUsername(int id) {
        return new GetUsernameDTO(userRepository.findByUserId(id).getUsername());
    }
}
