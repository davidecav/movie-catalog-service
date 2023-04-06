package com.davide.moviecatalogservice.models;

import java.util.Collection;
import java.util.List;

public class UserRating {

	
	private List<Rating> userRating;

	public List<Rating> getUserRating() {
		return userRating;
	}

	public void setUserRating(List<Rating> userRating) {
		this.userRating = userRating;
	}

	
	
	
	public void setUserId(String userId) {
		// TODO Auto-generated method stub
		
		
	}

	public void setRating(List<Rating> userRating) {
		this.userRating = userRating;
		
	}

	public Collection<CatalogItem> getRating() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
