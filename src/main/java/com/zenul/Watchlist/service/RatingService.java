package com.zenul.Watchlist.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class RatingService {

	String apiUrl = "https://www.omdbapi.com/?apikey=89ccce33&t=";
	
	public String getMovieRating(String title) {
		try {
			//try to fetch the rating called by omdb api
			RestTemplate template = new RestTemplate();   
			
			//api + title			
			ResponseEntity<ObjectNode> response = template.getForEntity(apiUrl + title, ObjectNode.class);
			
			ObjectNode jsonObject = response.getBody(); 
			
			return jsonObject.path("imdbRating").asText();
			
		} catch (Exception e) {
			//user entered rating will be taken
			System.out.println("Either Movie name not avaible or api is down" + e.getMessage());
			return null;
		}
	}
}
