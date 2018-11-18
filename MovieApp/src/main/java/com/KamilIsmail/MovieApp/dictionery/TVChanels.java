package com.KamilIsmail.MovieApp.dictionery;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author kamilismail
 * Klasa zawierająca listy kanałów. Sprawdza czy dana pozycja w programie tv jest "dobrym" kanałem,
 * oraz czy typ produkcji jest "filmowy".
 */
public class TVChanels {
    private ArrayList<String> list;
    private ArrayList<String> listAlternative;
    private ArrayList<String> showType;

    /**
     * Konstruktor zapisujący wszystkie listy.
     */
    public TVChanels() {
        list = new ArrayList<>();
        showType = new ArrayList<>();
        listAlternative = new ArrayList<>();

        list.addAll(Arrays.asList("TVP 1 HD", "TVP 2 HD", "TVN HD", "POLSAT HD", "TV Puls HD", "TV 4 HD", "Kino TV",
                "Comedy Central HD", "Comedy Central Family", "Paramount Channel HD", "AXN HD", "Stopklatka TV HD",
                "Polsat Film HD", "TVN FABUŁA HD", "TVN 7 HD", "FOX HD", "Ale Kino+ HD", "CBS Europa", "Metro TV HD"));

        listAlternative.addAll(Arrays.asList("13Ulica", "AleKino", "AXN", "CBSAction", "CBSEuropa", "ComedyCentral",
                "FoxComedy", "FOX", "KinoPolska", "PolsatFilm", "Polsat", "Stopklatka TV", "TV4", "TVPuls", "TVN7",
                "TVNFabula", "TVN", "TVP1", "TVP2", "ParamountChannel", "UniversalChannel"));

        showType.addAll(Arrays.asList("serial", "reality show", "program", "teleturniej", "magazyn", "widowisko",
                "telenowela", "sport", "felieton", "koncert"));
    }

    /**
     * Sprawdzenie czy podany kanał znajduje się na liście.
     * @param channel
     * @return
     */
    public Boolean ifContains(String channel) {
        return list.contains(channel);
    }

    /**
     * Sparwdzenie czy podany kanał znajduję się na liście.
     * @param channel
     * @return
     */
    public Boolean ifContainsAlternative(String channel) {
        return listAlternative.contains(channel.substring(0,channel.indexOf(".")));
    }

    /**
     * Sprawdzenie czy dana pozycja jest filmowa.
     * @param str
     * @return
     */
    public Boolean excludeProductions(String str) {
        str = str.toLowerCase();
        if (str.length() >= 20)
            str = str.substring(0,20);
        return showType.stream().anyMatch(str::contains);
    }
}
