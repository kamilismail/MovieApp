package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DAO.MovieCommentsDao;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.MovieCommentsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieCommentsServiceImpl implements MovieCommentsService {

    @Autowired
    MovieCommentsDao movieCommentsDao;

    @Override
    public List<MovieCommentsDTO> getComments(int movieId) {
        return movieCommentsDao.getComments(movieId);
    }

    @Override
    public BooleanDTO addComment(int userId, String comment, int movieId) {
        return movieCommentsDao.addComment(userId, comment, movieId);
    }

    @Override
    public BooleanDTO deleteComment(int userId, int movieId) {
        return movieCommentsDao.deleteComment(userId, movieId);
    }
}
