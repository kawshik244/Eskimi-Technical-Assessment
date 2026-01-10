package com.example.dhakaweather.serviceImpl;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class NumberToWordsServiceImpl {

    private static final String[] UNITS = {
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String[] TENS = {
            "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    public String convert(double number) {
        // Round to 2 decimal places
        BigDecimal bd = BigDecimal.valueOf(number).setScale(2, RoundingMode.HALF_UP);
        String numStr = bd.toPlainString();

        StringBuilder result = new StringBuilder();

        //  Handle Sign
        if (bd.compareTo(BigDecimal.ZERO) < 0) {
            result.append("minus ");
            numStr = numStr.substring(1); // Remove '-'
        } else {
            result.append("positive ");
        }

        //  Split Integer and Decimal
        String[] parts = numStr.split("\\.");
        int integerPart = Integer.parseInt(parts[0]);

        result.append(convertInteger(integerPart));

        //  Handle Decimal Part
        if (parts.length > 1) {
            String decimalPart = parts[1];
            result.append(" point");
            for (char c : decimalPart.toCharArray()) {
                result.append(" ").append(UNITS[c - '0']);
            }
        }

        // Clean up double spaces if any
        return result.toString().trim();
    }

    private String convertInteger(int num) {
        if (num == 0) return "zero";

        StringBuilder sb = new StringBuilder();
        if (num >= 100) {
            sb.append(UNITS[num / 100]).append(" hundred");
            num %= 100;
            if (num > 0) sb.append(" ");
        }
        if (num > 0) {
            if (num < 20) {
                sb.append(UNITS[num]);
            } else {
                sb.append(TENS[num / 10]);
                if (num % 10 > 0) sb.append(" ").append(UNITS[num % 10]);
            }
        }
        return sb.toString();
    }
}