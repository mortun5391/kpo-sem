package com.antiplagiarism.filestoring.service.impl;

import com.antiplagiarism.filestoring.dto.UploadRequest;
import com.antiplagiarism.filestoring.dto.UploadResponse;
import com.antiplagiarism.filestoring.entity.WorkEntity;
import com.antiplagiarism.filestoring.exception.FileNotFoundException;
import com.antiplagiarism.filestoring.exception.FileStorageException;
import com.antiplagiarism.filestoring.repository.WorkRepository;
import com.antiplagiarism.filestoring.service.FileStorageService;
import com.antiplagiarism.filestoring.service.WorkService;
import com.antiplagiarism.shared.constants.ApiConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    private final WorkRepository workRepository;
    private final WorkService workService;
    private final WebClient analysisServiceWebClient;

    @Value("${file.upload.dir:./uploads}")
    private String uploadDir;

    @Value("${file.max-size:10485760}") // 10MB по умолчанию
    private long maxFileSize;

    @Override
    @Transactional
    public UploadResponse storeFile(UploadRequest request) {
        MultipartFile file = request.getFile();

        // Валидация файла
        validateFile(file);

        // Генерация уникального имени файла
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = generateUniqueFileName(fileExtension);

        try {
            // Создание директории, если не существует
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            // Сохранение файла
            Path targetLocation = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Сохранение метаданных в БД
            WorkEntity work = WorkEntity.builder()
                    .studentId(request.getStudentId())
                    .assignmentId(request.getAssignmentId())
                    .originalFileName(originalFileName)
                    .storedFileName(uniqueFileName)
                    .fileSize(file.getSize())
                    .filePath(targetLocation.toString())
                    .mimeType(file.getContentType())
                    .uploadDate(LocalDateTime.now())
                    .status("UPLOADED")
                    .build();

            WorkEntity savedWork = workRepository.save(work);
            triggerAnalysisAsync(savedWork.getId());

            log.info("File uploaded successfully: workId={}, fileName={}",
                    savedWork.getId(), uniqueFileName);

            return UploadResponse.builder()
                    .workId(savedWork.getId())
                    .fileName(originalFileName)
                    .storedFileName(uniqueFileName)
                    .fileSize(file.getSize())
                    .uploadDate(savedWork.getUploadDate())
                    .downloadUri("/api/v1/files/" + savedWork.getId() + "/file")
                    .build();

        } catch (IOException ex) {
            log.error("Could not store file: {}", originalFileName, ex);
            throw new FileStorageException("Could not store file " + originalFileName, ex);
        }
    }

    private void triggerAnalysisAsync(Long workId) {
        try {
            analysisServiceWebClient.post()
                    .uri("/api/v1/analyze")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(Map.of("workId", workId))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .subscribe(
                            response -> log.info("Analysis triggered for workId: {}", workId),
                            error -> log.error("Failed to trigger analysis for workId: {}", workId, error)
                    );
        } catch (Exception e) {
            log.error("Error triggering analysis for workId: {}", workId, e);
        }
    }

    @Override
    public Resource loadFileAsResource(Long workId) {
        WorkEntity work = workRepository.findById(workId)
                .orElseThrow(() -> new FileNotFoundException("Work not found with id: " + workId));

        try {
            Path filePath = Paths.get(work.getFilePath()).toAbsolutePath().normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                log.error("File not found or not readable: {}", work.getFilePath());
                throw new FileNotFoundException("File not found: " + work.getOriginalFileName());
            }
        } catch (MalformedURLException ex) {
            log.error("Malformed URL for file: {}", work.getFilePath(), ex);
            throw new FileNotFoundException("File not found: " + work.getOriginalFileName(), ex);
        }
    }

    @Override
    public void deleteFile(Long workId) {
        WorkEntity work = workRepository.findById(workId)
                .orElseThrow(() -> new FileNotFoundException("Work not found with id: " + workId));

        try {
            Path filePath = Paths.get(work.getFilePath());
            Files.deleteIfExists(filePath);
            workRepository.delete(work);
            log.info("File deleted successfully: workId={}", workId);
        } catch (IOException ex) {
            log.error("Could not delete file: {}", work.getFilePath(), ex);
            throw new FileStorageException("Could not delete file: " + work.getOriginalFileName(), ex);
        }
    }

    @Override
    public String getFileStoragePath(Long workId) {
        WorkEntity work = workRepository.findById(workId)
                .orElseThrow(() -> new FileNotFoundException("Work not found with id: " + workId));
        return work.getFilePath();
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileStorageException("File is empty");
        }

        if (file.getSize() > maxFileSize) {
            throw new FileStorageException("File size exceeds maximum allowed size: " + maxFileSize);
        }

        String contentType = file.getContentType();
        boolean isValidType = false;
        for (String allowedType : ApiConstants.ALLOWED_FILE_TYPES) {
            if (allowedType.equals(contentType)) {
                isValidType = true;
                break;
            }
        }

        if (!isValidType) {
            throw new FileStorageException("File type not allowed: " + contentType);
        }
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex);
        }
        return "";
    }

    private String generateUniqueFileName(String fileExtension) {
        return UUID.randomUUID().toString() + fileExtension;
    }
}