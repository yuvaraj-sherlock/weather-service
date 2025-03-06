package com.klm.weather;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.klm.weather.model.Weather;
import com.klm.weather.repository.WeatherRepository;

public class WeatherRepositoryTest {

    private WeatherRepository weatherRepository;

    @BeforeEach
    void setUp() {
        weatherRepository = new WeatherRepository() {};
        weatherRepository.deleteAll(); // Ensure it's clean before each test
    }

    @Test
    void testSave() {
        Weather weather = new Weather(1, new Date(), 36.1189f, -86.6892f, "Nashville", "Tennessee", Arrays.asList(20.0, 21.5, 23.1));
        Weather savedWeather = weatherRepository.save(weather);

        assertNotNull(savedWeather.getId());
        assertEquals("Nashville", savedWeather.getCity());
    }

    @Test
    void testFindAll() {
        Weather w1 = new Weather(1, new Date(), 36.1189f, -86.6892f, "Nashville", "Tennessee", Arrays.asList(20.0, 21.5));
        Weather w2 = new Weather(2, new Date(), 40.7128f, -74.0060f, "New York", "New York", Arrays.asList(18.0, 19.5));

        weatherRepository.save(w1);
        weatherRepository.save(w2);

        List<Weather> allWeather = weatherRepository.findAll();
        assertEquals(2, allWeather.size());
    }

    @Test
    void testFindById() {
        Weather weather = new Weather(1, new Date(), 36.1189f, -86.6892f, "Nashville", "Tennessee", Arrays.asList(20.0, 21.5));
        Weather savedWeather = weatherRepository.save(weather);

        Optional<Weather> found = weatherRepository.findById(savedWeather.getId());
        assertTrue(found.isPresent());
        assertEquals("Nashville", found.get().getCity());
    }

    @Test
    void testFindById_NotFound() {
        Optional<Weather> found = weatherRepository.findById(999);
        assertFalse(found.isPresent());
    }

    @Test
    void testFindByDate() {
        Date date = new Date();
        Weather w1 = new Weather(1, date, 36.1189f, -86.6892f, "Nashville", "Tennessee", Arrays.asList(20.0, 21.5));
        weatherRepository.save(w1);

        List<Weather> found = weatherRepository.findByDate(date.toString());
        assertEquals(1, found.size());
        assertEquals("Nashville", found.get(0).getCity());
    }

    @Test
    void testFindByCities() {
        Weather w1 = new Weather(1, new Date(), 36.1189f, -86.6892f, "Nashville", "Tennessee", Arrays.asList(20.0, 21.5));
        Weather w2 = new Weather(2, new Date(), 40.7128f, -74.0060f, "New York", "New York", Arrays.asList(18.0, 19.5));

        weatherRepository.save(w1);
        weatherRepository.save(w2);

        List<Weather> found = weatherRepository.findByCities(Arrays.asList("nashville", "new york"));
        assertEquals(2, found.size());
    }

    @Test
    void testDeleteAll() {
        weatherRepository.save(new Weather(1, new Date(), 36.1189f, -86.6892f, "Nashville", "Tennessee", Arrays.asList(20.0, 21.5)));
        weatherRepository.deleteAll();

        assertTrue(weatherRepository.findAll().isEmpty());
    }
}
