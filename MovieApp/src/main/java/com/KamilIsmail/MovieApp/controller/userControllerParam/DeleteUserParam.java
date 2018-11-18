package com.KamilIsmail.MovieApp.controller.userControllerParam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author kamilismail
 * Klasa przechowująca dane json przy zapytaniu o usunięcie konta.
 */
public class DeleteUserParam {
    @NotNull
    @NotEmpty
    private String password;

    public DeleteUserParam() {
    }

    @JsonCreator
    public DeleteUserParam(@JsonProperty("password") String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
