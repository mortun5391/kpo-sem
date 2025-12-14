package com.antiplagiarism.apigateway.service;

import com.antiplagiarism.shared.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FileServiceClient {

    private final WebClient webClient;

    @Value("${file.service.url:http://file-storing-service:8081}")
    private String fileServiceUrl;

    public FileServiceClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(fileServiceUrl).build();
    }

    public Mono<ApiResponse<?>> uploadFile(MultipartBodyBuilder multipartBodyBuilder) {
        return webClient.post()
                .uri("/api/v1/files/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .map(response -> (ApiResponse<?>) response);
    }

    public Mono<ApiResponse<?>> getWorkInfo(Long workId) {
        return webClient.get()
                .uri("/api/v1/works/{id}", workId)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .map(response -> (ApiResponse<?>) response);
    }

    public Mono<Resource> downloadFile(Long workId) {
        return webClient.get()
                .uri("/api/v1/works/{id}/file", workId)
                .retrieve()
                .bodyToMono(ByteArrayResource.class)
                .cast(Resource.class);
    }
}