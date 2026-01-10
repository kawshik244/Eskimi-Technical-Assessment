package com.example.NumberOfWords.dto;

public class NumberResponse {
    private String result;

    public NumberResponse(String result) {
        this.result = result;
    }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
}