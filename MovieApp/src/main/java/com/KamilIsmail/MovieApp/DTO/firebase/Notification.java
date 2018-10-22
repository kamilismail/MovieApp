package com.KamilIsmail.MovieApp.DTO.firebase;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification {
    @JsonProperty("title")
    public String title;
    @JsonProperty("body")
    public String body;

    public Notification(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
