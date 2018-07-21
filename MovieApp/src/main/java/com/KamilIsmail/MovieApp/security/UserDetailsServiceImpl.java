package com.KamilIsmail.MovieApp.security;

import com.KamilIsmail.MovieApp.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserSecDao userSecDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userSecDao.getUserByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User" + username + " was not found in the database");

        List<GrantedAuthority> grantedList = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        grantedList.add(authority);

        UserDetails userDetails = (UserDetails) new User(user.getUsername(), user.getPassword(), grantedList);
        return userDetails;
    }
}
