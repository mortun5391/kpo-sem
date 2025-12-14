package com.antiplagiarism.analysis.repository;

import com.antiplagiarism.analysis.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
    List<ReportEntity> findByWorkId(Long workId);
    Optional<ReportEntity> findTopByWorkIdOrderByCreatedAtDesc(Long workId);
    Optional<ReportEntity> findTopByWorkIdAndStatus(Long workId, String status);
    boolean existsByWorkIdAndStatus(Long workId, String status);

    @Query("SELECT r FROM ReportEntity r WHERE r.workId != :workId AND r.status = 'COMPLETED'")
    List<ReportEntity> findCompletedReportsExcludingWork(Long workId);

    List<ReportEntity> findByPlagiarized(boolean plagiarized);
    List<ReportEntity> findByStatus(String status);
}