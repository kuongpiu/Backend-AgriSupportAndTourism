package com.example.agrisupportandtorism.repository.post;

import com.example.agrisupportandtorism.entity.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
    Optional<Post> findPostById(Integer id);
}
