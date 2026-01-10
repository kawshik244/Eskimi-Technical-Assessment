package com.example.dhakaweather.controller;

import com.example.dhakaweather.dto.WeatherRequest;
import com.example.dhakaweather.dto.WeatherResponse;
import com.example.dhakaweather.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @PostMapping("/dhaka")
    public ResponseEntity<WeatherResponse> getDhakaStats(@RequestBody WeatherRequest request) {
        WeatherResponse response = service.getStats(request.getStartDate(), request.getEndDate());
        return ResponseEntity.ok(response);
    }
}