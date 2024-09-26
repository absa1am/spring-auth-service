package com.example.springauthservice.service;

import com.example.springauthservice.model.enums.Role;
import com.example.springauthservice.model.User;
import com.example.springauthservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            var existingUser = user.get();

            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(existingUser.getUsername())
                    .password(existingUser.getPassword())
                    .authorities(existingUser.getRole().name())
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public User saveUser(User user) {
        user.setRole(Role.USER);
        user.setCreatedDate(LocalDate.now());
        user.setModifiedDate(LocalDate.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public Page<User> getUsers(int page, int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 1);

        return userRepository.findAll(PageRequest.of(page, size));
    }

}
