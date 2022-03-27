package com.example.agrisupportandtorism.service;

import com.example.agrisupportandtorism.entity.Document;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface DocumentService {
    Document createDocument(MultipartFile multipartFile);
    Resource loadDocumentAsResource(String fileName);
    Document findDocByHash(String hash);
}
