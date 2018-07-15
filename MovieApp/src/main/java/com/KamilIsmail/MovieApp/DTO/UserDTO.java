package com.KamilIsmail.MovieApp.DTO;

public class UserDTO {
    private long userId;
    private String username;
    private String role;

    public UserDTO() {
    }

    public UserDTO(long user_id, String username, String role) {
        this.userId = user_id;
        this.username = username;
        this.role = role;
    }

    public long getId() {
        return userId;
    }
    public void setId(long id) {
        this.userId = id;
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
}
