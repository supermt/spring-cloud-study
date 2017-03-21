/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: VectorList.java 
 * @Prject: microservice-infrastructure
 * @Package: edu.uestc.msstudio.cloud.recording 
 * @Description: TODO
 * @author: MT   
 * @date: 2017年3月21日 上午11:18:24 
 * @version: V1.0   
 */
package edu.uestc.msstudio.cloud.recording;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uestc.msstudio.cloud.userservice.entity.User;

/** 
 * @ClassName: VectorList 
 * @Description: TODO
 * @author: MT
 * @date: 2017年3月21日 上午11:18:24  
 */
public class VectorList {
	
	private static final Map<LifeCycleActions,VectorDesc> vectors;
	
	private static final Map<Class<?>,ObjectType> typeMap;
	
	static {
		vectors = new HashMap<>();
		typeMap = new HashMap<>();
		vectors.put(LifeCycleActions.queryByUser, new VectorDesc(LifeCycleStatus.Saved,LifeCycleStatus.Saved,User.class,User.class));
		vectors.put(LifeCycleActions.queryUserById, new VectorDesc(LifeCycleStatus.Saved,LifeCycleStatus.Saved,Long.class,User.class));
		vectors.put(LifeCycleActions.splitUser, new VectorDesc(LifeCycleStatus.Saved,LifeCycleStatus.Saved,User.class,List.class));
		vectors.put(LifeCycleActions.updateUser, new VectorDesc(LifeCycleStatus.Saved,LifeCycleStatus.Saved,User.class,User.class));
		vectors.put(LifeCycleActions.mergeUser, new VectorDesc(LifeCycleStatus.Saved,LifeCycleStatus.Saved,List.class,User.class));
		vectors.put(LifeCycleActions.createUser, new VectorDesc(LifeCycleStatus.InAction,LifeCycleStatus.Saved,List.class,User.class));
		vectors.put(LifeCycleActions.transferUser, new VectorDesc(LifeCycleStatus.Saved,LifeCycleStatus.Saved,List.class,User.class));
		
		typeMap.put(User.class,ObjectType.User);
	}
	
	public static VectorDesc getVectorDescription(LifeCycleActions targetAction){
		return vectors.get(targetAction);
	}
	
	public static ObjectType transTypeToClass(Class<?> classType){
		return typeMap.get(classType);
	}
}
