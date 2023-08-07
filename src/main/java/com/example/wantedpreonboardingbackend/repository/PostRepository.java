package com.example.wantedpreonboardingbackend.repository;

import com.example.wantedpreonboardingbackend.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("select p from Post p where p.id = :id")
    Post findPostById(Long id);
    @Query("select p from Post p order by p.id desc")
    Slice<Post> findAllPostsSlice(Pageable pageable);
}
