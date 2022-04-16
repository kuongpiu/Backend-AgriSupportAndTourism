package com.example.agrisupportandtorism.repository.upload;

import com.example.agrisupportandtorism.entity.upload.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepo extends JpaRepository<Document, Integer> {
    Document findByHash(String hash);
}
