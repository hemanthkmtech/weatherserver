package com.weather.weatherserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.weather.weatherserver.model.WeatherData;

@CrossOrigin
@RestController
public class MainController {
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    WeatherData weatherData;

    @PostMapping("/")
    public String demo(@RequestParam String lat, @RequestParam String lan) {
        weatherData.getWeatherData(lan, lat);
        return weatherData.getWeatherData(lan, lat).toString();
    }
}
