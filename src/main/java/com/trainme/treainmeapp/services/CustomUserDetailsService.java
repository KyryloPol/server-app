package com.trainme.treainmeapp.services;


import com.trainme.treainmeapp.domain.User;
import com.trainme.treainmeapp.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * It implements the UserDetailsService interface and overrides the loadUserByUsername() method
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * "If the user exists, return a UserDetails object, otherwise throw an exception."
     *
     * The UserDetails object is a Spring Security interface that contains all the information about a user
     *
     * @param username The username of the user you want to load.
     * @return A UserDetails object
     */
    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username: " + username));
        return build(user);
    }

    /**
     * If the user exists, return the user, otherwise return null.
     *
     * @param id The id of the user to load
     * @return A Response Entity of any Object for error handling
     */
    public User loadUserById(Long id) {
        return userRepository.findUserById(id).orElse(null);
    }

    /**
     * It takes a user object and returns a new user object with the same id, username, email, and password, but with a
     * list of authorities that are derived from the user's membership
     *
     * @param user The user object that we are building the UserDetails object from.
     * @return A User object
     */
    public static User build(User user){
        List<GrantedAuthority> authorities = user.getMembership().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        return new User(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }
}

