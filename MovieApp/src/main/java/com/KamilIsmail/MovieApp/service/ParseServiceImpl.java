package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.DAO.TVGuideDao;
import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import com.KamilIsmail.MovieApp.scheduled.TVGuideBean.MovieBean;
import com.KamilIsmail.MovieApp.scheduled.TVGuideController.ParseTVGuide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ParseServiceImpl implements ParseService{

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    TVGuideDao tvGuideDao;

    @Override
    public BooleanDTO parseTVGuide() {
        ParseTVGuide tvGuide = new ParseTVGuide();
        ArrayList<MovieBean> movieBeanList = tvGuide.run();

        if (movieBeanList == null)
            return new BooleanDTO(false);

        if (tvGuideDao.deleteTVGuide()) {
            movieBeanList.forEach(p-> tvGuideDao.addTVGuide(p));
        } else
            return new BooleanDTO(false);
        return new BooleanDTO(true);
    }
}
