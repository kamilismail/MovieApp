package com.KamilIsmail.MovieApp.helpers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kamilismail
 * Klasa ułatwiająca utworzenie sesji dla użytkownika zalogowanego przez Facebooka.
 */
public class GrantAuthHelper {

    private String username;
    private String role;

    public GrantAuthHelper(String username, String role) {
        this.username = username;
        this.role = role;
    }

    /**
     * Klasa dodająca danego użytkownika do do listy przyznanych dostępów.
     * @return
     */
    public UserDetails grantAuth() {
        List<GrantedAuthority> grantedList = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        grantedList.add(authority);
        return ((UserDetails) new User(username, "", grantedList));
    }
}
