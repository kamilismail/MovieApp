package com.KamilIsmail.MovieApp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieCommentsDTO {
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("comment")
    private String comment;

    public MovieCommentsDTO(String userName, String comment) {
        this.userName = userName;
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
