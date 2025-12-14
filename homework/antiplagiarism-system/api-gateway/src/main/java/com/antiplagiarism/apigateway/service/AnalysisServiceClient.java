package com.antiplagiarism.apigateway.service;

import com.antiplagiarism.shared.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;

@Service
public class AnalysisServiceClient {

    private final WebClient webClient;

    @Value("${analysis.service.url:http://analysis-service:8082}")
    private String analysisServiceUrl;

    public AnalysisServiceClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(analysisServiceUrl).build();
    }

    public Mono<ApiResponse<?>> analyzeWork(Long workId) {
        return webClient.post()
                .uri("/api/v1/analyze")
                .bodyValue(Map.of("workId", workId))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<?>>() {});
    }

    public Mono<ApiResponse<?>> getReport(Long reportId) {
        return webClient.get()
                .uri("/api/v1/reports/{id}", reportId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<?>>() {});
    }

    public Mono<ApiResponse<?>> getWorkReports(Long workId) {
        return webClient.get()
                .uri("/api/v1/reports/work/{workId}", workId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<?>>() {});
    }

    public Mono<ApiResponse<?>> generateWordCloud(Long workId) {
        return webClient.get()
                .uri("/api/v1/wordcloud/{workId}", workId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<?>>() {});
    }


}