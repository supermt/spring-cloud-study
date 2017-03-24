/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: SideCarBoot.java 
 * @Prject: microservice-sidecar-js
 * @Package: edu.uestc.msstudio.cloud 
 * @Description: TODO
 * @author: MT   
 * @date: 2017年3月24日 上午10:11:28 
 * @version: V1.0   
 */
package edu.uestc.msstudio.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

/** 
 * @ClassName: SideCarBoot 
 * @Description: TODO
 * @author: MT
 * @date: 2017年3月24日 上午10:11:28  
 */
@SpringBootApplication
@EnableSidecar
public class SideCarBoot {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SideCarBoot.class, args);
	}
}
