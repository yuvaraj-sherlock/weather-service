package com.klm.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.klm.weather.repository.InMemoryWeatherRepository;
import com.klm.weather.repository.MySQLWeatherRepository;
import com.klm.weather.repository.WeatherRepository;

@SpringBootApplication
@EnableJpaRepositories
public class WeatherApplication {
	
	@Value("${weather.repository}")
    private String repositoryType;

	@Bean
	WeatherRepository weatherRepository(InMemoryWeatherRepository inMemoryRepo, MySQLWeatherRepository mySQLRepo) {
		return "mySQLWeatherRepository".equals(repositoryType) ? mySQLRepo : inMemoryRepo;
	}
	
    public static void main(String[] args) {
        SpringApplication.run(WeatherApplication.class, args);
    }

}
