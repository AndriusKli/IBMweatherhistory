package com.example.weatherhistory.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double temperature;

    private ZonedDateTime dateTime;

    public Weather(Double temperature, ZonedDateTime dateTime) {
        this.temperature = temperature;
        this.dateTime = dateTime;
    }


}
