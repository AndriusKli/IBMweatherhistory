package com.example.weatherhistory.service;

import com.example.weatherhistory.entity.Weather;

import java.time.ZonedDateTime;
import java.util.List;

public interface WeatherService {
    List<Weather> getAllEntries();
    ZonedDateTime retrieveLatestEntryDateTime();
    void updateData();
}
