package com.example.cloudmediauploader.controller;

import com.example.cloudmediauploader.dto.UploadResponse;
import com.example.cloudmediauploader.entity.ProfileImage;
import com.example.cloudmediauploader.repository.ProfileImageRepository;
import com.example.cloudmediauploader.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class UploadController {

    private final StorageService storageService;
    private final ProfileImageRepository profileImageRepository;

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            log.info("Received upload request for file: {}", file.getOriginalFilename());

            // Upload to DigitalOcean Spaces
            String fileUrl = storageService.uploadFile(file);

            // Save metadata to database
            ProfileImage profileImage = ProfileImage.builder()
                    .fileName(file.getOriginalFilename())
                    .fileUrl(fileUrl)
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .build();

            ProfileImage saved = profileImageRepository.save(profileImage);

            // Build response
            UploadResponse response = UploadResponse.builder()
                    .id(saved.getId())
                    .fileName(saved.getFileName())
                    .fileUrl(saved.getFileUrl())
                    .contentType(saved.getContentType())
                    .fileSize(saved.getFileSize())
                    .uploadedAt(saved.getUploadedAt())
                    .build();

            log.info("File uploaded successfully with ID: {}", saved.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IOException e) {
            log.error("Error uploading file: {}", e.getMessage());
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @GetMapping("/images")
    public ResponseEntity<List<UploadResponse>> getAllImages() {
        List<ProfileImage> images = profileImageRepository.findAllByOrderByUploadedAtDesc();

        List<UploadResponse> responses = images.stream()
                .map(img -> UploadResponse.builder()
                        .id(img.getId())
                        .fileName(img.getFileName())
                        .fileUrl(img.getFileUrl())
                        .contentType(img.getContentType())
                        .fileSize(img.getFileSize())
                        .uploadedAt(img.getUploadedAt())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}
