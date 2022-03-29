package com.example.agrisupportandtorism.repository;

import com.example.agrisupportandtorism.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {
    List<Comment> findByPostId(Integer postId, Sort sort);
    Optional<Comment> findCommentById(Integer id);
}
