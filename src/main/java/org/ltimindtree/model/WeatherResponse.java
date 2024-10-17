package org.ltimindtree.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.ltimindtree.configuration.WeatherArrayConverter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;

@Data
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {
    private String cityName;
    private Coord coord;
    private Weather[] weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private long dt;
    private Sys sys;
    private int timezone;
    private long id;
    private String name;
    private int cod;
    private long timestamp;

    @DynamoDbPartitionKey
    public String getCityName() {
        return cityName;
    }

    @DynamoDbConvertedBy(WeatherArrayConverter.class)
    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    @Data
    @DynamoDbBean
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Coord {
        private double lon;
        private double lat;
    }

    @Data
    @DynamoDbBean
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;
    }

    @Data
    @DynamoDbBean
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Main {
        private double temp;
        @JsonProperty("feels_like")
        private double feelsLike;
        @JsonProperty("temp_min")
        private double tempMin;
        @JsonProperty("temp_max")
        private double tempMax;
        private int pressure;
        private int humidity;
        @JsonProperty("sea_level")
        private int seaLevel;
        @JsonProperty("grnd_level")
        private int grndLevel;
    }

    @Data
    @DynamoDbBean
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Wind {
        private double speed;
        private int deg;
        private double gust;
    }

    @Data
    @DynamoDbBean
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Clouds {
        private int all;
    }

    @Data
    @DynamoDbBean
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sys {
        private int type;
        private long id;
        private String country;
        private long sunrise;
        private long sunset;
    }
}
