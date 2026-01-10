package com.example.dhakaweather.serviceImpl;

import com.example.dhakaweather.dto.OpenMeteoResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OpenMeteoClient {

    private final WebClient webClient;

    public OpenMeteoClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://archive-api.open-meteo.com").build();
    }

    public OpenMeteoResponse fetchDhakaWeather(String startDate, String endDate) {
        // Dhaka Coords: Lat 23.8103, Lon 90.4125
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/archive")
                        .queryParam("latitude", "23.8103")
                        .queryParam("longitude", "90.4125")
                        .queryParam("start_date", startDate)
                        .queryParam("end_date", endDate)
                        .queryParam("daily", "temperature_2m_mean")
                        .queryParam("timezone", "auto")
                        .build())
                .retrieve()
                .bodyToMono(OpenMeteoResponse.class)
                .block(); // Blocking for simplicity in this synchronous service flow
    }
}