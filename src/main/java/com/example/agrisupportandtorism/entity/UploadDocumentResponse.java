package com.example.agrisupportandtorism.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UploadDocumentResponse {
    private String fileName;
    private String downloadUri;
    private String mimeType;
    private String size;
}
