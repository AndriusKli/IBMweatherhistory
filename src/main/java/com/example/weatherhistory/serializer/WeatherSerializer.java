package com.example.weatherhistory.serializer;

import com.example.weatherhistory.entity.Weather;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class WeatherSerializer {
    private static final Logger log = LoggerFactory.getLogger(WeatherSerializer.class);

    public WeatherSerializer() {
    }

    public List<Weather> bulkSerialize(List<String> processedBulkData) {
        List<String> preparedData = processBulkData(processedBulkData);
        List<Weather> serializedWeatherData = new ArrayList<>();
        preparedData.forEach(entry -> serializedWeatherData.add(serialize(entry)));
        return serializedWeatherData;
    }

    private List<String> processBulkData(List<String> rawBulkData) {
        List<String> processedRawData = new ArrayList<>();
        for (String rawDatum : rawBulkData) {
            try {
                processedRawData.addAll(splitBulkData(rawDatum));
            } catch (IOException e) {
                log.warn(e.getMessage());
            }
        }
        return processedRawData;
    }

    private List<String> splitBulkData(String jsonArray) throws IOException {
        final JsonNode jsonNode = new ObjectMapper().readTree(jsonArray);
        return StreamSupport.stream(jsonNode.spliterator(), false)
                .map(JsonNode::toString)
                .collect(Collectors.toList());
    }

    private Weather serialize(String responseData) {
        JSONObject json = new JSONObject(responseData);
        Double temperature = json.getJSONObject("temp").getDouble("value");
        ZonedDateTime dateTime = ZonedDateTime.parse(json.getJSONObject("observation_time").getString("value"));
        return new Weather(temperature, dateTime);
    }
}
