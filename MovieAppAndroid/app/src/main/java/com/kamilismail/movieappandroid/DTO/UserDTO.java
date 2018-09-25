package com.kamilismail.movieappandroid.DTO;

import com.kamilismail.movieappandroid.Enum.Role;

public class UserDTO {
    private String login;
    private int id;
    private Role role;

    public UserDTO(String _login, int _id, Role _role) {
        this.login = _login;
        this.id = _id;
        this.role = _role;
    }

    public Role getRole() {
        return this.role;
    }

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }
}
