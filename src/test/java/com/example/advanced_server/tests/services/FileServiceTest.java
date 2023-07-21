package com.example.advanced_server.tests.services;

import com.example.advanced_server.dto.CustomSuccessResponse;
import com.example.advanced_server.service.impl.FileServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class FileServiceTest {

    @Test
    void success_UploadFile() throws IOException {
        FileServiceImpl fileService = new FileServiceImpl();

        MockMultipartFile file = new MockMultipartFile("file",
                "Hello, World!".getBytes());
        CustomSuccessResponse response = fileService.uploadFile(file);
        assertNotNull(response.getData());
        Assertions.assertTrue(response.isSuccess());
        assertEquals(response.getStatusCode(), 1);
    }

    @Test
    void success_GetFile() throws IOException {
        FileServiceImpl fileService = new FileServiceImpl();

        MockMultipartFile file = new MockMultipartFile("file",
                "Hello, World!".getBytes());

        String fileName = fileService.uploadFile(file).getData();
        assertNotNull(fileService.getFile(fileName));
    }
}