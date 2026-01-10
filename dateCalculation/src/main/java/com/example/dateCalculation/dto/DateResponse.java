package com.example.dateCalculation.dto;

public class DateResponse {

    private long daysDifference;

    public DateResponse(long daysDifference) {
        this.daysDifference = daysDifference;
    }

    public long getDaysDifference() { return daysDifference; }
    public void setDaysDifference(long daysDifference) { this.daysDifference = daysDifference; }
}
