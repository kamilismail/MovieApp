package com.KamilIsmail.MovieApp;

public class Constants {

    private static String tmdbAPI = "4415570664d3008a558b498ee258962a";
    private static String fcmURL = "https://fcm.googleapis.com/fcm/send";
    private static String fcmKey = "AAAAcPwCoSg:APA91bFTSpK9EoM4Tcyu57lu3NvkSzdZCLFxyuQs48lKznzViG4kiK-TAp4nqvm_KHmbt8qQLswSR4UUCveG0LE_adNyH4qn-2zZw56rFB5fMBS2pzeSt-ZlmQea82LLZQCEB7RXrQxS_CTyvAO4dL3RqlyOFP9zWw";
    private static String posterPath = "https://image.tmdb.org/t/p/w500/";

    public String getPosterPath() {
        return posterPath;
    }

    public String getFcmURL() {
        return fcmURL;
    }

    public String getFcmKey() {
        return fcmKey;
    }

    public String getTmdbAPI() {
        return tmdbAPI;
    }
}
