package com.davide.moviecatalogservice.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.davide.moviecatalogservice.models.Rating;
import com.davide.moviecatalogservice.models.UserRating;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Service
public class UserRatingInfo {

	
	//utenteService è preso come nome dall'application.yml
	//da userService viene definito il bulkhead
	public final String UTENTE = "utenteService";
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@CircuitBreaker(name = UTENTE, fallbackMethod = "getFallbackUserRating")
	public UserRating getUserRating(@PathVariable("userId")String userId) {
		
		return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/foo" + userId, UserRating.class);
	}
	
	public UserRating getFallbackUserRating(@PathVariable("userId") String userId,Exception e) {
		UserRating userRating=  new UserRating();
		
		userRating.setUserId(userId);
		userRating.setRating(Arrays.asList(
				new Rating("0",0)
				));
		
		return userRating;
	}
}
