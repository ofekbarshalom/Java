package com.securefromscratch.busybee.controllers;

import com.securefromscratch.busybee.safety.ImageName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@RestController
public class ImageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);
    private static final long MAX_IMAGE_BYTES = 10L * 1024 * 1024; // 10 MB
    private static final Set<String> ALLOWED_IMAGE_EXTENSIONS = Set.of(
            ".jpg", ".jpeg", ".png", ".gif"
    );

    @PreAuthorize("hasRole('ADMIN') or @taskAuthorization.imgIsInOwnedOrAssignedTask(#img, authentication)")
    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage(@RequestParam ImageName img) throws IOException {
        Path uploadsDir = Path.of("uploads").toAbsolutePath();
        Path imagePath = uploadsDir.resolve(img.getName()).normalize();

        if (img.getName() == null || img.getName().isBlank()) {
            throw new IllegalArgumentException("Image name is required");
        }

        String lowerName = img.getName().toLowerCase();
        boolean extensionAllowed = ALLOWED_IMAGE_EXTENSIONS.stream().anyMatch(lowerName::endsWith);
        if (!extensionAllowed) {
            throw new IllegalArgumentException("Unsupported image extension");
        }

        if (!imagePath.startsWith(uploadsDir)) {
            LOGGER.warn("Path traversal attempt: {}", img.getName());
            throw new IllegalArgumentException("Path traversal attempt: " + img.getName());
        }

        if (!Files.isReadable(imagePath) || Files.isDirectory(imagePath)) {
            LOGGER.warn("Image not found or is a directory: {}", imagePath);
            throw new java.io.FileNotFoundException("Image not found or is not a file: " + img.getName());
        }

        long fileSize = Files.size(imagePath);
        if (fileSize > MAX_IMAGE_BYTES) {
            throw new IllegalArgumentException("Image exceeds maximum allowed size of " + (MAX_IMAGE_BYTES / 1024 / 1024) + " MB");
        }

        String contentType = Files.probeContentType(imagePath);
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File is not a valid image");
        }

        byte[] imgBytes = Files.readAllBytes(imagePath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(imgBytes);
    }
}