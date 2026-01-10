package com.example.NumberOfWords.controller;

import com.example.NumberOfWords.dto.NumberRequest;
import com.example.NumberOfWords.dto.NumberResponse;
import com.example.NumberOfWords.service.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/numbers")
public class NumberController {

    private final NumberService numberService;

    public NumberController(NumberService numberService) {
        this.numberService = numberService;
    }

    @PostMapping("/convert")
    public ResponseEntity<NumberResponse> convertNumber(@RequestBody NumberRequest request) {
        String result = numberService.convertToWords(request.getNumber());
        return ResponseEntity.ok(new NumberResponse(result));
    }
}