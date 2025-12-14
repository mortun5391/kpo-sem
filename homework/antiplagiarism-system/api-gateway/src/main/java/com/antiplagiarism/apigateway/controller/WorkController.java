package com.antiplagiarism.apigateway.controller;

import com.antiplagiarism.shared.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/works")
public class WorkController {

    @PostMapping("/upload")
    public Mono<ResponseEntity<ApiResponse<?>>> uploadWork(
            @RequestParam("file") MultipartFile file,
            @RequestParam("studentId") String studentId,
            @RequestParam("assignmentId") String assignmentId) {

        return Mono.just(ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.success(null, "File upload request accepted")));
    }

    @GetMapping("/{workId}/reports")
    public Mono<ResponseEntity<ApiResponse<?>>> getWorkReports(@PathVariable Long workId) {
        return Mono.just(ResponseEntity.ok(ApiResponse.success(null, "Reports request accepted")));
    }

    

    @GetMapping("/fallback/works")
    public ResponseEntity<ApiResponse<?>> worksFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ApiResponse.error("File service is temporarily unavailable", "SERVICE_UNAVAILABLE", "/api/works"));
    }

    @GetMapping("/fallback/reports")
    public ResponseEntity<ApiResponse<?>> reportsFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ApiResponse.error("Analysis service is temporarily unavailable", "SERVICE_UNAVAILABLE", "/api/reports"));
    }
}