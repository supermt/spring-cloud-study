/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: Record.java 
 * @Prject: microservice-infrastructure
 * @Package: edu.uestc.msstudio.cloud.recording 
 * @Description: 
 * the eneity access into database
 * @author: MT   
 * @date: 2017年3月13日 上午10:35:25 
 * @version: V1.0   
 */
package edu.uestc.msstudio.cloud.recording;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * @ClassName: Record 
 * @Description: 
 * this is a entity for operation recording
 * @author: MT
 * @date: 2017年3月13日 上午10:35:25  
 */
@Entity
@Table(name="record")
public class Record {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Date startTime;
	
	private Date endTime;
	
	private ObjectType operationObject;
	
	private String instance;// record by host:port which direct to a single server instance
	
	private String jsonSource;
	
	private String jsonTarget;
	
	private boolean ok;
	
	private LifeCycleActions action;
	
	public LifeCycleActions getAction() {
		return action;
	}

	public void setAction(LifeCycleActions action) {
		this.action = action;
	}

	public String getJsonTarget() {
		return jsonTarget;
	}

	public void setJsonTarget(String jsonTarget) {
		this.jsonTarget = jsonTarget;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public ObjectType getOperationObject() {
		return operationObject;
	}

	public void setOperationObject(ObjectType operationObject) {
		this.operationObject = operationObject;
	}

	public String getJsonSource() {
		return jsonSource;
	}

	public void setJsonSource(String jsonSource) {
		this.jsonSource = jsonSource;
	}
}
