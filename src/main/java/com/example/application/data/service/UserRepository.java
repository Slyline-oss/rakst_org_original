package com.example.application.data.service;

import com.example.application.data.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);
}