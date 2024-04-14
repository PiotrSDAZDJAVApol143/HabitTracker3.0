package com.example.authorizationapi.service;

import com.example.authorizationapi.configuration.CustomUserDetails;
import com.example.authorizationapi.model.User;
import com.example.authorizationapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByLogin(username);
        return userOptional.map(CustomUserDetails::new).orElseThrow(()-> new UsernameNotFoundException("User not found with username: "+ username));
    }
}

