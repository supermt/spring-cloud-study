package edu.uestc.msstudio.cloud.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.uestc.msstudio.cloud.userservice.dao.UserRepository;
import edu.uestc.msstudio.cloud.userservice.entity.User;

@RestController
public class UserController {
  @Autowired
  private UserRepository userRepository;
  
  @GetMapping("/{id}")
  public User findById(@PathVariable Long id) {
    return  this.userRepository.findOne(id);
  }
}