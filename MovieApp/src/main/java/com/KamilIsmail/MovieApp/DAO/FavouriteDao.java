package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import org.springframework.stereotype.Repository;

/**
 * @author kamilismail
 */
@Repository
public interface FavouriteDao {
    BooleanDTO addFavourite(int userId, int movieId);

    BooleanDTO deleteFavourite(int userId, int movieId);
}
