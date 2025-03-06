package com.klm.weather.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.klm.weather.model.Weather;

@Repository
public interface WeatherRepository {
	
	public Weather save(Weather weather);

	public List<Weather> findAll();

	public Optional<Weather> findById(int id);
	
	public List<Weather> findByDate(Date date);
	
	public List<Weather> findByCityIgnoreCaseIn(List<String> cities);
	
	public void deleteAll();

}
