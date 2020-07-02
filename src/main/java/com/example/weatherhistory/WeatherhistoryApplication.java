package com.example.weatherhistory;

import com.example.weatherhistory.service.WeatherServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class WeatherhistoryApplication {

	@Autowired
	WeatherServiceImp weatherServiceImp;

	public static void main(String[] args) {
		SpringApplication.run(WeatherhistoryApplication.class, args);
	}

	private static final Logger log = LoggerFactory.getLogger(WeatherhistoryApplication.class);

	@Scheduled(fixedRate = 60*60*1000)
	public void scheduleRetrieval() {
		log.info("Retrieving weather data.");
		weatherServiceImp.updateData();
	}

}
