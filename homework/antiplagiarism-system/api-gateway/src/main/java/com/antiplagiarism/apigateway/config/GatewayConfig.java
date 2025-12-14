package com.antiplagiarism.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("file-storing-service", r -> r
                        .path("/api/files/**")
                        .filters(f -> f
                                .prefixPath("/api/v1")
                                .rewritePath("/api/files/(?<segment>.*)", "/${segment}"))
                        .uri("http://file-storing-service:8081"))

                .route("analysis-service", r -> r
                        .path("/api/analyze/**", "/api/reports/**", "/api/wordcloud/**")
                        .filters(f -> f
                                .prefixPath("/api/v1")
                                .rewritePath("/api/(?<segment>.*)", "/${segment}"))
                        .uri("http://analysis-service:8082"))

                .route("works-gateway", r -> r
                        .path("/api/works/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("worksCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/works")))
                        .uri("http://file-storing-service:8081"))

                .route("reports-gateway", r -> r
                        .path("/api/works/*/reports")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("reportsCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/reports"))
                                .rewritePath("/api/works/(?<workId>.*)/reports", "/api/v1/reports/work/${workId}"))
                        .uri("http://analysis-service:8082"))

                .build();
    }
}