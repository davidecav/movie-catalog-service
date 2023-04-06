package com.davide.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient;

import com.davide.moviecatalogservice.models.CatalogItem;
import com.davide.moviecatalogservice.models.Movie;
import com.davide.moviecatalogservice.models.Rating;
import com.davide.moviecatalogservice.models.UserRating;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.davide.moviecatalogservice.services.MovieInfo;
import com.davide.moviecatalogservice.services.UserRatingInfo;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;




@RequestMapping("/catalog")
@RestController
public class MovieCatalogResource {

	//oggetto instanziato automaticamente con eureka client
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	
	//è meglio chiamare dei service autowired separando in ogni caso le chiamate di fallback dalla stessa classe
	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;
	

	
	
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId")String userId){
		
		
		UserRating userRating =userRatingInfo.getUserRating(userId);
		
	
		return userRating.getUserRating().stream().map(rating->{
			
			return movieInfo.getCatalogItem(rating);

		})
				.collect(Collectors.toList());
		
		
		/*
			//secondo metodo con WebClient
		 
		return ratings.stream().map(rating->{
		Movie movie = webClientBuilder.build()								//usa il builder pattern e ci da il client
			.get() 															//metodo get
			.uri("http://localhost:8082/movies/" + rating.getMovieId())		//accesso dall'altro microservice
			.retrieve()														//fai la fetch
			.bodyToMono(Movie.class)										//qualsiasi body prendi converti nella classe 
			.block();														//blocca l'esecuzione 
		
		return new CatalogItem(movie.getName(),"desc",rating.getRating());

		}).collect(Collectors.toList());
		
	*/
		
	/*
		//primo metodo con restTemplate
		
		return ratings.stream().map(rating->{
			Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
			return new CatalogItem(movie.getName(),"desc",rating.getRating());

		}).collect(Collectors.toList());
		
	*/
		

	}

	
	
	/*
	// su resilience4j bisogna aggiungere sempre la exception al metodo
	
	public List<CatalogItem> getFallbackCatalog(Exception e) {
		System.out.println(e);
		return Arrays.asList(
				new CatalogItem("no item","no description",0)
				);
	}
	*/
	
	
	
	
	
	
	
	
	
	
	
}
