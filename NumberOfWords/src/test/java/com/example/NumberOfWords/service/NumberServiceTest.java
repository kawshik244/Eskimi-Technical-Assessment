package com.example.NumberOfWords.service;

import com.example.NumberOfWords.exception.InvalidNumberException;
import com.example.NumberOfWords.serviceImpl.NumberServiceImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NumberServiceTest {

    private final NumberService service = new NumberServiceImpl();

    @Test
    void testStandardTens() {
        assertEquals("thirty six", service.convertToWords("36"));
    }

    @Test
    void testStandardHundreds() {
        assertEquals("one hundred five", service.convertToWords("105"));
    }

    @Test
    void testDecimalWithTrailingZero() {
        // Requirement: 36.40 -> "thirty six point four zero"
        assertEquals("thirty six point four zero", service.convertToWords("36.40"));
    }

    @Test
    void testDecimalWithoutTrailingZero() {
        assertEquals("thirty six point four", service.convertToWords("36.4"));
    }

    @Test
    void testZero() {
        assertEquals("zero", service.convertToWords("0"));
    }

    @Test
    void testZeroWithDecimals() {
        assertEquals("zero point five", service.convertToWords("0.5"));
    }

    @Test
    void testTeens() {
        assertEquals("nineteen", service.convertToWords("19"));
        assertEquals("one hundred twelve", service.convertToWords("112"));
    }

    @Test
    void testMaxLimitBoundary() {
        assertEquals("nine hundred ninety nine point nine nine", service.convertToWords("999.99"));
    }

    @Test
    void testInvalidRangeHigh() {
        assertThrows(InvalidNumberException.class, () -> service.convertToWords("1000"));
    }

    @Test
    void testInvalidNegative() {
        assertThrows(InvalidNumberException.class, () -> service.convertToWords("-5"));
    }

    @Test
    void testInvalidFormat() {
        assertThrows(InvalidNumberException.class, () -> service.convertToWords("abc"));
        assertThrows(InvalidNumberException.class, () -> service.convertToWords("12.345")); // >2 decimal places
    }
}