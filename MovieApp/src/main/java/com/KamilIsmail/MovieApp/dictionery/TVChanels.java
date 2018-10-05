package com.KamilIsmail.MovieApp.dictionery;

import java.util.ArrayList;

public class TVChanels {
    private ArrayList<String> list;
    private ArrayList<String> showType;

    public TVChanels() {
        list = new ArrayList<>();
        showType = new ArrayList<>();

        list.add("TVP 1 HD");
        list.add("TVP 2 HD");
        list.add("TVN HD");
        list.add("POLSAT HD");
        list.add("TV Puls HD");
        list.add("TV 4 HD");
        //list.add("TVN Fabula HD");
        //list.add("HBO HD");
        //list.add("HBO2 HD");
        //list.add("HBO3 HD");
        //list.add("CANAL+ HD");
        //list.add("CANAL+ 1 HD");
        list.add("Kino TV");
        list.add("Comedy Central HD");
        list.add("Comedy Central Family");
        list.add("Paramount Channel HD");
        list.add("AXN HD");
        list.add("Stopklatka TV HD");
        //list.add("Stopklatka TV");
        list.add("Polsat Film HD");
        list.add("TVN FABUŁA HD");
        list.add("TVN 7 HD");
        list.add("FOX HD");
        list.add("Ale Kino+ HD");
        list.add("CBS Europa");
        list.add("Metro TV HD");

        showType.add("serial");
        showType.add("reality show");
        showType.add("program");
        showType.add("teleturniej");
        showType.add("magazyn");
        showType.add("widowisko");
        showType.add("telenowela");
        showType.add("sport");
        showType.add("felieton");
        showType.add("koncert");
    }

    public Boolean ifContains(String channel) {
        return list.contains(channel);
    }

    public Boolean excludeProductions(String str) {
        if (str.length() >= 20)
            str = str.substring(0,20);
        str = str.toLowerCase();
        for (String show : showType) {
            if (str.contains(show)) {
                return true;
            }
        }
        return false;
    }
}
