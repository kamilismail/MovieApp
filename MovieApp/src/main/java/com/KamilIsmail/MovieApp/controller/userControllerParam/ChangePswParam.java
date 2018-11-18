package com.KamilIsmail.MovieApp.controller.userControllerParam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author kamilismail
 * Klasa służaca do przechowywania danych json do zapytania oz mianę hasła użytkownika.
 */
public class ChangePswParam {
    @NotNull
    @NotEmpty
    private String oldPassword;
    @NotNull
    @NotEmpty
    private String newPassword;

    public ChangePswParam() {
    }

    @JsonCreator
    public ChangePswParam(@JsonProperty("old_password") String oldPassword,
                           @JsonProperty("new_password") String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
