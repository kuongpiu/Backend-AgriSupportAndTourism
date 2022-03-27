package com.example.agrisupportandtorism.service.imp;

import com.example.agrisupportandtorism.entity.Document;
import com.example.agrisupportandtorism.exception.CreateDocumentException;
import com.example.agrisupportandtorism.exception.FileNotFoundInFileSystemException;
import com.example.agrisupportandtorism.property.DocumentStorageProperty;
import com.example.agrisupportandtorism.repository.DocumentRepo;
import com.example.agrisupportandtorism.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

@Service
public class DocumentServiceImp implements DocumentService {
    @Autowired
    private DocumentRepo documentRepo;

    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImp.class);

    private final Path docStorageLocation;

    public DocumentServiceImp(DocumentStorageProperty documentStorageProperty) {
        this.docStorageLocation = Paths.get(documentStorageProperty.getUploadDirectory()).toAbsolutePath().normalize();
        try{
            Files.createDirectories(this.docStorageLocation);
        }catch (IOException e){
            throw new CreateDocumentException(String.format("Files.createDirectories(%s): IO Exception", this.docStorageLocation), e);
        }
    }

    public Document createDocument(MultipartFile multipartFile) {
        Document doc = new Document();
        doc.setName(multipartFile.getOriginalFilename());
        doc.setMimeType(multipartFile.getContentType());
        doc.setSize(multipartFile.getSize());
        try{
            doc.setHash();
            storeInFileSystem(multipartFile, doc.getHash());
        }catch (NoSuchAlgorithmException e ){
            throw new CreateDocumentException("Document.setHash(): No such algorithm exception", e);
        }catch (IOException e){
            throw new CreateDocumentException("StoreInFileSystem(): IO Exception", e);
        }
        return documentRepo.save(doc);
    }

    public Resource loadDocumentAsResource(String fileName) throws FileNotFoundInFileSystemException{
        try {
            Path docPath = this.docStorageLocation.resolve(fileName);
            Resource resource = new UrlResource(docPath.toUri());
            if(resource.exists()){
                return resource;
            }else{
                throw new FileNotFoundInFileSystemException(String.format("File not found, path= %s", docPath));
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundInFileSystemException("File not found", ex);
        }
    }

    public Document findDocByHash(String hash){
        return documentRepo.findByHash(hash);
    }

    private void storeInFileSystem(MultipartFile file, String hash) throws IOException {
        Path targetLocation = this.docStorageLocation.resolve(hash);
        Files.copy(file.getInputStream(), targetLocation);
    }
}
