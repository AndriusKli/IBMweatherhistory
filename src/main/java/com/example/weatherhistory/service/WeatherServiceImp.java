package com.example.weatherhistory.service;

import com.example.weatherhistory.comparator.SortByDate;
import com.example.weatherhistory.entity.Weather;
import com.example.weatherhistory.repository.WeatherRepository;
import com.example.weatherhistory.retriever.WeatherDataRetriever;
import com.example.weatherhistory.serializer.WeatherSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class WeatherServiceImp implements WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private WeatherDataRetriever weatherDataRetriever;

    @Autowired
    private WeatherSerializer weatherSerializer;

    @Override
    public List<Weather> getAllEntries() {
        return weatherRepository.findAll();
    }

    @Override
    public ZonedDateTime retrieveLatestEntryDateTime() {
        if (weatherRepository.findAll().isEmpty()) {
            return null;
        } else {
            return weatherRepository.findTopByOrderByIdDesc().getDateTime();
        }
    }

    @Override
    public void updateData() {
        ZonedDateTime latestEntry = retrieveLatestEntryDateTime();
        List<String> rawWeatherData = weatherDataRetriever.retrieveData(latestEntry);
        List<Weather> serializedWeatherData = weatherSerializer.bulkSerialize(rawWeatherData);
        serializedWeatherData.sort(new SortByDate());

        for (Weather entry : serializedWeatherData) {
            if (entry != null) {
                if (latestEntry == null) {
                    weatherRepository.save(entry);
                    latestEntry = entry.getDateTime();
                }
                if (latestEntry.plusHours(1).isBefore(entry.getDateTime()) || latestEntry.plusHours(1).isEqual(entry.getDateTime())) {
                    weatherRepository.save(entry);
                    latestEntry = entry.getDateTime();
                } else {
//                    Skip adding every weather reading if an hour hasn't passed since the last one. A simple 'skip every 2nd element'
//                    approach doesn't really work because the API readings sometimes (even if rarely) skips 30-60 minutes.
                }
            }
        }
    }
}
