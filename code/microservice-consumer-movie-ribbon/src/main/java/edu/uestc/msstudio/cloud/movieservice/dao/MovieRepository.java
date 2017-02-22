package edu.uestc.msstudio.cloud.movieservice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.uestc.msstudio.cloud.userservice.entity.User;

@Service
public class MovieRepository {
	@Autowired
	RestTemplate restTemplate;
	
	public User searchUser(String id){
		return restTemplate.getForObject("http://microservice-provider-user/"+id, User.class);
	}
}
