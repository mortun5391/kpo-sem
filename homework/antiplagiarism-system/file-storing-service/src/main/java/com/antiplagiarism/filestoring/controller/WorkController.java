package com.antiplagiarism.filestoring.controller;

import com.antiplagiarism.filestoring.dto.WorkDTO;
import com.antiplagiarism.filestoring.service.WorkService;
import com.antiplagiarism.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/works")
@Tag(name = "Work Controller", description = "API для работы с метаданными работ")
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;

    @Operation(summary = "Получение информации о работе", description = "Возвращает метаданные работы по ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Информация о работе найдена"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Работа не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkDTO>> getWorkById(
            @Parameter(description = "ID работы", required = true)
            @PathVariable Long id) {

        WorkDTO work = workService.getWorkById(id);
        return ResponseEntity.ok(ApiResponse.success(work));
    }

    @Operation(summary = "Получение всех работ", description = "Возвращает список всех работ")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Список работ получен")
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllWorks() {
        return ResponseEntity.ok(ApiResponse.success(workService.getAllWorks()));
    }

    @Operation(summary = "Получение работ студента", description = "Возвращает все работы конкретного студента")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Работы студента найдены"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Студент не найден")
    })
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<?>> getWorksByStudentId(
            @Parameter(description = "ID студента", required = true)
            @PathVariable String studentId) {

        return ResponseEntity.ok(ApiResponse.success(workService.getWorksByStudentId(studentId)));
    }

    @Operation(summary = "Получение работ по заданию", description = "Возвращает все работы по конкретному заданию")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Работы по заданию найдены"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Задание не найдено")
    })
    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<ApiResponse<?>> getWorksByAssignmentId(
            @Parameter(description = "ID задания", required = true)
            @PathVariable String assignmentId) {

        return ResponseEntity.ok(ApiResponse.success(workService.getWorksByAssignmentId(assignmentId)));
    }
}