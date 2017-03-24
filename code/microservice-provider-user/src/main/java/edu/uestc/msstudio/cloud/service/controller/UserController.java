package edu.uestc.msstudio.cloud.service.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uestc.msstudio.cloud.recording.LifeCycle;
import edu.uestc.msstudio.cloud.recording.LifeCycleActions;
import edu.uestc.msstudio.cloud.userservice.dao.UserRepository;
import edu.uestc.msstudio.cloud.userservice.entity.User;

@RestController
public class UserController {
  @Autowired
  private UserRepository userRepository;
  
  @GetMapping("/{id}")
  @LifeCycle(action = LifeCycleActions.queryUserById)
  public User findById(@PathVariable Long id) {
    return  this.userRepository.findOne(id);
  }
  
  @GetMapping("/createTest")
  @LifeCycle(action = LifeCycleActions.createUser)
  public User createUserSample(){
	  User result = new User();
	  return this.userRepository.save(result);
  }
  
  @PostMapping("/createOne")
  @LifeCycle(action = LifeCycleActions.createUser)
  public User createOne(@RequestBody User user){
	  return this.userRepository.save(user);
  }
  
  @PostMapping("/createList")
  @LifeCycle(action = LifeCycleActions.createUser)
  public List<User> createList(@RequestBody List<User> users){
	  return this.userRepository.save(users);
  }
  
  @GetMapping("/count")
  public Long countUsers(){
	  return userRepository.myCount();
  }
  
  @PutMapping("/transfer")
  @LifeCycle(action = LifeCycleActions.transferUser)
  public User UserGrowOlder(@RequestBody User sourceUser){
	  sourceUser.setAge(sourceUser.getAge()+1);
	  userRepository.save(sourceUser);
	  return sourceUser;
  }
  
  @PutMapping("/split")
  @LifeCycle(action = LifeCycleActions.splitUser)
  public List<User> randomSplitUser(@RequestBody User sourceUser){
	  int randomAge = new Random().nextInt(sourceUser.getAge());
	  List<User> result = new ArrayList<>();
	  
	  User temp = new User();
	  temp.setAge(randomAge);
	  temp.setUsername(sourceUser.getUsername());
	  
	 
	  result.add(temp);
	  
	  temp = new User();
	  temp.setAge(sourceUser.getAge()-randomAge);
	  result.add(temp);
	  
	  return userRepository.save(result);
	  
  }
  
  @PutMapping("/merge")
  @LifeCycle(action = LifeCycleActions.mergeUser)
  public User mergeUsers(@RequestBody List<User> users){
	  
	  User result = new User();
	  
	  int userage= 0;
	  String username = "";
	  
	  for(User u:users){
		  userage+=u.getAge();
		  username+=u.getUsername();
	  }
	  
	  result.setAge(userage);
	  result.setUsername(username);
	  
	  return userRepository.save(result);
	  
  }
  
}