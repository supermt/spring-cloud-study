/**   
 * Copyright © 2017 微软创新工作室. All rights reserved.
 * 
 * @Title: LogMonitor.java 
 * @Prject: microservice-monitor
 * @Package: edu.uestc.msstudio.cloud 
 * @Description: TODO
 * @author: MT   
 * @date: 2017年3月13日 下午2:35:08 
 * @version: V1.0   
 */
package edu.uestc.msstudio.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/** 
 * @ClassName: LogMonitor 
 * @Description: 
 * 
 * @author: MT
 * @date: 2017年3月13日 下午2:35:08  
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LogMonitor {
	public static void main(String[] args) {
        SpringApplication.run(LogMonitor.class, args);
    }
    
    public Object someMethod(){
    	return null;
    }
}
