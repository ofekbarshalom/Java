package com.securefromscratch.busybee.storage;

import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileStore;
import java.util.UUID;

public class FileStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorage.class);
    
    public enum FileType {
        IMAGE,
        PDF,
        OTHER
    }

    private static final int UUID_LENGTH = UUID.randomUUID().toString().length();
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB
    private static final long MIN_DISK_SPACE = 50 * 1024 * 1024; // 50 MB minimum free space
    private static final String[] ALLOWED_IMAGE_EXTENSIONS = {".png", ".jpg", ".jpeg", ".gif", ".webp"};
    private static final String ALLOWED_PDF_EXTENSION = ".pdf";

    private final Path m_storagebox;

    public FileStorage(Path storageDirectory) throws IOException {
        m_storagebox = storageDirectory;
        Path rootPath = m_storagebox.toAbsolutePath();
        if (!Files.exists(rootPath)) {
            Files.createDirectories(rootPath);
        }
    }

    public String store(MultipartFile file) throws IOException {
        // Validate file
        validateFile(file);
        
        // Check disk space before proceeding
        checkDiskSpace();

        String extension = extractExtension(file.getOriginalFilename());
        String storedFilename = UUID.randomUUID().toString() + extension;
        Path destinationFile = m_storagebox.resolve(storedFilename).normalize();

        if (!destinationFile.getParent().equals(m_storagebox)) {
            LOGGER.warn("File store attempt outside storage directory: {}", storedFilename);
            throw new IOException("Cannot store file outside current directory!");
        }

        try (var inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile);
            LOGGER.info("File stored successfully: {} (original name: {})", storedFilename, file.getOriginalFilename());
        }
        return storedFilename;
    }

    private void validateFile(MultipartFile file) {
        // Check if file is empty
        if (file.isEmpty()) {
            LOGGER.warn("File upload validation failed: file is empty");
            throw new InvalidFileUploadException("File is empty");
        }

        // Check file size
        if (file.getSize() > MAX_FILE_SIZE) {
            LOGGER.warn("File upload validation failed: file size {} exceeds maximum {} MB", file.getSize(), MAX_FILE_SIZE / 1024 / 1024);
            throw new InvalidFileUploadException("File size exceeds maximum allowed size of " + (MAX_FILE_SIZE / 1024 / 1024) + " MB");
        }

        // Check file type
        FileType filetype = identifyType(file);
        if (filetype == FileType.OTHER) {
            LOGGER.warn("File upload validation failed: unsupported file type {}", file.getContentType());
            throw new InvalidFileUploadException("File type not allowed. Only IMAGE and PDF files are supported");
        }

        // Validate MIME type matches file type
        validateMimeType(file, filetype);

        // Check filename
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            LOGGER.warn("File upload validation failed: invalid filename");
            throw new InvalidFileUploadException("Invalid filename");
        }

        if (originalFilename.length() > 255) {
            LOGGER.warn("File upload validation failed: filename too long ({})", originalFilename.length());
            throw new InvalidFileUploadException("Filename is too long");
        }

        String extension = extractExtension(originalFilename).toLowerCase();
        if (extension.isEmpty()) {
            LOGGER.warn("File upload validation failed: missing file extension");
            throw new InvalidFileUploadException("Missing file extension");
        }
        if (filetype == FileType.IMAGE && !isAllowedImageExtension(extension)) {
            LOGGER.warn("File upload validation failed: unsupported image extension {}", extension);
            throw new InvalidFileUploadException("Unsupported image extension: " + extension);
        }
        if (filetype == FileType.PDF && !ALLOWED_PDF_EXTENSION.equals(extension)) {
            LOGGER.warn("File upload validation failed: unsupported PDF extension {}", extension);
            throw new InvalidFileUploadException("Unsupported PDF extension: " + extension);
        }

        // Prevent path traversal in filename
        if (originalFilename.contains("..") || originalFilename.contains("/") || originalFilename.contains("\\")) {
            LOGGER.warn("File upload validation failed: path traversal attempt detected in filename: {}", originalFilename);
            throw new InvalidFileUploadException("Invalid filename: path traversal attempt detected");
        }
    }

    private void validateMimeType(MultipartFile file, FileType filetype) {
        String contentType = file.getContentType();
        if (contentType == null) {
            LOGGER.warn("File upload validation failed: missing MIME type for file {}", file.getOriginalFilename());
            throw new InvalidFileUploadException("Missing MIME type");
        }

        contentType = contentType.toLowerCase();
        
        if (filetype == FileType.IMAGE && !contentType.startsWith("image/")) {
            LOGGER.warn("File upload validation failed: MIME type mismatch for image file: {}", contentType);
            throw new InvalidFileUploadException("MIME type mismatch: file claims to be image but has type " + contentType);
        }

        if (filetype == FileType.PDF && !contentType.contains("pdf")) {
            LOGGER.warn("File upload validation failed: MIME type mismatch for PDF file: {}", contentType);
            throw new InvalidFileUploadException("MIME type mismatch: file claims to be PDF but has type " + contentType);
        }
    }

    private void checkDiskSpace() throws IOException {
        FileStore store = Files.getFileStore(m_storagebox);
        long usableSpace = store.getUsableSpace();

        if (usableSpace < MIN_DISK_SPACE) {
            LOGGER.error("Insufficient disk space: required {} MB, available {} MB", MIN_DISK_SPACE / 1024 / 1024, usableSpace / 1024 / 1024);
            throw new InsufficientDiskSpaceException("Not enough disk space available. Required: " + (MIN_DISK_SPACE / 1024 / 1024) + " MB, Available: " + (usableSpace / 1024 / 1024) + " MB");
        }
    }

    public byte[] getBytes(String filename) throws IOException {
        Path filepath = m_storagebox.resolve(filename);
        byte[] serialized = Files.readAllBytes(filepath);
        return serialized;
    }

    public static FileType identifyType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null) {
            return FileType.OTHER;
        }
        contentType = contentType.toLowerCase();
        if (contentType.startsWith("image/")) {
            return FileType.IMAGE;
        }
        if (contentType.contains("pdf")) {
            return FileType.PDF;
        }
        return FileType.OTHER;
    }

    private static String extractExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int lastDot = filename.lastIndexOf('.');
        if (lastDot == -1) {
            return "";
        }
        return filename.substring(lastDot);
    }

    private static boolean isAllowedImageExtension(String extension) {
        for (String allowed : ALLOWED_IMAGE_EXTENSIONS) {
            if (allowed.equals(extension)) {
                return true;
            }
        }
        return false;
    }
}
