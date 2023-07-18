package com.example.advanced_server.service;

import java.io.IOException;

import com.example.advanced_server.dto.CustomSuccessResponse;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    CustomSuccessResponse uploadFile(MultipartFile file) throws IOException;
    UrlResource getFile(String fileName);
}
