package com.example.wantedpreonboardingbackend.repository;

import com.example.wantedpreonboardingbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
