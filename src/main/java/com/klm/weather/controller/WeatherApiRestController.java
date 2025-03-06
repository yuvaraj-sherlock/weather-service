package com.klm.weather.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klm.weather.model.Weather;
import com.klm.weather.service.WeatherService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/weather")
public class WeatherApiRestController {

	private final WeatherService service;

    public WeatherApiRestController(WeatherService service) {
        this.service = service;
    }
    
    @PostMapping
    public ResponseEntity<Weather> createWeather(@RequestBody Weather weather) {
    	log.info("Invoked create Weather endpoint: {}", weather);
        Weather createdWeather = service.saveWeather(weather);
        return ResponseEntity.status(201).body(createdWeather);
    }

    @GetMapping
    public ResponseEntity<List<Weather>> getWeather(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String sort) {
    	log.info("Invoked get Weather endpoint date: {}, city: {}, sort: {}", date,city,sort);
        List<String> cities = city != null ? List.of(city.toLowerCase().split(",")) : null;
        List<Weather> weatherList = service.getAllWeatherByOptionalParams(date, cities, sort);

        return ResponseEntity.ok(weatherList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Weather> getWeatherById(@PathVariable int id) {
    	log.info("Invoked get Weather endpoint by ID: {}", id);
    	Optional<Weather> weather = service.getWeatherById(id);
    	if (weather.isPresent()) {
    		log.info("Weather record found for ID: {}", id);
    	    return ResponseEntity.ok(weather.get());
    	} else {
    		log.warn("No weather record found for ID: {}", id);
    	    return ResponseEntity.notFound().build();
    	}
    }

}
