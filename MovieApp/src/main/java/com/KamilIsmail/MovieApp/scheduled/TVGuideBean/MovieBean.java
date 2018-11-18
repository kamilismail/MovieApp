package com.KamilIsmail.MovieApp.scheduled.TVGuideBean;

import com.KamilIsmail.MovieApp.Constants;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author kamilismail
 * Klasa zawierająca informacji o filmie z progrmau tv.
 */
public class MovieBean {

    private Date date;
    private String chanel;
    private String title;
    private String description;
    private float rating;
    private int productionYear;
    private int YEAR = 1950;
    private int MINVOTES = 5000;
    private float AVERAGEVOTE = 7.0f;
    private MovieDb movieDb;

    private String PREMIERA = "Premiera: ";
    private String MOCNEKINO = "Mocne sobotnie kino: ";
    private String GWIAZDY = "Niedziela z gwiazdami: ";
    private String MEGAHIT = "Megahit: ";
    private String STRASZNY_PIATEK = "Straszny piątek: ";

    public MovieBean(Date date, String chanel, String title, String description) {
        this.date = date;
        this.chanel = chanel;
        this.description = description;

        this.title = Stream.of(PREMIERA, MOCNEKINO, GWIAZDY, MEGAHIT, STRASZNY_PIATEK).
                filter(title::contains).findFirst().map(p -> title.substring(p.length())).orElse(title);
    }

    public MovieBean(){}

    /**
     * The formula for calculating the Top Rated 250 Titles gives a true Bayesian estimate:
     * weighted rating (WR) = (v ÷ (v+m)) × R + (m ÷ (v+m)) × C
     * Where:
     * R = average for the movie (mean) = (Rating)
     * v = number of votes for the movie = (votes)
     * m = minimum votes required to be listed in the Top 250 (currently 25000)
     * C = the mean vote across the whole report (currently 7.0)
     * For the Top 250, only votes from regular voters are considered.
     *
     * via https://www.quora.com/What-algorithm-does-IMDB-use-to-decide-the-top-250-movies
     * @param rating
     * @param popularity
     * @param voteCount
     */
    private void setWeightedRating(float rating, float popularity, int voteCount) {
            //this.rating = (voteCount / (voteCount + MINVOTES)) * rating + (MINVOTES / (voteCount + MINVOTES)) * AVERAGEVOTE;
        this.rating = rating;
    }

    /**
     * Metoda sprawdza czy dany film ma już wystawioną ocenę w systemie tmdb. Jeśli nie to znaczy, że dana produkcja nie jest filmem.
     * @return
     */
    public Boolean setFilmwebRating() {
        try {
            Constants constants = new Constants();
            TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
            List<MovieDb> movieResultsPage = tmdbApi.getSearch().searchMovie(this.title, this.productionYear, Constants.getLanguage(), false, 0).getResults();
            if (movieResultsPage.size() > 1) {
                movieResultsPage.sort((movie1, movie2) -> Integer.compare(movie2.getVoteCount(), movie1.getVoteCount()));
                movieResultsPage.stream().filter(p -> !movieDb.getReleaseDate().contains(Integer.toString(productionYear)))
                        .forEach(movieResultsPage::remove);
            }
            this.movieDb = movieResultsPage.get(0);
            setWeightedRating(movieDb.getVoteAverage(), movieDb.getPopularity(), movieDb.getVoteCount());
            if (this.rating == 0.0f)
                return false;
        } catch (Exception e) {
            this.rating = -1;
            return false;
        }
        return true;
    }

    /**
     * Metodą znajdująca w opisie filmu datę produkcji.
     */
    public void parseYear() {
        int actualYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = YEAR; i <= actualYear; i++) {
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
        return "Movie: " + this.title + " date: " + Integer.toString(this.productionYear) + " at: " + this.date.toString()
                + " on: " + this.chanel + " rating: " + this.rating +"\n description: " + this.description;
    }

    public MovieDb getMovieDb() {
        return movieDb;
    }

    public void setMovieDb(MovieDb movieDb) {
        this.movieDb = movieDb;
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
