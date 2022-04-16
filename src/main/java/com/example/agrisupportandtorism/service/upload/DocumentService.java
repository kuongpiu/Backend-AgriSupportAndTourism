package com.example.agrisupportandtorism.service.upload;

import com.example.agrisupportandtorism.entity.upload.Document;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    Document createDocument(MultipartFile multipartFile);
    Resource loadDocumentAsResource(String fileName);
    Document findDocByHash(String hash);
}
