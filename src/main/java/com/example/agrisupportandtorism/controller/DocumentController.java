package com.example.agrisupportandtorism.controller;

import com.example.agrisupportandtorism.entity.upload.Document;
import com.example.agrisupportandtorism.entity.upload.UploadDocumentResponse;
import com.example.agrisupportandtorism.service.upload.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/doc")
@CrossOrigin("http://localhost:9528/")
public class DocumentController {
    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);
    @Autowired
    private DocumentService documentService;

    @PostMapping("/add")
    public List<UploadDocumentResponse> uploadDocuments(@Valid @RequestParam(value = "documents") MultipartFile[] multipartFiles) {
        logger.info("add new doc");
        return Arrays.stream(multipartFiles)
                .map(this::uploadDocument)
                .collect(Collectors.toList());
    }
    @GetMapping("/download/{hash}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable String hash, HttpServletRequest request) {
        Document doc = documentService.findDocByHash(hash);

        Resource resource = documentService.loadDocumentAsResource(hash);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getMimeType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private UploadDocumentResponse uploadDocument(MultipartFile multipartFile) {
        Document doc = documentService.createDocument(multipartFile);

        String docHash = doc.getHash();

        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/doc/download/")
                .path(docHash)
                .toUriString();

        return new UploadDocumentResponse(docHash, downloadUri, doc.getMimeType(), doc.getSize().toString());
    }
}
