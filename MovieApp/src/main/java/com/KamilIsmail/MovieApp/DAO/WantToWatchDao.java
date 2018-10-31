package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface WantToWatchDao {
    BooleanDTO addWantToWatch(int userId, int movieId);

    BooleanDTO deleteWantToWatch(int userId, int movieId);
}
