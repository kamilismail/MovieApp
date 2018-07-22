package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface WantToWatchDao {
    BooleanDTO addFavourite(int userId, int movieId);
    BooleanDTO deleteFavourite(int userId, int movieId);
}
