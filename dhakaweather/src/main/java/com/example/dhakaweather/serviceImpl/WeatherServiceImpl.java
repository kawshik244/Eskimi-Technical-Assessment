package com.example.dhakaweather.serviceImpl;

import com.example.dhakaweather.exception.InvalidRequestException;
import com.example.dhakaweather.dto.OpenMeteoResponse;
import com.example.dhakaweather.dto.WeatherResponse;
import com.example.dhakaweather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final OpenMeteoClient apiClient;
    private final NumberToWordsServiceImpl textService;

    @Autowired
    public WeatherServiceImpl(OpenMeteoClient apiClient, NumberToWordsServiceImpl textService) {
        this.apiClient = apiClient;
        this.textService = textService;
    }

    @Override
    public WeatherResponse getStats(String startDate, String endDate) {
        validateDates(startDate, endDate);

        OpenMeteoResponse response = apiClient.fetchDhakaWeather(startDate, endDate);

        if (response == null || response.getDaily() == null ||
                response.getDaily().getTemperature_2m_mean() == null ||
                response.getDaily().getTemperature_2m_mean().isEmpty()) {
            throw new InvalidRequestException("No weather data found for the given range.");
        }

        List<Double> temps = response.getDaily().getTemperature_2m_mean();

        // Calculate Stats
        // Filter out nulls if API returns incomplete data
        DoubleSummaryStatistics stats = temps.stream()
                .filter(t -> t != null)
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();

        if (stats.getCount() == 0) {
            throw new InvalidRequestException("All temperature data points were null.");
        }

        double min = round(stats.getMin());
        double max = round(stats.getMax());
        double avg = round(stats.getAverage());

        WeatherResponse res = new WeatherResponse();
        res.setMin(min);
        res.setMax(max);
        res.setAverage(avg);

        res.setMinText(textService.convert(min));
        res.setMaxText(textService.convert(max));
        res.setAverageText(textService.convert(avg));

        return res;
    }

    private void validateDates(String start, String end) {
        try {
            LocalDate s = LocalDate.parse(start);
            LocalDate e = LocalDate.parse(end);

            // Check Logical Order
            if (s.isAfter(e)) {
                throw new InvalidRequestException("Start date cannot be after end date.");
            }

            // Check Data Availability (Archive API limitation)
            if (e.isAfter(LocalDate.now().minusDays(1))) {
                throw new InvalidRequestException("Data unavailable for today or future dates. Please use a date up to yesterday.");
            }

        } catch (DateTimeParseException ex) {
            throw new InvalidRequestException("Date format must be YYYY-MM-DD");
        }
    }

    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}