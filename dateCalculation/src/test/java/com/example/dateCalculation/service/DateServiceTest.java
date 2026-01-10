package com.example.dateCalculation.service;

import com.example.dateCalculation.exception.InvalidDateException;
import com.example.dateCalculation.serviceImpl.DateServiceImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DateServiceTest {

    private final DateService service = new DateServiceImpl();

    @Test
    void testSameDate() {
        assertEquals(0, service.calculateDaysBetween("2023-01-01", "2023-01-01"));
    }

    @Test
    void testOneDayDifference() {
        assertEquals(1, service.calculateDaysBetween("2023-01-01", "2023-01-02"));
    }

    @Test
    void testOverNewYear() {
        assertEquals(1, service.calculateDaysBetween("2022-12-31", "2023-01-01"));
    }

    @Test
    void testStandardLeapYear() {
        // 2024 is a leap year, Feb has 29 days
        assertEquals(1, service.calculateDaysBetween("2024-02-28", "2024-02-29"));
        assertEquals(2, service.calculateDaysBetween("2024-02-28", "2024-03-01"));
    }

    @Test
    void testNonLeapYear() {
        // 2023 is not a leap year, Feb has 28 days
        assertEquals(1, service.calculateDaysBetween("2023-02-28", "2023-03-01"));
    }

    @Test
    void testCenturyLeapYearRule() {
        // 2000 was a leap year (divisible by 400)
        assertEquals(2, service.calculateDaysBetween("2000-02-28", "2000-03-01"));

        // 1900 was NOT a leap year (divisible by 100 but not 400)
        assertEquals(1, service.calculateDaysBetween("1900-02-28", "1900-03-01"));
    }

    @Test
    void testLongDistance() {
        // Approx 1 year
        assertEquals(365, service.calculateDaysBetween("2022-01-01", "2023-01-01"));
    }

    @Test
    void testInvalidDateFormat() {
        assertThrows(InvalidDateException.class, () ->
                service.calculateDaysBetween("2023/01/01", "2023-01-02"));
    }

    @Test
    void testInvalidDayCount() {
        // April has 30 days
        assertThrows(InvalidDateException.class, () ->
                service.calculateDaysBetween("2023-04-31", "2023-05-01"));
    }

    @Test
    void testInvalidLeapDay() {
        // 2023 is not leap, cannot have Feb 29
        assertThrows(InvalidDateException.class, () ->
                service.calculateDaysBetween("2023-02-29", "2023-03-01"));
    }

}
