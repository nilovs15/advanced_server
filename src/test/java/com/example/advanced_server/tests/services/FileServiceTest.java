package com.example.advanced_server.tests.services;

import com.example.advanced_server.dto.CustomSuccessResponse;
import com.example.advanced_server.service.impl.FileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static com.example.advanced_server.tests.services.TestsConstants.successStatusCode;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class FileServiceTest {

    @Test
    void successUploadFile() throws IOException {
        FileServiceImpl fileService = new FileServiceImpl();

        ReflectionTestUtils.setField(fileService, "uploadDir", "/home/dunice/newAfterDelete/files/");
        ReflectionTestUtils.setField(fileService, "fileUrl", "http://localhost:8080/v1/file/");

        MockMultipartFile file = new MockMultipartFile("file",
                "Hello, World!".getBytes());
        CustomSuccessResponse response = fileService.uploadFile(file);
        assertNotNull(response.getData());
        assertTrue(response.isSuccess());
        assertEquals(successStatusCode , response.getStatusCode());
    }

    @Test
    void successGetFile() throws IOException {
        FileServiceImpl fileService = new FileServiceImpl();

        ReflectionTestUtils.setField(fileService, "uploadDir", "/home/dunice/newAfterDelete/files/");
        ReflectionTestUtils.setField(fileService, "fileUrl", "http://localhost:8080/v1/file/");

        MockMultipartFile file = new MockMultipartFile("file",
                "Hello, World!".getBytes());

        String fileName = fileService.uploadFile(file).getData();
        assertNotNull(fileService.getFile(fileName));
    }
}