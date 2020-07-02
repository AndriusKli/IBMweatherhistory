package com.example.weatherhistory.controller;

import com.example.weatherhistory.entity.Weather;
import com.example.weatherhistory.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/weather")
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Weather> getAllData() {
        return weatherService.getAllEntries();
    }
}
