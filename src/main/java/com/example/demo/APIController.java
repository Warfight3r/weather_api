package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class APIController {

	@GetMapping("/weather")
	public String weather(@RequestParam(value = "cityName", defaultValue = "Patna") String cityName) {

		RestTemplate restTemplateKey = new RestTemplate();
		String locationString = restTemplateKey.getForObject("https://dataservice.accuweather.com/"
				+ "locations/v1/cities/"
				+ "search?q=" + cityName + "&apikey=v2GJSKjjXCzossQGextAOHIDAk81Zdkl", String.class);
		int start = locationString.indexOf("\"Key\":") + 6;
		String substr = locationString.substring(start, locationString.indexOf(',', start));
		String result = getStringBetweenTwoCharacters(substr, '"', '"');
		String cityCode = result;

		String weatherURL = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/" + cityCode
				+ "?apikey=v2GJSKjjXCzossQGextAOHIDAk81Zdkl";

		RestTemplate restTemplateCity = new RestTemplate();
		ResponseEntity<String> response = restTemplateCity.getForEntity(weatherURL, String.class);
		return String.format(response.getBody());
	}


	public static String getStringBetweenTwoCharacters(String input, char to, char from) {
		return input.substring(input.indexOf(to) + 1, input.lastIndexOf(from));
	}
}