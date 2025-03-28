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
        return weatherRepository.save(weather);
    }

    @Override
    public List<Weather> getAllWeatherByOptionalParams(String date, List<String> cities, String sort){
        List<Weather> weatherList = weatherRepository.findAll();
        weatherList = filterByDate(weatherList, date);
        weatherList = filterByCities(weatherList, cities);
        sortWeatherList(weatherList, sort);

        return weatherList;
    }

    private void sortWeatherList(List<Weather> weatherList, String sort) {
        if ("date".equals(sort)) {
            log.info("Sorting weather records in ascending order");
            weatherList.sort(Comparator.comparing(Weather::getDate)
                    .thenComparing(Weather::getId));
        } else if ("-date".equals(sort)) {
            log.info("Sorting weather records in descending order");
            weatherList.sort(Comparator.comparing(Weather::getDate)
                    .reversed().thenComparing(Weather::getId));
        }
    }

    private List<Weather> filterByCities(List<Weather> weatherList, List<String> cities) {
        if (cities == null || cities.isEmpty()) {
            return weatherList;
        }

        log.info("Fetching weather records for cities: {}", cities);
        weatherList = weatherRepository.findByCityIgnoreCaseIn(cities);
        log.info("Fetched {} weather records for cities: {}", weatherList.size(), cities);

        return weatherList;
    }

    private List<Weather> filterByDate(List<Weather> weatherList, String date) {
        if (date == null) {
            return weatherList;
        }
        log.info("Fetching weather records for date: {}", date);
        Date parsedDate = parseDate(date);

        if (parsedDate != null) {
            weatherList = weatherRepository.findByDate(parsedDate);
            log.info("Fetched {} weather records for date: {}", weatherList.size(), date);
        }

        return weatherList;
    }
    private Date parseDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            log.error("Error parsing date: {} - Exception: {}", date, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Optional<Weather> getWeatherById(int id) {
        log.info("Fetching weather records by Id: {}", id);
        return weatherRepository.findById(id);
    }

}
