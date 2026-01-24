package com.adrign93.tokenGenerator.repository;

import com.adrign93.tokenGenerator.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /** findByUsername */
    Optional<User> findByUsername(String username);
}
