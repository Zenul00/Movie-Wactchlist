package com.zenul.Watchlist.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zenul.Watchlist.entity.Movie;
import com.zenul.Watchlist.repository.MovieRepo;

@Service
public class DatabaseService {
	
	@Autowired
	MovieRepo movieRepo;
	
	@Autowired
	RatingService ratingService;
	
	public void create(Movie movie) {
		
		String rating = ratingService.getMovieRating(movie.getTitle()); 
		if(rating != null) {
			movie.setRating(Float.parseFloat(rating));
		}
		movieRepo.save(movie);
	}

	public List<Movie>getAllMovies() {
		// TODO Auto-generated method stub
		return movieRepo.findAll();
	}

	public Movie getMoviebyId(Integer id) {
		// TODO Auto-generated method stub
		return movieRepo.findById(id).get();
	}

	public void update(Movie movie, Integer id) {
		// TODO Auto-generated method stub
		Movie toBeUpdate = getMoviebyId(id);
		toBeUpdate.setTitle(movie.getTitle());
		toBeUpdate.setRating(movie.getRating());
		toBeUpdate.setPriority(movie.getPriority());
		toBeUpdate.setComment(movie.getComment());
		movieRepo.save(toBeUpdate);
	}
	
	public void delete(Integer id) {
		movieRepo.deleteById(id);
	}
	
	
	
}
