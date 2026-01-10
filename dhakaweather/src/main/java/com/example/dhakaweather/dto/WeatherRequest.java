package com.example.dhakaweather.dto;

public class WeatherRequest {
    private String startDate;
    private String endDate;

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
}