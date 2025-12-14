package com.antiplagiarism.filestoring.service;

import com.antiplagiarism.filestoring.dto.UploadRequest;
import com.antiplagiarism.filestoring.dto.UploadResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    UploadResponse storeFile(UploadRequest request);
    Resource loadFileAsResource(Long workId);
    void deleteFile(Long workId);
    String getFileStoragePath(Long workId);
}