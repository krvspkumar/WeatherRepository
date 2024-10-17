package org.ltimindtree.repository;

import org.ltimindtree.model.WeatherResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository {
    WeatherResponse getWeatherForCity(String cityName);
}