package com.KamilIsmail.MovieApp.DTO;

/**
 * @author kamilismail
 */
public class UserPhotoDTO {

    private String photo;

    public UserPhotoDTO(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
