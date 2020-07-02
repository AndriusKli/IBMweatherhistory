package com.example.weatherhistory.repository;

import com.example.weatherhistory.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Weather findTopByOrderByIdDesc();
}
