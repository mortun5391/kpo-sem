package com.antiplagiarism.filestoring.service.impl;

import com.antiplagiarism.filestoring.dto.WorkDTO;
import com.antiplagiarism.filestoring.entity.WorkEntity;
import com.antiplagiarism.filestoring.exception.FileNotFoundException;
import com.antiplagiarism.filestoring.repository.WorkRepository;
import com.antiplagiarism.filestoring.service.WorkService;
import com.antiplagiarism.shared.constants.ApiConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkServiceImpl implements WorkService {

    private final WorkRepository workRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    @Cacheable(value = "works", key = "#id")
    public WorkDTO getWorkById(Long id) {
        log.info("Fetching work from database: id={}", id);
        WorkEntity work = workRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("Work not found with id: " + id));

        return convertToDTO(work);
    }

    @Override
    public List<WorkDTO> getAllWorks() {
        return workRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "studentWorks", key = "#studentId")
    public List<WorkDTO> getWorksByStudentId(String studentId) {
        log.info("Fetching works for student from database: studentId={}", studentId);
        return workRepository.findByStudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "assignmentWorks", key = "#assignmentId")
    public List<WorkDTO> getWorksByAssignmentId(String assignmentId) {
        log.info("Fetching works for assignment from database: assignmentId={}", assignmentId);
        return workRepository.findByAssignmentId(assignmentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = {"works", "studentWorks", "assignmentWorks"}, allEntries = true)
    public WorkDTO updateWorkStatus(Long workId, String status) {
        WorkEntity work = workRepository.findById(workId)
                .orElseThrow(() -> new FileNotFoundException("Work not found with id: " + workId));

        work.setStatus(status);
        WorkEntity updatedWork = workRepository.save(work);

        // Инвалидируем кэш для конкретной работы
        String cacheKey = ApiConstants.CACHE_WORK_PREFIX + workId;
        redisTemplate.delete(cacheKey);

        log.info("Work status updated: workId={}, newStatus={}", workId, status);
        return convertToDTO(updatedWork);
    }

    private WorkDTO convertToDTO(WorkEntity entity) {
        return WorkDTO.builder()
                .id(entity.getId())
                .studentId(entity.getStudentId())
                .assignmentId(entity.getAssignmentId())
                .originalFileName(entity.getOriginalFileName())
                .storedFileName(entity.getStoredFileName())
                .fileSize(entity.getFileSize())
                .mimeType(entity.getMimeType())
                .uploadDate(entity.getUploadDate())
                .status(entity.getStatus())
                .build();
    }
}