package com.KamilIsmail.MovieApp.DAO;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.scheduled.TVGuideBean.MovieBean;
import org.springframework.stereotype.Repository;

@Repository
public interface TVGuideDao {
    BooleanDTO addTVGuide(MovieBean movieBean);
}
