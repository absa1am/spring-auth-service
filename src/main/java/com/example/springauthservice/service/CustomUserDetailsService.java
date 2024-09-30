package com.example.springauthservice.service;

import com.example.springauthservice.model.User;
import com.example.springauthservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            User existingUser = user.get();

            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(existingUser.getUsername())
                    .password(existingUser.getPassword())
                    .authorities(existingUser.getRole().name())
                    .build();
        }

        throw new UsernameNotFoundException(username);
    }

}
