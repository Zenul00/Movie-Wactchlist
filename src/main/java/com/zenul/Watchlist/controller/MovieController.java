package com.zenul.Watchlist.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.zenul.Watchlist.entity.Movie;
import com.zenul.Watchlist.service.DatabaseService;

@RestController
public class MovieController {
	
	@Autowired
	DatabaseService databaseService;
	
	@GetMapping("/watchlistItemForm")
	public ModelAndView showWatchListitemForm(@RequestParam(required = false)Integer id) {
		
		String viewName = "watchlistItemForm";
		
		Map<String, Object> model = new HashMap<>();
		if(id == null) {
			model.put("watchlistItem", new Movie());			
		} else {
			model.put("watchlistItem", databaseService.getMoviebyId(id));
		}
//		
//		Movie dummy = new Movie();
//		dummy.setTitle("Don");
//		dummy.setRating(0);
//		dummy.setPriority("low");
//		dummy.setComment("Not int");
//		model.put("watchlistItem", dummy);
		
//		model.put("watchlistItem", new Movie());
		return new ModelAndView(viewName, model);
	}
	
	@PostMapping("/watchlistItemForm")
	public ModelAndView submitWatchListForm(@Valid @ModelAttribute("watchlistItem") Movie movie, BindingResult bindingResult) {
//		if errors are there, redisplay the form and user enter again
		if(bindingResult.hasErrors()) {
			return new ModelAndView("watchlistItemForm");   //to display form again
		}
		/*
		 * if(id== null){ create new object}
		 * else{update}
		 */		
		Integer id = movie.getId();
		if(id == null) {
			databaseService.create(movie);						
		} else {
			databaseService.update(movie, id);
		}
		RedirectView rd = new RedirectView();
		rd.setUrl("/watchlists");
		
		return new ModelAndView(rd);
	}
	
	@GetMapping("/watchlists")
	public ModelAndView getWatchlist(@RequestParam(required = false) Integer id) {
		
		String viewName = "watchlists";
		
		Map<String, Object> model = new HashMap<>();
		if(id==null ) {
			List<Movie> movieList = databaseService.getAllMovies();
			model.put("watchlistrows", movieList);
			model.put("noofmovies", movieList.size());
			return new ModelAndView(viewName, model);			
		} else {
			databaseService.delete(id);
			RedirectView rd = new RedirectView();
			rd.setUrl("/watchlists");
			return new ModelAndView(rd);			
		}
	}	
	
//	@DeleteMapping("/watchlists")
//	public ModelAndView deleteItem(@RequestParam Integer id) {
//		databaseService.delete(id);
//		RedirectView rd = new RedirectView();
//		rd.setUrl("/watchlistItemForm");
//		System.out.println("controller");
//		return new ModelAndView(rd);
//	}
}
