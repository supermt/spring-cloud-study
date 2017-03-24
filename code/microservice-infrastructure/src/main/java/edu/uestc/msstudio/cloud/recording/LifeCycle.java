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

@Retention(RetentionPolicy.RUNTIME)
public @interface LifeCycle {
	public LifeCycleActions action();// the description of the action 
}