/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: LifeCycle.java 
 * @Prject: microservice-infrastructure
 * @Package: edu.uestc.msstudio.cloud.recording 
 * @author: MT   
 * @date: 2017年3月10日 下午4:58:48 
 * @version: V1.0   
 */
package edu.uestc.msstudio.cloud.recording;

/** 
 * @ClassName: LifeCycle 
 * @Description: 
 * A annotation which can provide life cycle modeling,
 * transfer most of the method into status cycle.
 * @author: MT
 * @date: 2017年3月10日 下午4:58:48  
 */
public @interface LifeCycle {
	public String source();// which state did the action come from
	
	public String target();// which state did the action transfer to
	
	public String action();// the description of the action 
}