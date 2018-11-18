package com.KamilIsmail.MovieApp.helpers;

import com.KamilIsmail.MovieApp.DTO.BooleanDTO;
import info.talacha.filmweb.api.FilmwebApi;
import info.talacha.filmweb.connection.FilmwebException;
import info.talacha.filmweb.models.Broadcast;
import info.talacha.filmweb.models.Size;
import info.talacha.filmweb.models.TVChannel;
import info.talacha.filmweb.search.models.FilmSearchResult;

import java.util.List;
import java.util.Optional;

/**
 * @author kamilismail
 * Klasa pozwaljąca na łatwiejsze sprawdzenie emisji danego filmu w tv.
 */
public class BroadcastsHelper {

    private String stationName;
    private String logoPath;
    private String date;
    private String time;
    private Long filmwebID;

    private String title;
    private Integer year;
    private int movieID;

    public BroadcastsHelper(String title, String year) {
        this.title = title;
        this.year = Integer.parseInt(year.substring(0, 4));
    }

    public BroadcastsHelper(int movieId) {
        this.movieID = movieId;
    }

    /**
     * Metoda sprawdzająca czy dany film jest już w programie tv.
     * @return
     */
    public Boolean getBroadcastById(){
        try {
            FilmwebApi fa = new FilmwebApi();
            List<Broadcast> broadcasts = fa.getBroadcasts((long) movieID, 0, 20);
            if (!broadcasts.isEmpty()) {
                Long chanelID = broadcasts.get(0).getChannelId();
                List<TVChannel> tvChannels = fa.getTvChannels();
                for (TVChannel tvChannel : tvChannels) {
                    if (tvChannel.getId() == chanelID) {
                        stationName = tvChannel.getName();
                        logoPath = tvChannel.getLogo(Size.SMALL).getPath();
                        date = broadcasts.get(0).getDate().toString();
                        time = broadcasts.get(0).getTime().toString();
                        break;
                    }
                }
            }
        } catch (FilmwebException e) {
            return false;
        }
        return true;
    }

    /**
     * Metoda sprawdzająca czy dany film jest już w programie tv.
     * @return
     */
    public Boolean getBroadcast(){
        Optional<FilmSearchResult> filmResult;
        try {
            FilmwebApi fa = new FilmwebApi();
            List <FilmSearchResult> list = fa.findFilm(title, year);
            filmResult = list.stream().filter(p -> p.getTitle().equals(title)).findFirst();
            List<Broadcast> broadcasts;
            broadcasts = fa.getBroadcasts(filmResult.get().getId(), 0, 20);
            if (!broadcasts.isEmpty()) {
                Long chanelID = broadcasts.get(0).getChannelId();
                List<TVChannel> tvChannels = fa.getTvChannels();
                for (TVChannel tvChannel : tvChannels) {
                    if (tvChannel.getId() == chanelID) {
                        stationName = tvChannel.getName();
                        logoPath = tvChannel.getLogo(Size.SMALL).getPath();
                        date = broadcasts.get(0).getDate().toString();
                        time = broadcasts.get(0).getTime().toString();
                        filmwebID = filmResult.get().getId();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String getStationName() {
        return stationName;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Long getFilmwebID() {
        return filmwebID;
    }
}
