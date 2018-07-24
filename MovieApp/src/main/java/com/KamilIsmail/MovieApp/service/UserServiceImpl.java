package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DAO.UserDao;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.GetUsernameDTO;
import com.KamilIsmail.MovieApp.DTO.UserDTO;
import com.KamilIsmail.MovieApp.entities.UserEntity;
import com.KamilIsmail.MovieApp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDao userDao;

    @Override
    public UserDTO getUser(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).get(0);
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userEntity, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userList = new ArrayList<>();
        for (UserEntity userEntity : userEntityList) {
            userList.add(new UserDTO(userEntity.getUserId(), userEntity.getUsername(), userEntity.getRole()));
        }
        return userList;
    }

    @Override
    public UserDTO createUser(String username, String password, String role) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userDao.createUser(username, password, role), UserDTO.class);
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
}
