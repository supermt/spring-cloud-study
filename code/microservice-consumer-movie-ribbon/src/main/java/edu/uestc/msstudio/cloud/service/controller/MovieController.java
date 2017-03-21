package edu.uestc.msstudio.cloud.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.uestc.msstudio.cloud.movieservice.dao.MovieRepository;
import edu.uestc.msstudio.cloud.recording.LifeCycle;
import edu.uestc.msstudio.cloud.recording.LifeCycleActions;
import edu.uestc.msstudio.cloud.userservice.entity.User;

@RestController
public class MovieController {
	
	@Autowired
	private MovieRepository movieDao;
	
	@GetMapping("users/{id}")
	@LifeCycle(action = LifeCycleActions.query, operationType = User.class)
	public User searchUser(@PathVariable String id){
		return movieDao.searchUser(id);
	}
}
