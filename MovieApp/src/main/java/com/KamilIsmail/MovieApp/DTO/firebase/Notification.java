package com.KamilIsmail.MovieApp.DTO.firebase;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kamilismail
 */
public class Notification {
    @JsonProperty("title")
    public String title;
    @JsonProperty("body")
    public String body;
    @JsonProperty("icon")
    public String icon;

    public Notification(String title, String body) {
        this.title = title;
        this.body = body;
        this.icon = "ic_notification";
    }
}
