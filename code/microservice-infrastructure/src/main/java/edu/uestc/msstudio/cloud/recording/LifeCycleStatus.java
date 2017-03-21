/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: LifeCycleStatus.java 
 * @Prject: microservice-infrastructure
 * @Package: edu.uestc.msstudio.cloud.recording 
 * @Description: TODO
 * @author: MT   
 * @date: 2017年3月13日 下午4:37:41 
 * @version: V1.0   
 */
package edu.uestc.msstudio.cloud.recording;

/** 
 * @ClassName: LifeCycleStatus 
 * @Description: 
 * These status help to describe the objects' life cycle 
 * @author: MT
 * @date: 2017年3月13日 下午4:37:41  
 */
public enum LifeCycleStatus {
	New,
	PreTranscation,
	Saved,
	InAction,
	Deleted
}
