package com.example.dhakaweather.dto;

import java.util.List;

public class OpenMeteoResponse {
    private Daily daily;

    public Daily getDaily() { return daily; }
    public void setDaily(Daily daily) { this.daily = daily; }

    public static class Daily {
        // Open-Meteo returns "temperature_2m_mean" list
        private List<Double> temperature_2m_mean;

        public List<Double> getTemperature_2m_mean() { return temperature_2m_mean; }
        public void setTemperature_2m_mean(List<Double> temperature_2m_mean) {
            this.temperature_2m_mean = temperature_2m_mean;
        }
    }
}