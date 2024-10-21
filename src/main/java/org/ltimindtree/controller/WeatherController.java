package org.ltimindtree.controller;

import java.util.List;

import org.ltimindtree.model.WeatherResponse;
import org.ltimindtree.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
public class WeatherController {

	@Autowired
	private WeatherService weatherService;

	@GetMapping("/currentWeather/{cityName}")
	public ResponseEntity<?> getCurrentWeather(@PathVariable String cityName) {
		try {
			WeatherResponse weatherResponse = weatherService.getWeatherForCity(cityName);
			return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Data saving Failed", HttpStatus.NOT_FOUND);
		}
	}

//	@PostMapping("/currentWeather/save")
//	public ResponseEntity<?> getWeather(@RequestBody String city) {
//		try {
//			WeatherResponse weatherResponse = weatherService.getWeatherForCity(city);
//			return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>("Data saving Failed", HttpStatus.NOT_FOUND);
//		}
//	}

	@GetMapping("/currentWeather/all")
	public ResponseEntity<?> allWeatherData() {
		List<WeatherResponse> weatherResponseList = weatherService.allWeatherData();
		if (!weatherResponseList.isEmpty()) {
			return new ResponseEntity<>(weatherResponseList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No Data found", HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/currentWeather/{cityName}")
	public ResponseEntity<?> deleteWeatherData(@PathVariable("cityName") String cityName) {
		try {
			weatherService.deleteWeatherData(cityName);
			return new ResponseEntity<>("Weather Data Deleted for " + cityName, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(cityName + " Weather Data Not Found", HttpStatus.NOT_FOUND);
		}
	}
}
