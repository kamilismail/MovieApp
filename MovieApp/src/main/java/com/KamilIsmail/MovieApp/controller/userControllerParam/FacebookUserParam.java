package com.KamilIsmail.MovieApp.controller.userControllerParam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FacebookUserParam {
    @NotNull
    @NotEmpty
    private String username;
    @NotNull
    @NotEmpty
    private String facebookID;
    @NotNull
    @NotEmpty
    private String mail;
    @NotNull
    @NotEmpty
    private String role;

    public FacebookUserParam() {
    }

    @JsonCreator
    public FacebookUserParam(@JsonProperty("username") String username,
                           @JsonProperty("facebookID") String facebookID,
                             @JsonProperty("mail") String mail,
                           @JsonProperty("role") String role) {
        this.username = username;
        this.facebookID = facebookID;
        this.mail = mail;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
