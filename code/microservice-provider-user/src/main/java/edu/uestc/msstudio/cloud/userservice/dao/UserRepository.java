package edu.uestc.msstudio.cloud.userservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.uestc.msstudio.cloud.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}