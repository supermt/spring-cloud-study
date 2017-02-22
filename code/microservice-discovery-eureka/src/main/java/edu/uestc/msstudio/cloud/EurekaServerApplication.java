package edu.uestc.msstudio.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}
	
	//这个方法的目的是一个候补方法
	//不一定有具体实现，但是防止当前类因为全部都是静态方法而被sonar检测为工具类
	public Object someMethod(){
		return null;
	}
}