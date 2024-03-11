package com.system.expenseTracker.service.other;

import com.system.expenseTracker.dto.requestDto.ArimaRequestDto;
import com.system.expenseTracker.dto.responseDto.ArimaResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ArimaApiService {
    private final WebClient webClient;
    private final String arimaApiUrl;
    @Autowired
    public ArimaApiService(WebClient.Builder webClientBuilder, @Value("${arima.api.url}") String arimaApiUrl) {
        this.webClient = webClientBuilder.baseUrl(arimaApiUrl).build();
        this.arimaApiUrl = arimaApiUrl;
    }

    public ArimaResponseDto getArimaPrediction(ArimaRequestDto arimaRequest) {
        // Assuming you have an endpoint like "/api/j-arima/predict" in your ARIMA API
        String endpoint = "/j-arima/predict";

        // Make a POST request to the ARIMA API using WebClient
        ArimaResponseDto arimaResponse = webClient.post()
                .uri(endpoint)
                .bodyValue(arimaRequest)
                .retrieve()
                .bodyToMono(ArimaResponseDto.class)
                .block(); // Use subscribe or other reactive patterns in a non-blocking environment

        return arimaResponse;
    }
}
