package com.keretrendszer_e1jxex.keretrendszer_e1jxex.service;

import com.keretrendszer_e1jxex.keretrendszer_e1jxex.dao.UserDAO;
import com.keretrendszer_e1jxex.keretrendszer_e1jxex.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreUserDetailsService implements UserDetailsService {

    private final UserDAO userDAO;

    public StoreUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Felhasználó keresése
        User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Szerepkörök betöltése
        List<GrantedAuthority> authorities = userDAO.findRolesByUserId(user.getId())
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        // Visszatérés UserDetails objektummal
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

}