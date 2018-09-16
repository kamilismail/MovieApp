package com.KamilIsmail.MovieApp.dictionery;

import java.util.ArrayList;

public class TVChanels {
    ArrayList<String> list;
    public TVChanels() {
        list = new ArrayList<>();
        list.add("TVP 1 HD");
        list.add("TVP 2 HD");
        list.add("TVN HD");
        list.add("POLSAT HD");
        list.add("TV PULS HD");
        list.add("TV 4 HD");
        list.add("TVN Fabula HD");
        list.add("HBO HD");
        list.add("HBO2 HD");
        list.add("HBO3 HD");
        list.add("CANAL+ HD");
        list.add("CANAL+ 1 HD");
        list.add("Kino TV");
        list.add("Comedy Central HD");
        list.add("Paramount Channel HD");
        list.add("AXN HD");
        list.add("Stopklatka TV HD");
        list.add("Polsat Film HD");
        list.add("TVN FABUŁA HD");
        list.add("TVN 7 HD");
    }

    public Boolean ifContains(String channel) {
        return list.contains(channel);
    }
}
