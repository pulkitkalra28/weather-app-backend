package com.example.weatherapp.response;

import lombok.Data;

@Data
public class WeatherResponse {
    private String apiProviderName;
    private double temperature;
    private String weather;
    private long responseTime;
    private long startTime;
    private long endTime;
    private boolean isError;
    private String errorMessage;
}
