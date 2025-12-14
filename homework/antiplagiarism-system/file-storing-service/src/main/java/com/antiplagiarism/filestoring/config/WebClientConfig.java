// file-storing-service/src/main/java/com/antiplagiarism/filestoring/config/WebClientConfig.java
package com.antiplagiarism.filestoring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${analysis.service.url:http://analysis-service:8082}")
    private String analysisServiceUrl;

    @Bean
    public WebClient analysisServiceWebClient() {
        return WebClient.builder()
                .baseUrl(analysisServiceUrl)
                .build();
    }
}