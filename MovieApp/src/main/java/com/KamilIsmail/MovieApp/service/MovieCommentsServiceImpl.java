package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DAO.MovieCommentsDao;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.MovieCommentsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kamilismail
 * Serwis wywoływany z kontrolera.
 */
@Service
public class MovieCommentsServiceImpl implements MovieCommentsService {

    @Autowired
    MovieCommentsDao movieCommentsDao;

    /**
     * Metoda zwracająca wszystkei komentarze dla danej pozycji filmowej.
     * @param movieId
     * @return
     */
    @Override
    public List<MovieCommentsDTO> getComments(int movieId) {
        return movieCommentsDao.getComments(movieId);
    }

    /**
     * Metoda dodająca komentarz użytkownika do filmu.
     * @param userId
     * @param comment
     * @param movieId
     * @return
     */
    @Override
    public BooleanDTO addComment(int userId, String comment, int movieId) {
        return movieCommentsDao.addComment(userId, comment, movieId);
    }

    /**
     * Metoda usuwająca komentarz użytkownika z danego filmu.
     * @param userId
     * @param movieId
     * @return
     */
    @Override
    public BooleanDTO deleteComment(int userId, int movieId) {
        return movieCommentsDao.deleteComment(userId, movieId);
    }
}
