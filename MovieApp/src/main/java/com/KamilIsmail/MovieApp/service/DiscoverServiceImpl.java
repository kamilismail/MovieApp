package com.KamilIsmail.MovieApp.service;

import com.KamilIsmail.MovieApp.Constants;
import com.KamilIsmail.MovieApp.DTO.DiscoverDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverMovieDTO;
import com.KamilIsmail.MovieApp.DTO.DiscoverSeriesDTO;
import com.KamilIsmail.MovieApp.DTO.recommender.RecommenderDTO;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.Credits;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.NamedIdElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kamilismail
 * Serwis wywoływany z kontrolera.
 */
@Service
public class DiscoverServiceImpl implements DiscoverService {

    @Autowired
    TVGuideService tvGuideService;
    @Autowired
    FavouritesService favouritesService;

    /**
     * Metoda zwracajaca w jsonie liste popularnych filmow, popularnych seriali, aktualnie granych filmow w kinach oraz
     * tych nadchodzacych.
     *
     * @return
     */
    @Cacheable(value = "discover")
    @Override
    public DiscoverDTO getJSON() {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());

        List<DiscoverMovieDTO> upcomingList = tmdbApi.getMovies().getUpcoming(Constants.getLanguage(), 1, "")
                .getResults().stream().map(p -> new DiscoverMovieDTO(p.getMediaType().toString(), Integer.toString(p.getId()),
                        p.getTitle(), Constants.getPosterPath() + p.getBackdropPath(), p.getReleaseDate(),
                        String.valueOf(p.getVoteAverage())))
                .collect(Collectors.toList());

        List<DiscoverMovieDTO> nowPlayingList = tmdbApi.getMovies().getNowPlayingMovies(Constants.getLanguage(), 1, "")
                .getResults().stream().map(p -> new DiscoverMovieDTO(p.getMediaType().toString(), Integer.toString(p.getId()),
                        p.getTitle(), Constants.getPosterPath() + p.getBackdropPath(), p.getReleaseDate(),
                        String.valueOf(p.getVoteAverage())))
                .collect(Collectors.toList());

        List<DiscoverMovieDTO> popularMoviesList = tmdbApi.getMovies().getPopularMovies(Constants.getLanguage(), 1)
                .getResults().stream().map(p -> new DiscoverMovieDTO(p.getMediaType().toString(), Integer.toString(p.getId()),
                        p.getTitle(), Constants.getPosterPath() + p.getBackdropPath(),
                        p.getReleaseDate(), String.valueOf(p.getVoteAverage())))
                .collect(Collectors.toList());


        List<DiscoverSeriesDTO> popularSeriesList =  tmdbApi.getTvSeries().getPopular(Constants.getLanguage(), 1)
                .getResults().stream().map(p -> new DiscoverSeriesDTO(Integer.toString(p.getId()), p.getName(),
                        Constants.getPosterPath() + p.getBackdropPath(), String.valueOf(p.getVoteAverage()),
                        p.getFirstAirDate()))
                .collect(Collectors.toList());

        return (new DiscoverDTO(upcomingList, nowPlayingList, popularMoviesList, popularSeriesList));
    }


    /**
     * Zaciągamy listę filmów aktualnie granych w kinach. Wybieramy z nich max 3 gatunki, max 3 aktorów i reżysera
     * Tworzymy vector słów ze wszytskich wyników bez powtórzeń uszeregowany alfabetycznie z polubionych filmów przez użytkownika.
     * Tworzymy taki sam wektor dla danych tagów dla każdego filmu aktualnie granego w kinach i wpisujemy ile razy dany tag się powtórzył.
     * z tego robimy matrix i następnie cosine similarity między użytkownikem a wszytstkimi zaciągniętymi filmami.
     */
    @Override
    public List<RecommenderDTO> getBestForUser(int userID) {
        Constants constants = new Constants();
        TmdbApi tmdbApi = new TmdbApi(constants.getTmdbAPI());
        List<RecommenderDTO> recommenderList = new ArrayList<>();
        List<RecommenderDTO> userRecommendationList = new ArrayList<>();

        MovieResultsPage moviesPage = tmdbApi.getMovies().getNowPlayingMovies(Constants.getLanguage(), 1, "");
        for (MovieDb movie : moviesPage)
            recommenderList.add(getInfoForRecommendation(movie.getId(), movie.getTitle(), tmdbApi));

        try {
            List<DiscoverMovieDTO> discoverMovies = favouritesService.getFavourites(userID);
            for (DiscoverMovieDTO movie : discoverMovies)
                userRecommendationList.add(getInfoForRecommendation(Integer.parseInt(movie.getId()), movie.getTitle(), tmdbApi));
        } catch (IOException e) {
            return null;
        }

        //zaladowanie wszystkich tagow usera i filmow
        String userInfo = getMovieInfo(userRecommendationList);
        List<String> moviesInfo = new ArrayList<>();
        recommenderList.forEach(p -> moviesInfo.add(getMovieInfo(Collections.singletonList(p)).replaceAll("  ", " ")));

        //utworzenie jednego stringa z tagami
        String allTags = combineAllTags(userInfo, moviesInfo);

        HashMap<String, Long> userScore = calculateOccurences(userInfo, allTags, true);
        List<HashMap<String, Long>> moviesScore = new ArrayList<>();
        for (String str : moviesInfo)
            moviesScore.add(calculateOccurences(str, allTags, false));

        List<Double> result = calculateSimilarity(userScore, moviesScore);
        List<MovieDb> resultList = new ArrayList<>();
        int i = 0;
        for (RecommenderDTO recommenderDTO : recommenderList) {
            recommenderDTO.setScore(result.get(i));
            recommenderDTO.setMovieDb(moviesPage.getResults().get(i));
            i++;
        }
        recommenderList.sort(Comparator.comparingDouble(RecommenderDTO::getScore).reversed());
        return recommenderList;
    }

    private List<Double> calculateSimilarity(HashMap<String, Long> userScore, List<HashMap<String, Long>> moviesScore) {
        List<Double> resultList = new ArrayList<>();
        for (HashMap<String, Long> movieScore : moviesScore) {
            double dotProduct = 0.0;
            double normA = 0.0;
            double normB = 0.0;
            for (Map.Entry<String, Long> entry : userScore.entrySet()) {
                dotProduct += entry.getValue() * movieScore.get(entry.getKey());
                normA += Math.pow(entry.getValue(), 2);
                normB += Math.pow(movieScore.get(entry.getKey()), 2);
            }
            resultList.add(dotProduct / (Math.sqrt(normA) * Math.sqrt(normB)));
        }
        return resultList;
    }

    private String combineAllTags(String strUser, List<String> movies) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(strUser).append(" ");
        for (String str : movies)
            stringBuilder.append(str).append(" ");
        return stringBuilder.toString().replaceAll("\\b(\\w+)\\b\\s*(?=.*\\b\\1\\b)", "").replaceAll("  ", " ");
    }

    private HashMap<String, Long> calculateOccurences(String str, String allTags, Boolean czyOcenaUsera) {
        HashMap<String, Long> hashMap= new HashMap<>();
        for (String tag : allTags.split(" "))
            hashMap.put(tag, 0L);

        for (String tag : str.split(" ")) {
            if (hashMap.containsKey(tag)) {
                hashMap.put(tag, hashMap.get(tag) + 1L);
                if (czyOcenaUsera && hashMap.get(tag) > 1L)
                    hashMap.put(tag, 1L);
            }
        }
        return hashMap;
    }

    private String getMovieInfo(List<RecommenderDTO> list) {
        StringBuilder actorsString = new StringBuilder();
        StringBuilder genresString = new StringBuilder();
        StringBuilder directorsString = new StringBuilder();
        for (RecommenderDTO movie : list) {
            actorsString.append(movie.getActorsList().stream().map(p -> p = p.replaceAll(" ", "").toLowerCase()).collect(Collectors.joining(" "))).append(" ");
            genresString.append(movie.getGenresList().stream().map(p -> p = p.replaceAll(" ", "").toLowerCase()).collect(Collectors.joining(" "))).append(" ");
            directorsString.append(movie.getDirector().replaceAll(" ", "").toLowerCase()).append(" ");
        }
        return actorsString.toString() + " " + genresString.toString() + " " + directorsString.toString();
    }

    private RecommenderDTO getInfoForRecommendation(int movieID, String movieTitle, TmdbApi tmdbApi) {
        RecommenderDTO recommenderDTO = new RecommenderDTO(movieTitle, movieID);
        MovieDb movieDb = tmdbApi.getMovies().getMovie(movieID, Constants.getLanguage());
        recommenderDTO.setGenresList(movieDb.getGenres().stream().map(NamedIdElement::getName).collect(Collectors.toList()));
        Credits credits = tmdbApi.getMovies().getCredits(movieID);
        recommenderDTO.setActorsList(credits.getCast().stream().map(NamedIdElement::getName).limit(3).collect(Collectors.toList()));
        recommenderDTO.setDirector(credits.getCrew().get(0).getName());
        return recommenderDTO;
    }
}
