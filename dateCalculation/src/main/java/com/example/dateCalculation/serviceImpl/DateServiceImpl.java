package com.example.dateCalculation.serviceImpl;

import com.example.dateCalculation.exception.InvalidDateException;
import com.example.dateCalculation.service.DateService;
import org.springframework.stereotype.Service;

@Service
public class DateServiceImpl implements DateService {
    private static final int[] DAYS_IN_MONTH={0,31,28,31,30,31,30,31,31,30,31,30,31};

    @Override
    public long calculateDaysBetween(String date1,String date2)
    {
        long days1 = dateToAbsolute(date1);
        long days2 = dateToAbsolute(date2);

        if(days1 > days2) return days1-days2;
        else return days2-days1;

    }

    private long dateToAbsolute(String date){
        if(date== null || !date.matches("\\d{4}-\\d{2}-\\d{2}")){
            throw new InvalidDateException("Date format must be YYYY-MM-DD. Invalid input: " + date);
        }

        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        validateDate(year, month, day);

        long totalDays = 0;

        // calculating how many days have passed from Year 0 up to (year-1)
        int prevYear = year - 1;
        totalDays += (long) prevYear * 365;

        // Add leap days for all previous years
        totalDays += prevYear / 4;
        totalDays -= prevYear / 100;
        totalDays += prevYear / 400;

        // Add days for full previous months in current year
        for (int m = 1; m < month; m++) {
            if (m == 2 && isLeapYear(year)) {
                totalDays += 29;
            } else {
                totalDays += DAYS_IN_MONTH[m];
            }
        }

        // Add days in current month
        totalDays += day;

        return totalDays;
    }

    private void validateDate(int year, int month, int day) {
        if (year < 1) {
            throw new InvalidDateException("Year must be positive.");
        }
        if (month < 1 || month > 12) {
            throw new InvalidDateException("Month must be between 01 and 12.");
        }

        int maxDays = DAYS_IN_MONTH[month];
        if (month == 2 && isLeapYear(year)) {
            maxDays = 29;
        }

        if (day < 1 || day > maxDays) {
            throw new InvalidDateException("Invalid day for " + year + "-" + month + ". Expected 1-" + maxDays);
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
