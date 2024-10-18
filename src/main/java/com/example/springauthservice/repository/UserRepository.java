package com.example.springauthservice.repository;

import com.example.springauthservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.username = ?1 and u.enabled = true")
    Optional<User> findByUsernameAndEnabled(String username);

}
