package com.KamilIsmail.MovieApp.DTO;

/**
 * @author kamilismail
 */
public class GetUsernameDTO {
    private String username;

    public GetUsernameDTO() {
    }

    public GetUsernameDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
