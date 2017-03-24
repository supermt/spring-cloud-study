/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: RecordRepository.java 
 * @Prject: microservice-infrastructure
 * @Package: edu.uestc.msstudio.cloud.recording 
 * @Description: TODO
 * @author: MT
 * @date: 2017年3月13日 上午10:34:00 
 * @version: V1.0   
 */
package edu.uestc.msstudio.cloud.recording;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/** 
 * @ClassName: RecordRepository 
 * @Description: 
 * this is Data Access Object for Entity {@link Record}
 * @author: MT
 * @date: 2017年3月13日 上午10:34:00  
 */
public interface RecordRepository extends JpaRepository<Record, Long>{
	
	@Query("select r from Record r where (source_object = ?1 or target_object = ?1) and (json_source like ?2 or json_target like ?3)")
	List<Record> searchByTypeAndId(int targettype,String idSource,String idTarget);
}
