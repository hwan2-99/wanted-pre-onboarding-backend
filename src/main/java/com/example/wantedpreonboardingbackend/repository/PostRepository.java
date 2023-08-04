package com.example.wantedpreonboardingbackend.repository;

import com.example.wantedpreonboardingbackend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
