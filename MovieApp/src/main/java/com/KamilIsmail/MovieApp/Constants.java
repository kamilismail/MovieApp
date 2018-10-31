package com.KamilIsmail.MovieApp;

public class Constants {

    private String tmdbAPI = "4415570664d3008a558b498ee258962a";
    private static String fcmURL = "https://fcm.googleapis.com/fcm/";
    private static String fcmKey = "AAAAcPwCoSg:APA91bFTSpK9EoM4Tcyu57lu3NvkSzdZCLFxyuQs48lKznzViG4kiK-TAp4nqvm_KHmbt8qQLswSR4UUCveG0LE_adNyH4qn-2zZw56rFB5fMBS2pzeSt-ZlmQea82LLZQCEB7RXrQxS_CTyvAO4dL3RqlyOFP9zWw";
    private static String posterPath = "https://image.tmdb.org/t/p/w780/";
    private static String tvGuideUrl = "http://epg.koditvepg2.com/PL/guide.xml.gz";
    private static String tvGuideUrlAlternative = "http://epg.server.xdns.pro/poland.xml.gz";
    private static String logoPath = "http://1.fwcdn.pl/";
    private static String language = "pl";


    public Constants(){}

    public static String getTvGuideUrl() {
        return tvGuideUrl;
    }

    public static String getPosterPath() {
        return posterPath;
    }

    public static String getFcmURL() {
        return fcmURL;
    }

    public static String getFcmKey() {
        return fcmKey;
    }

    public static String getTvGuideUrlAlternative() {
        return tvGuideUrlAlternative;
    }

    public static String getLanguage() {
        return language;
    }

    public String getTmdbAPI() {
        return tmdbAPI;
    }

    public static String getLogoPath() {
        return logoPath;
    }
}
