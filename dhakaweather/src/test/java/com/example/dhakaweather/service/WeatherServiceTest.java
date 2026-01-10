package com.example.dhakaweather.service;

import com.example.dhakaweather.exception.InvalidRequestException;
import com.example.dhakaweather.dto.OpenMeteoResponse;
import com.example.dhakaweather.dto.WeatherResponse;
import com.example.dhakaweather.serviceImpl.NumberToWordsServiceImpl;
import com.example.dhakaweather.serviceImpl.OpenMeteoClient;
import com.example.dhakaweather.serviceImpl.WeatherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private OpenMeteoClient apiClient;

    @Mock
    private NumberToWordsServiceImpl textService;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Test
    void testStandardCalculation() {
        // Setup Mock Data
        OpenMeteoResponse mockResponse = new OpenMeteoResponse();
        OpenMeteoResponse.Daily daily = new OpenMeteoResponse.Daily();
        // data: 10.0, 20.0, 30.0 -> Avg 20.0
        daily.setTemperature_2m_mean(Arrays.asList(10.0, 20.0, 30.0));
        mockResponse.setDaily(daily);

        when(apiClient.fetchDhakaWeather(anyString(), anyString())).thenReturn(mockResponse);

        // Mock Text Conversion (Simplified for test)
        when(textService.convert(10.0)).thenReturn("positive ten");
        when(textService.convert(30.0)).thenReturn("positive thirty");
        when(textService.convert(20.0)).thenReturn("positive twenty");

        WeatherResponse res = weatherService.getStats("2023-01-01", "2023-01-03");

        assertEquals(10.0, res.getMin());
        assertEquals(30.0, res.getMax());
        assertEquals(20.0, res.getAverage());
        assertEquals("positive ten", res.getMinText());
    }

    @Test
    void testFloatingPointRounding() {
        // Data: 1.123, 1.127 -> Avg 1.125 -> rounds to 1.13
        OpenMeteoResponse mockResponse = new OpenMeteoResponse();
        OpenMeteoResponse.Daily daily = new OpenMeteoResponse.Daily();
        daily.setTemperature_2m_mean(Arrays.asList(1.123, 1.127));
        mockResponse.setDaily(daily);

        when(apiClient.fetchDhakaWeather(anyString(), anyString())).thenReturn(mockResponse);
        when(textService.convert(anyDouble())).thenReturn("text");

        WeatherResponse res = weatherService.getStats("2023-01-01", "2023-01-02");

        // Min should be 1.12, Max 1.13
        assertEquals(1.12, res.getMin());
        assertEquals(1.13, res.getMax());
        // Avg (1.123+1.127)/2 = 1.125 -> 1.13
        assertEquals(1.13, res.getAverage());
    }

    @Test
    void testInvalidDateOrder() {
        assertThrows(InvalidRequestException.class, () ->
                weatherService.getStats("2023-02-01", "2023-01-01"));
    }
}