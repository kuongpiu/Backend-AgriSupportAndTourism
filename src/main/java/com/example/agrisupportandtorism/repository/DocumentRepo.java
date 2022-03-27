package com.example.agrisupportandtorism.repository;

import com.example.agrisupportandtorism.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepo extends JpaRepository<Document, Integer> {
    Document findByHash(String hash);
}
