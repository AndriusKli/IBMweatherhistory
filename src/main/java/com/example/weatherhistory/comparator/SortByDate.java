package com.example.weatherhistory.comparator;

import com.example.weatherhistory.entity.Weather;

import java.util.Comparator;

public class SortByDate implements Comparator<Weather> {
    @Override
    public int compare(Weather o1, Weather o2) {
        return o1.getDateTime().compareTo(o2.getDateTime());
    }
}
