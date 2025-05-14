package by.tima_zaytsev.matketplace_parser.infrastracture;

import by.tima_zaytsev.matketplace_parser.common.exceptions.RegValidationException;
import io.minio.BucketExistsArgs;
import io.minio.GetBucketPolicyArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;
    @Value("${minio.bucket-name}")
    private String bucketName;

    public String uploadAvatarToMinio(MultipartFile avatar) throws RegValidationException, Exception {
        String filename = StringUtils.cleanPath(avatar.getOriginalFilename());
        String objectName = "avatars/" + System.currentTimeMillis() + "_" + filename;
        createBucket(bucketName);

        String contentType = avatar.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RegValidationException("Only image can be allowed", HttpStatus.BAD_REQUEST.value());
        }

        try (InputStream inputStream = avatar.getInputStream()) {
            minioClient.putObject(
                    io.minio.PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, avatar.getSize(), -1)
                            .contentType(avatar.getContentType())
                            .build()
            );
        }

        return minioClient.getBucketPolicy(GetBucketPolicyArgs
                .builder()
                .bucket(bucketName)
                .build());
    }

    public void createBucket(String bucketName) {

        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                System.out.println("Bucket '" + bucketName + "' создан.");
            } else {
                System.out.println("Bucket '" + bucketName + "' уже существует.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
