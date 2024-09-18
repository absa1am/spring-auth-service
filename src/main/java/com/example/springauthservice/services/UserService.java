package com.example.springauthservice.services;

import com.example.springauthservice.models.User;
import com.example.springauthservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            var existingUser = user.get();

            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(existingUser.getUsername())
                    .password(existingUser.getPassword())
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
