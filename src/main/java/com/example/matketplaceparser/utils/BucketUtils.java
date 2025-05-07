package com.example.matketplaceparser.utils;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BucketUtils {
    @Autowired
    private MinioClient minioClient;

    public void createBucket(String bucketName) {

        try {
            // Проверяем, существует ли бакет
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                // Создаем бакет, если его еще нет
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                System.out.println("Bucket '" + bucketName + "' создан.");
            } else {
                System.out.println("Bucket '" + bucketName + "' уже существует.");
            }
        } catch (Exception e) {
            // Обработка ошибок
            e.printStackTrace();
        }
    }
}
