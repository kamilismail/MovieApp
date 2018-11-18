package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import org.springframework.stereotype.Repository;

/**
 * @author kamilismail
 */
@Repository
public interface RatingDao {
    BooleanDTO setRating(int userId, int movieId, int rating);
}
