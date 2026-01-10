package com.example.NumberOfWords.serviceImpl;

import com.example.NumberOfWords.exception.InvalidNumberException;
import com.example.NumberOfWords.service.NumberService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class NumberServiceImpl implements NumberService {

    private static final String[] UNITS = {
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String[] TENS = {
            "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    @Override
    public String convertToWords(String numStr){
        if(numStr == null || numStr.trim().isEmpty()){
            throw new InvalidNumberException("Input number cannot be null or empty");
        }

        //validate format(positive number or integer)
        if(!numStr.matches("^\\d+(\\.\\d{1,2})?$")){
            throw new InvalidNumberException("Invalid format. Expected positive number with max 2 decimal places (e.g., '36', '36.40'). Input: " + numStr);
        }

        // Validate range
        BigDecimal number = new BigDecimal(numStr);
        if(number.compareTo(BigDecimal.ZERO) < 0 || number.compareTo(new BigDecimal("1000")) >= 0 ){
            throw new InvalidNumberException("Number must be >= 0 and < 1000");
        }

        StringBuilder result = new StringBuilder();
        String[] parts = numStr.split("\\.");

        //convert interger part
        int integerPart = Integer.parseInt(parts[0]);
        result.append(convertIntegerPart(integerPart));

        //convert decimal part if exist
        if(parts.length > 1){
            result.append(" point");
            String decimalPart = parts[1];
            for(char c:decimalPart.toCharArray()){
                int digit = c - '0';
                result.append(" ").append(UNITS[digit]);
            }
        }

        return result.toString();
    }

    private String convertIntegerPart(int number){
        if(number == 0) return UNITS[0];

        StringBuilder tempResult = new StringBuilder();

        if(number>=100){
            tempResult.append(UNITS[number/100]).append(" hundred");
            number %=100;
            if(number>0){
                tempResult.append(" ");
            }
        }

        //tens and Units
        if(number>0){
            if(number<20){
                tempResult.append(UNITS[number]);
            }
            else{
                tempResult.append(TENS[number/10]);
                if (number % 10 > 0) {
                    tempResult.append(" ").append(UNITS[number % 10]);
                }
            }
        }

        return tempResult.toString();
    }
}
