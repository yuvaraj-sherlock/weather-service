package com.klm.weather.service;

import com.klm.weather.model.Weather;
import com.klm.weather.repository.WeatherRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
public class WeatherServiceImpl implements WeatherService{

	@Autowired
	private WeatherRepository weatherRepository;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Weather saveWeather(Weather weather) {
    	log.info("Saving Weather information: {}", weather);
        Weather savedWeather = weatherRepository.save(weather);
        return savedWeather;
    }

    @Override
    public List<Weather> getAllWeatherByOptionalParams(String date, List<String> cities, String sort){
        List<Weather> weatherList = weatherRepository.findAll();
        
        if (date != null) {
        	log.info("Fetching weather records for date: {}", date);
        	Date parsedDate = null;
			try {
				parsedDate = dateFormat.parse(date);
			} catch (ParseException e) {
				log.error("Error parsing date: {} - Exception: {}", date, e.getMessage(), e);
			}
            weatherList = weatherRepository.findByDate(parsedDate);
            log.info("Fetched {} weather records for date: {}", weatherList.size(), date);
        }

        if (cities != null && !cities.isEmpty()) {
        	log.info("Fetching weather records for cities: {}", cities);
            weatherList = weatherRepository.findByCityIgnoreCaseIn(cities);
            log.info("Fetched {} weather records for cities: {}", weatherList.size(), cities);
        }

        if ("date".equals(sort)) {
        	log.info("Fetching weather records in sorder order");
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
        	log.info("Fetching weather records in reverse order");
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
    	log.info("Fetching weather records by Id: {} "+id);
        return weatherRepository.findById(id);
    }

}
