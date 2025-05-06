package com.example.matketplaceparser.service;

import com.example.matketplaceparser.dto.UserRegistrationRequest;
import com.example.matketplaceparser.entity.User;
import com.example.matketplaceparser.repository.UserRepository;
import com.example.matketplaceparser.utils.BucketUtils;
import io.minio.GetBucketPolicyArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private MinioClient minioClient;
    @Value("${minio.bucket-name}")
    private String bucketName;
    @Autowired
    private BucketUtils bucketUtils;

    public User registerUser(UserRegistrationRequest request) throws Exception {
        isValidAccount(request.getEmail(), request.getNickname(), request.getAge());

        String password = request.getPassword();
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password must be at least 6 characters long including at least one lowercase letter and one number");
        }

        String avatarUrl = null;
        User user = new User();
        if (request.getAvatar() != null && !request.getAvatar().isEmpty()) {
            avatarUrl = uploadAvatarToMinio(request.getAvatar());
            user.setAvatarUrl(avatarUrl);
        }

        user.setEmail(request.getEmail());
        user.setNickname(request.getNickname());
        user.setAge(request.getAge());
        user.setPassword(encoder.encode(password));

        return userRepository.save(user);
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 6) return false;
        Pattern lowerCasePattern = Pattern.compile("[a-z]");
        Pattern digitPattern = Pattern.compile("[0-9]");
        return lowerCasePattern.matcher(password).find() && digitPattern.matcher(password).find();
    }

    private String uploadAvatarToMinio(MultipartFile avatar) throws Exception {
        String filename = StringUtils.cleanPath(avatar.getOriginalFilename());
        String objectName = "avatars/" + System.currentTimeMillis() + "_" + filename;
        bucketUtils.createBucket();

        String contentType = avatar.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image can be allowed");
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

    private void isValidAccount(String mail, String nick, int age) {

        if (userRepository.existsByEmail(mail)) {
            throw new IllegalArgumentException("Email already exist");
        }
        if (userRepository.existsByNickname(nick)) {
            throw new IllegalArgumentException("Nickname already exist");
        }
        if (nick.length() < 6) {
            throw new IllegalArgumentException("Nickname must contain min 6 symbols");
        }

        if (age < 14 || age > 100) {
            throw new IllegalArgumentException("Age must be from 14 to 100");
        }
    }

}
