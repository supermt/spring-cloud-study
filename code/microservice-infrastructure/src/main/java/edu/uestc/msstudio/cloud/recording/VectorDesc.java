/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: VectorDesc.java 
 * @Prject: microservice-infrastructure
 * @Package: edu.uestc.msstudio.cloud.recording 
 * @Description: TODO
 * @author: MT   
 * @date: 2017年3月21日 上午11:25:27 
 * @version: V1.0   
 */
package edu.uestc.msstudio.cloud.recording;

/** 
 * @ClassName: VectorDesc 
 * @Description: TODO
 * @author: MT
 * @date: 2017年3月21日 上午11:25:27  
 */
public class VectorDesc {
	
	private LifeCycleStatus head;
	
	private LifeCycleStatus tail;
	
	private Class<?> sourceType;
	
	private Class<?> targetType;

	public VectorDesc(LifeCycleStatus head,LifeCycleStatus tail,Class<?> sourceType,Class<?> targetType){
		this.head = head;
		this.tail = tail;
		this.sourceType = sourceType;
		this.targetType = targetType;
	}
	
	public LifeCycleStatus getHead() {
		return head;
	}

	public void setHead(LifeCycleStatus head) {
		this.head = head;
	}

	public LifeCycleStatus getTail() {
		return tail;
	}

	public void setTail(LifeCycleStatus tail) {
		this.tail = tail;
	}

	public Class<?> getSourceType() {
		return sourceType;
	}

	public void setSourceType(Class<?> sourceType) {
		this.sourceType = sourceType;
	}

	public Class<?> getTargetType() {
		return targetType;
	}

	public void setTargetType(Class<?> targetType) {
		this.targetType = targetType;
	}
	
	
}
