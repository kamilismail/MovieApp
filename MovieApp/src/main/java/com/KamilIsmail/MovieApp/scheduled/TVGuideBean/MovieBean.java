package com.KamilIsmail.MovieApp.scheduled.TVGuideBean;

public class MovieBean {

    private String date;
    private String chanel;
    private String title;
    private String description;
    private int rating;

    public MovieBean(String date, String chanel, String title, String description) {
        this.date = date;
        this.chanel = chanel;
        this.title = title;
        this.description = description;
    }

    public MovieBean(){}

    @Override
    public String toString() {
        return "Movie: " + this.title + " at: " + this.date + " on: " + this.chanel + " rating: "
                + this.rating +"\n description: " + this.description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
