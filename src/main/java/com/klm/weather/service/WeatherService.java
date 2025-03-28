package com.klm.weather.service;

import org.springframework.stereotype.Service;
import com.klm.weather.model.Weather;

import java.util.List;
import java.util.Optional;

@Service
public interface WeatherService {

    Weather saveWeather(Weather weather);

    List<Weather> getAllWeatherByOptionalParams(String date, List<String> cities, String sort);

    Optional<Weather> getWeatherById(int id);

}