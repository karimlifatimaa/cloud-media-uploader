package com.example.cloudmediauploader.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageService {

    private final S3Client s3Client;

    @Value("${digitalocean.spaces.bucket}")
    private String bucketName;

    @Value("${digitalocean.spaces.endpoint}")
    private String endpoint;

    @Value("${upload.allowed-types}")
    private String allowedTypes;


    public String uploadFile(MultipartFile file) throws IOException {
        // Validate file
        validateFile(file);

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        try {
            // Upload to DigitalOcean Spaces
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uniqueFilename)
                    .contentType(file.getContentType())
                    .acl("public-read") // Make file publicly accessible
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            // Generate public URL
            String fileUrl = String.format("%s/%s/%s", endpoint, bucketName, uniqueFilename);

            log.info("File uploaded successfully: {}", fileUrl);
            return fileUrl;

        } catch (S3Exception e) {
            log.error("Error uploading file to Spaces: {}", e.getMessage());
            throw new RuntimeException("Failed to upload file to storage", e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String contentType = file.getContentType();
        List<String> allowedTypesList = Arrays.asList(allowedTypes.split(","));

        if (!allowedTypesList.contains(contentType)) {
            throw new IllegalArgumentException(
                    "Invalid file type. Allowed types: " + allowedTypes);
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}
