package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.DTO.MovieCommentsDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieCommentsDao {
    List<MovieCommentsDTO> getComments(int movieId);

    BooleanDTO addComment(int userId, String comment, int movieId);

    BooleanDTO deleteComment(int userid, int movieId);
}
