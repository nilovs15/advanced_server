package com.example.advanced_server.service.impl;

import com.example.advanced_server.dto.CustomSuccessResponse;
import com.example.advanced_server.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadDir = "/home/dunice/newAfterDelete/files/";

    @Value("${server.fileUrl}")
    private String fileUrl = "http://localhost:8080/v1/file/";

    private Path root = Paths.get("files/").toAbsolutePath();

    @Override
    public CustomSuccessResponse<String> uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "." + file.getOriginalFilename();
        Path copyLocation = Paths.get(uploadDir + File.separator + fileName);
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        return CustomSuccessResponse.getResponse(fileUrl + fileName);
    }

    @Override
    public UrlResource getFile(String fileName) {
        try {
            Path file = root.resolve(fileName);
            return new UrlResource(file.toUri());
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
