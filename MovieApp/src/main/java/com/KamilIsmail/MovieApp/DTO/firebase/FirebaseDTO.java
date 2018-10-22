package com.KamilIsmail.MovieApp.DTO.firebase;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FirebaseDTO {
    @JsonProperty("notification")
    public Notification notification;
    @JsonProperty("to")
    public String to;

    public FirebaseDTO(Notification notification, String to) {
        this.notification = notification;
        this.to = to;
    }
}
