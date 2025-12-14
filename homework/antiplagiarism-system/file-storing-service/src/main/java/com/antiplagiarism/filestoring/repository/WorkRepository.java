package com.antiplagiarism.filestoring.repository;

import com.antiplagiarism.filestoring.entity.WorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkRepository extends JpaRepository<WorkEntity, Long> {
    List<WorkEntity> findByStudentId(String studentId);
    List<WorkEntity> findByAssignmentId(String assignmentId);
    Optional<WorkEntity> findByStoredFileName(String storedFileName);
    List<WorkEntity> findByStatus(String status);
    boolean existsByStudentIdAndAssignmentId(String studentId, String assignmentId);
}