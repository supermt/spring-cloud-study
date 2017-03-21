package edu.uestc.msstudio.cloud.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.uestc.msstudio.cloud.movieservice.dao.MovieRepository;
import edu.uestc.msstudio.cloud.userservice.entity.User;

@RestController
public class MovieController {
	
	@Autowired
	private MovieRepository movieDao;
	
	@GetMapping("users/{id}")
	public User searchUser(@PathVariable String id){
		return movieDao.searchUser(id);
	}
}
