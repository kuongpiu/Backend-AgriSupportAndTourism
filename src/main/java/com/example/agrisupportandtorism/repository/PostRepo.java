package com.example.agrisupportandtorism.repository;

import com.example.agrisupportandtorism.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
    Optional<Post> findPostById(Integer id);
}
