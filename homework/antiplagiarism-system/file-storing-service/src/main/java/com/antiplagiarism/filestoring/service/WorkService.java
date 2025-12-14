package com.antiplagiarism.filestoring.service;

import com.antiplagiarism.filestoring.dto.WorkDTO;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface WorkService {
    WorkDTO getWorkById(Long id);
    List<WorkDTO> getAllWorks();
    List<WorkDTO> getWorksByStudentId(String studentId);
    List<WorkDTO> getWorksByAssignmentId(String assignmentId);
    WorkDTO updateWorkStatus(Long workId, String status);
}