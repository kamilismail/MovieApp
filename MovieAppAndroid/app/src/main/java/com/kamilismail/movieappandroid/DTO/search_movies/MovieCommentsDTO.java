package com.kamilismail.movieappandroid.DTO.search_movies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieCommentsDTO {
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("comment")
    @Expose
    private String comment;

    public MovieCommentsDTO(String userName, String comment) {
        this.userName = userName;
        this.comment = comment;
    }

    public MovieCommentsDTO() {
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
