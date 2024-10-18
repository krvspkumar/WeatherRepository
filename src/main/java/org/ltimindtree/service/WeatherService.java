package org.ltimindtree.service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.ltimindtree.configuration.WeatherArrayConverter;
import org.ltimindtree.model.WeatherResponse;
import org.ltimindtree.model.WeatherResponse.Weather;
import org.ltimindtree.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Service
public class WeatherService implements WeatherRepository {

	private final OkHttpClient client;
	private final ObjectMapper objectMapper;
	private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
	private final DynamoDbTable<WeatherResponse> weatherTable;

	private final String rapidApiKey = "70e9807224msh051386d6df7def9p13b6c1jsnbc44159947de";
	private final String rapidApiHost = "open-weather13.p.rapidapi.com";
	private final String tableName = "WeatherData";

	public WeatherService(OkHttpClient client, ObjectMapper objectMapper, DynamoDbClient dynamoDbClient) {
		this.client = client;
		this.objectMapper = objectMapper;
		this.dynamoDbEnhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
		this.weatherTable = dynamoDbEnhancedClient.table(tableName,
				TableSchema.builder(WeatherResponse.class).newItemSupplier(WeatherResponse::new)
						.addAttribute(String.class, a -> a.name("cityName").getter(WeatherResponse::getCityName)
								.setter(WeatherResponse::setCityName).tags(StaticAttributeTags.primaryPartitionKey()))
						.addAttribute(Weather[].class, a -> a.name("weather").getter(WeatherResponse::getWeather)
								.setter(WeatherResponse::setWeather).attributeConverter(new WeatherArrayConverter()))
						.build());
	}

	@Override
	public WeatherResponse getWeatherForCity(String cityName) {
		// Check DynamoDB cache first
		WeatherResponse cachedResponse = weatherTable.getItem(Key.builder().partitionValue(cityName).build());
		if (cachedResponse != null && !isCacheExpired(cachedResponse)) {
			return cachedResponse;
		}

		// If not in cache or expired, call the API
		WeatherResponse weatherResponse = callWeatherApi(cityName);
		weatherResponse.setCityName(cityName);
		weatherResponse.setTimestamp(Instant.now().getEpochSecond());

		// Cache the result in DynamoDB
		weatherTable.putItem(weatherResponse);

		return weatherResponse;
	}

	private boolean isCacheExpired(WeatherResponse response) {
		long currentTime = Instant.now().getEpochSecond();
		
		return (currentTime) > 1800; // 30 minutes
	}

	private WeatherResponse callWeatherApi(String cityName) {
		Request request = new Request.Builder().url("https://open-weather13.p.rapidapi.com/city/" + cityName + "/EN")
				.get().addHeader("x-rapidapi-key", rapidApiKey).addHeader("x-rapidapi-host", rapidApiHost).build();

		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful())
				throw new IOException("Unexpected code " + response);

			String responseBody = response.body().string();
			return objectMapper.readValue(responseBody, WeatherResponse.class);
		} catch (IOException e) {
			throw new RuntimeException("Error fetching weather data", e);
		}
	}

	public List<WeatherResponse> allWeatherData() {
		List<WeatherResponse> weatherResponseList = new ArrayList<>();
		for (WeatherResponse weatherResponse : weatherTable.scan().items()) {
			weatherResponseList.add(weatherResponse);
		}
		return weatherResponseList;
	}

	public void deleteWeatherData(String cityName) {
		weatherTable.deleteItem(Key.builder().partitionValue(cityName).build());
	}
}
