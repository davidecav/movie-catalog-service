package com.davide.moviecatalogservice.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.davide.moviecatalogservice.models.CatalogItem;
import com.davide.moviecatalogservice.models.Movie;
import com.davide.moviecatalogservice.models.Rating;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Service
public class MovieInfo {

	
	//utenteService è preso come nome dall'application.yml
	//da userService viene definito il bulkhead
	public final String UTENTE = "utenteService"; 
	
	@Autowired
	private RestTemplate restTemplate;
	
	//@Retry(name = UTENTE, fallbackMethod = "getFallbackCatalogItem")
	@CircuitBreaker(name = UTENTE, fallbackMethod = "getFallbackCatalogItem")
	public CatalogItem getCatalogItem(Rating rating) {
		
		
		
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getName(),"desc",rating.getRating());	
	}
	
	
	public CatalogItem getFallbackCatalogItem(Rating rating,Exception e) {
		return new CatalogItem("movie not found","",rating.getRating());
	}
	

	
}
