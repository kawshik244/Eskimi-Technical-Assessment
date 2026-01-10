package com.example.dhakaweather.service;

import com.example.dhakaweather.dto.WeatherResponse;

public interface WeatherService {
    public WeatherResponse getStats(String startDate, String endDate);
}
