package com.example.matketplaceparser.controller;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@RestController
@Tag(name = "Тестовый контроллер", description = "тестирует соединение")
public class TestController {
    @Autowired
    private MinioClient minioClient;
    @GetMapping("/test")
    @Operation(summary = "Тестирует соединение с MinIO")
    public ResponseEntity<String> test() {
        StringBuilder result = new StringBuilder();
        try {
            minioClient.listBuckets();
            result.append("Соединение с MinIO успешно.");
        } catch (MinioException | NoSuchAlgorithmException | InvalidKeyException
                 | IllegalArgumentException | IOException e) {
            result.append("Ошибка при подключении к MinIO: ").append(e.getMessage());
            return ResponseEntity.status(503).body(result.toString());
        }
        return ResponseEntity.ok("Hello World");
    }
}
