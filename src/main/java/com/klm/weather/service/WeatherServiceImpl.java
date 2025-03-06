package com.klm.weather.service;

import com.klm.weather.model.Weather;
import com.klm.weather.repository.WeatherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WeatherServiceImpl implements WeatherService{

    @Autowired
    private WeatherRepository weatherRepository;

    @Override
    public Weather saveWeather(Weather weather) {
        Weather savedWeather = weatherRepository.save(weather);
        return savedWeather;
    }

    @Override
    public List<Weather> getAllWeatherByOptionalParams(String date, List<String> cities, String sort){
        List<Weather> weatherList = weatherRepository.findAll();
        
        if (date != null) {
            weatherList = weatherRepository.findByDate(date);
        }

        if (cities != null && !cities.isEmpty()) {
            weatherList = weatherRepository.findByCities(cities);
        }

        if ("date".equals(sort)) {
            Collections.sort(weatherList, new Comparator<Weather>() {
                @Override
                public int compare(Weather w1, Weather w2) {
                    int dateComparison = w1.getDate().compareTo(w2.getDate());
                    if (dateComparison == 0) {
                        return Integer.compare(w1.getId(), w2.getId());
                    }
                    return dateComparison;
                }
            });
        } else if ("-date".equals(sort)) {
           Collections.sort(weatherList, new Comparator<Weather>() {
                @Override
                public int compare(Weather w1, Weather w2) {
                    int dateComparison = w2.getDate().compareTo(w1.getDate()); // Reverse order
                    if (dateComparison == 0) {
                        return Integer.compare(w1.getId(), w2.getId());
                    }
                    return dateComparison;
                }
            });
        }

        return weatherList;
    }

    @Override
    public Optional<Weather> getWeatherById(int id) {
        return weatherRepository.findById(id);
    }

}
