package edu.uestc.msstudio.cloud.userservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.uestc.msstudio.cloud.recording.LifeCycle;
import edu.uestc.msstudio.cloud.recording.LifeCycleActions;
import edu.uestc.msstudio.cloud.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   
	@LifeCycle(action = LifeCycleActions.query, operationType = User.class)
	public default Long myCount(){
		return this.count();
	};
}