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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/** 
 * @ClassName: LifeCycle 
 * @Description: 
 * A annotation which can provide life cycle modeling,
 * transfer most of the method into status cycle.
 * This annotation need static type definitions which reference from 
 * {@link edu.uestc.msstudio.cloud.recording.LifeCycleStatus}
 * and 
 * {@link edu.uestc.msstudio.cloud.recording.LifeCycleActions}
 * @author: MT
 * @date: 2017年3月10日 下午4:58:48  
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface LifeCycle {
	public LifeCycleActions action();// the description of the action 
	
	public Class<?> operationType(); // operation object type
}