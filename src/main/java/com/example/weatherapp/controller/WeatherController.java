package com.example.weatherapp.controller;

import com.example.weatherapp.response.WeatherResponse;
import com.example.weatherapp.service.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/async")
    public List<WeatherResponse> getWeatherAsync() throws Exception {
        return weatherService.getWeatherDataAsync();
    }

    @GetMapping("/sync")
    public List<WeatherResponse> getWeatherSync() {
        return weatherService.getWeatherDataSync();
    }
}
