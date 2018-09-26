package com.kamilismail.movieappandroid.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDTO {

    @SerializedName("username")
    @Expose
    private String username;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserDTO() {
    }

    /**
     *
     * @param username
     */
    public UserDTO(String username) {
        super();
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
