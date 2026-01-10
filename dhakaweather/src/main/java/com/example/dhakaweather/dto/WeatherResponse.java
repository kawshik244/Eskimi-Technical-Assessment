package com.example.dhakaweather.dto;

import lombok.Data;

@Data
public class WeatherResponse {
    private double min;
    private double max;
    private double average;
    private String minText;
    private String maxText;
    private String averageText;
}