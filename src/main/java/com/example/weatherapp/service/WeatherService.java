package com.example.weatherapp.service;

import com.example.weatherapp.response.WeatherResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public WeatherService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    // Async code (Multithreading)

    public CompletableFuture<WeatherResponse> fetchWeatherFromWeatherBitAsync() {
        long startTime = System.currentTimeMillis();
        String url = "https://api.weatherbit.io/v2.0/current?lat=41.6130&lon=-70.9705&key=aaa0214c6cdb4707bf9143e99827a901";

        return CompletableFuture.supplyAsync(() -> {
            WeatherResponse weatherResponse = new WeatherResponse();
            weatherResponse.setApiProviderName("WeatherBit");
            try {
                String responseString = restTemplate.getForObject(url, String.class);
                JsonNode response = objectMapper.readTree(responseString);
                if (response != null && response.get("data") != null &&
                        response.get("data").isArray() && !response.get("data").isEmpty()) {
                    weatherResponse.setTemperature(response.get("data").get(0).get("app_temp").asDouble());
                    long endTime = System.currentTimeMillis();
                    weatherResponse.setStartTime(startTime);
                    weatherResponse.setEndTime(endTime);
                    weatherResponse.setResponseTime(endTime - startTime);
                } else {
                    weatherResponse.setError(true);
                    weatherResponse.setErrorMessage("Could not fetch weather data");
                }
            } catch (Exception e) {
                weatherResponse.setError(true);
                weatherResponse.setErrorMessage("Failed to fetch weather data");
            }
            return weatherResponse;
        });
    }

    public CompletableFuture<WeatherResponse> fetchWeatherFromWeatherAPIAsync() {
        long startTime = System.currentTimeMillis();
        String url = "http://api.weatherapi.com/v1/current.json?key=81f50fcfe0294f23971235451240411&q=41.613,-70.9705";

        return CompletableFuture.supplyAsync(() -> {
            WeatherResponse weatherResponse = new WeatherResponse();
            weatherResponse.setApiProviderName("WeatherAPI");
            try {
                String responseString = restTemplate.getForObject(url, String.class);
                JsonNode response = objectMapper.readTree(responseString);
                if (response != null && response.get("current") != null &&
                        response.get("current").get("temp_c") != null) {
                    weatherResponse.setTemperature(response.get("current").get("temp_c").asDouble());
                    long endTime = System.currentTimeMillis();
                    weatherResponse.setStartTime(startTime);
                    weatherResponse.setEndTime(endTime);
                    weatherResponse.setResponseTime(endTime - startTime);
                } else {
                    weatherResponse.setError(true);
                    weatherResponse.setErrorMessage("Could not fetch weather data");
                }
            } catch (Exception e) {
                weatherResponse.setError(true);
                weatherResponse.setErrorMessage("Failed to fetch weather data");
            }
            return weatherResponse;
        });
    }

    public CompletableFuture<WeatherResponse> fetchWeatherFromOpenWeatherAsync() {
        long startTime = System.currentTimeMillis();
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=41.613&lon=-70.9705&appid=f6d854b3d8928bd439aa1e0e18b9a3a4&units=metric";

        return CompletableFuture.supplyAsync(() -> {
            WeatherResponse weatherResponse = new WeatherResponse();
            weatherResponse.setApiProviderName("OpenWeatherMap");
            try {
                String responseString = restTemplate.getForObject(url, String.class);
                JsonNode response = objectMapper.readTree(responseString);
                if (response != null && response.get("main") != null && response.get("main").get("temp") != null) {
                    weatherResponse.setTemperature(response.get("main").get("temp").asDouble());
                    long endTime = System.currentTimeMillis();
                    weatherResponse.setStartTime(startTime);
                    weatherResponse.setEndTime(endTime);
                    weatherResponse.setResponseTime(endTime - startTime);
                } else {
                    weatherResponse.setError(true);
                    weatherResponse.setErrorMessage("Could not fetch weather data");
                }
            } catch (Exception e) {
                weatherResponse.setError(true);
                weatherResponse.setErrorMessage("Failed to fetch weather data");
            }
            return weatherResponse;
        });
    }

    public List<WeatherResponse> getWeatherDataAsync() throws Exception {
        CompletableFuture<WeatherResponse> weatherBit = fetchWeatherFromWeatherBitAsync();
        CompletableFuture<WeatherResponse> weatherAPI = fetchWeatherFromWeatherAPIAsync();
        CompletableFuture<WeatherResponse> openWeather = fetchWeatherFromOpenWeatherAsync();

        // Wait for all futures to complete and gather results
        return List.of(weatherBit.join(), weatherAPI.join(), openWeather.join());
    }

    // Sync code (Single Thread)

    public WeatherResponse fetchWeatherFromWeatherBitSync() {
        long startTime = System.currentTimeMillis();
        String url = "https://api.weatherbit.io/v2.0/current?lat=41.6130&lon=-70.9705&key=aaa0214c6cdb4707bf9143e99827a901";

        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setApiProviderName("WeatherBit");
        try {
            String responseString = restTemplate.getForObject(url, String.class);
            JsonNode response = objectMapper.readTree(responseString);
            if (response != null && response.get("data") != null &&
                    response.get("data").isArray() && !response.get("data").isEmpty()) {
                weatherResponse.setTemperature(response.get("data").get(0).get("app_temp").asDouble());
                long endTime = System.currentTimeMillis();
                weatherResponse.setStartTime(startTime);
                weatherResponse.setEndTime(endTime);
                weatherResponse.setResponseTime(endTime - startTime);
            } else {
                weatherResponse.setError(true);
                weatherResponse.setErrorMessage("Could not fetch weather data");
            }
        } catch (Exception e) {
            weatherResponse.setError(true);
            weatherResponse.setErrorMessage("Failed to fetch weather data");
        }
        return weatherResponse;
    }

    public WeatherResponse fetchWeatherFromWeatherAPISync() {
        long startTime = System.currentTimeMillis();
        String url = "http://api.weatherapi.com/v1/current.json?key=81f50fcfe0294f23971235451240411&q=41.613,-70.9705";

        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setApiProviderName("WeatherAPI");
        try {
            String responseString = restTemplate.getForObject(url, String.class);
            JsonNode response = objectMapper.readTree(responseString);
            if (response != null && response.get("current") != null &&
                    response.get("current").get("temp_c") != null) {
                weatherResponse.setTemperature(response.get("current").get("temp_c").asDouble());
                long endTime = System.currentTimeMillis();
                weatherResponse.setStartTime(startTime);
                weatherResponse.setEndTime(endTime);
                weatherResponse.setResponseTime(endTime - startTime);
            } else {
                weatherResponse.setError(true);
                weatherResponse.setErrorMessage("Could not fetch weather data");
            }
        } catch (Exception e) {
            weatherResponse.setError(true);
            weatherResponse.setErrorMessage("Failed to fetch weather data");
        }
        return weatherResponse;
    }

    public WeatherResponse fetchWeatherFromOpenWeatherSync() {
        long startTime = System.currentTimeMillis();
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=41.613&lon=-70.9705&appid=f6d854b3d8928bd439aa1e0e18b9a3a4&units=metric";

        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setApiProviderName("OpenWeatherMap");
        try {
            String responseString = restTemplate.getForObject(url, String.class);
            JsonNode response = objectMapper.readTree(responseString);
            if (response != null && response.get("main") != null && response.get("main").get("temp") != null) {
                weatherResponse.setTemperature(response.get("main").get("temp").asDouble());
                long endTime = System.currentTimeMillis();
                weatherResponse.setStartTime(startTime);
                weatherResponse.setEndTime(endTime);
                weatherResponse.setResponseTime(endTime - startTime);
            } else {
                weatherResponse.setError(true);
                weatherResponse.setErrorMessage("Could not fetch weather data");
            }
        } catch (Exception e) {
            weatherResponse.setError(true);
            weatherResponse.setErrorMessage("Failed to fetch weather data");
        }
        return weatherResponse;
    }


    public List<WeatherResponse> getWeatherDataSync() {
        List<WeatherResponse> weatherResponses = new ArrayList<>();

        // Call each API one after the other
        weatherResponses.add(fetchWeatherFromWeatherBitSync());
        weatherResponses.add(fetchWeatherFromWeatherAPISync());
        weatherResponses.add(fetchWeatherFromOpenWeatherSync());

        return weatherResponses;
    }
}
