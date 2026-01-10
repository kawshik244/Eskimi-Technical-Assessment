package com.example.dateCalculation.controller;

import com.example.dateCalculation.dto.DateRequest;
import com.example.dateCalculation.dto.DateResponse;
import com.example.dateCalculation.service.DateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DateController {

    private final DateService dateService;

    public DateController(DateService dateService) {
        this.dateService = dateService;
    }

    @PostMapping("/api/v1/calculate")
    public ResponseEntity<DateResponse> calculateDifference(@RequestBody DateRequest request) {
        long difference = dateService.calculateDaysBetween(request.getStartDate(), request.getEndDate());
        return ResponseEntity.ok(new DateResponse(difference));
    }
}
