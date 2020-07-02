package com.example.weatherhistory.retriever;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class WeatherDataRetriever {
    private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    private static final Logger log = LoggerFactory.getLogger(WeatherDataRetriever.class);


    public List<String> retrieveData(ZonedDateTime lastUpdate) {
        if (lastUpdate == null) {
            log.info("Database is empty, retrieving full data.");
            return retrieveTemperatureHistory(getCurrentTime().minusDays(30));
        } else if (getCurrentTime().minusHours(1).isEqual(lastUpdate)) {
            log.info("Last update was an hour ago, retrieving latest data only.");
            return Collections.singletonList(retrieveCurrentTemperature());
        } else {
            log.info("Last update at {}. Updating.", lastUpdate);
            return retrieveTemperatureHistory(lastUpdate);
        }
    }


    private List<String> retrieveTemperatureHistory(ZonedDateTime retrieveUntil) {
        ZonedDateTime startDate = getCurrentTime();
        ZonedDateTime endDate = startDate.minusDays(1);
        List<String> temperatureHistory = new ArrayList<>();
        while (startDate.isAfter(retrieveUntil)) {
            log.info("Performing request for historical temperature data from {} to {}.", startDate, endDate);
            String response = performRequest(buildRequestHistoricalTemp(startDate, endDate));
            if (response != null) {
                log.info("Sucess");
                temperatureHistory.add(response);
            }
            startDate = startDate.minusDays(1);
            endDate = endDate.minusDays(1);
        }
        return temperatureHistory;
    }

    private String retrieveCurrentTemperature() {
        return performRequest(buildRequestCurrentTemp());
    }

    private String performRequest(HttpRequest httpRequest) {
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                log.warn("Request was declined: {}",response.statusCode());
                log.warn(response.body());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            log.info("Request failed: " + e.getMessage());
            return null;
        }
    }

    private HttpRequest buildRequestHistoricalTemp(ZonedDateTime startDate, ZonedDateTime endDate) {
        String requestUrl = "https://api.climacell.co/v3/weather/historical/station?lat=54.6872&lon=25.2797&start_time=" +
                endDate.toLocalDate().toString() + "T" +
                String.format("%02d", endDate.getHour()) + "%3A" +
                String.format("%02d", endDate.getMinute()) + "%3A" +
                String.format("%02d", endDate.getSecond()) + "Z&end_time=" +
                startDate.toLocalDate().toString() + "T" +
                String.format("%02d", startDate.getHour()) + "%3A" +
                String.format("%02d", startDate.getMinute()) + "%3A" +
                String.format("%02d", startDate.getSecond()) + "Z&fields%5B%5D=temp";

        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(requestUrl))
                .setHeader("content-type", "application/json")
                .setHeader("apikey", "lkbRCntE4UHzCv6Q9gj8d4ufdkEPPxzZ")
                .build();
    }

    private HttpRequest buildRequestCurrentTemp() {
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.climacell.co/v3/weather/realtime?lat=54.6872&lon=25.2797&location_id=&fields%5B%5D=temp"))
                .setHeader("content-type", "application/json")
                .setHeader("apikey", "lkbRCntE4UHzCv6Q9gj8d4ufdkEPPxzZ")
                .build();
    }

    private ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now(ZoneOffset.UTC);
    }
}
