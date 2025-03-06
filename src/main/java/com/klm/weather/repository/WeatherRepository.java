package com.klm.weather.repository;

import com.klm.weather.model.Weather;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class WeatherRepository {

	final Map<Integer, Weather> weatherData = new ConcurrentHashMap<Integer, Weather>();
	final AtomicInteger idGenerator = new AtomicInteger(1);

    public Weather save(Weather weather) {
        weather.setId(idGenerator.getAndIncrement());
        weatherData.put(weather.getId(), weather);
        return weather;
    }

    public List<Weather> findAll() {
        return new ArrayList<Weather>((weatherData.values()));
    }

    public Optional<Weather> findById(int id) {
        return Optional.ofNullable(weatherData.get(id));
    }

    public List<Weather> findByDate(String date) {
        return weatherData.values().stream()
                .filter(w -> w.getDate().toString().equals(date))
                .sorted(Comparator.comparingInt(Weather::getId))
                .collect(Collectors.toList());
    }

    public List<Weather> findByCities(List<String> cities) {
        return weatherData.values().stream()
                .filter(w -> cities.contains(w.getCity().toLowerCase()))
                .sorted(Comparator.comparingInt(Weather::getId))
                .collect(Collectors.toList());
    }

	public void deleteAll() {
		 weatherData.clear();		
	}

}
