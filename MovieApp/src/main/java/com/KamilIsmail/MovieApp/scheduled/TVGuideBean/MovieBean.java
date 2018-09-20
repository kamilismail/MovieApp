package com.KamilIsmail.MovieApp.scheduled.TVGuideBean;

import info.talacha.filmweb.api.FilmwebApi;
import info.talacha.filmweb.connection.FilmwebException;
import info.talacha.filmweb.models.Film;
import info.talacha.filmweb.search.models.FilmSearchResult;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class MovieBean {

    private Date date;
    private String chanel;
    private String title;
    private String description;
    private float rating;
    private int productionYear;
    private int year = 1950;

    private FilmwebApi fa;
    FilmSearchResult filmResult = null;

    public MovieBean(Date date, String chanel, String title, String description) {
        this.date = date;
        this.chanel = chanel;
        this.title = title;
        this.description = description;

        this.fa = new FilmwebApi();
    }

    public MovieBean(){}

    public void setFilmwebRating() {
        filmResult = fa.findFilm(this.title, this.productionYear).get(0);
        long id = filmResult.getId();
        try {
            Film f = fa.getFilmData(id);
            this.rating = f.getRate();
        } catch (FilmwebException e) {
            this.rating = -1;
        }
    }

    public void parseYear() {
        int actualYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year; i <= actualYear; i++) {
            String str = " " + Integer.toString(i) + ".";
            if (this.description.contains(str)) {
                this.productionYear = i;
                return;
            }
        }
        this.productionYear = -1;
    }

    @Override
    public String toString() {
        return "Movie: " + this.title + " date: " + Integer.toString(this.productionYear) + " at: " + this.date.toString() + " on: " + this.chanel + " rating: "
                + this.rating +"\n description: " + this.description;
    }

    public Date getDate() {
        return date;
    }

    public Timestamp parseDateToTimestamp () {
        return new java.sql.Timestamp(this.date.getTime());
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }
}
