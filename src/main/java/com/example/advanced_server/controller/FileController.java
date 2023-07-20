package com.example.advanced_server.controller;

import java.io.IOException;

import com.example.advanced_server.service.FileService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/file/")
@Validated
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("uploadFile")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(fileService.uploadFile(file));
    }

    @GetMapping("{fileName}")
    public ResponseEntity getFile(@PathVariable String fileName) {
        return ResponseEntity.ok(fileService.getFile(fileName));
    }
}
