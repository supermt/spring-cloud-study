package edu.uestc.msstudio.cloud.recording;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LifeCycleScanner {
	
	private static final String projectRoot = "edu.uestc.msstudio";
	
	@Value("${eureka.instance.hostname}")
	private String host;
	
	@Value("${server.port}")
	private String port;
	
	private static final Logger logger = LoggerFactory.getLogger(LifeCycleScanner.class);
	
	@Pointcut("execution (* "
			+ projectRoot +"..*(..)"
			+ ") and @annotation(edu.uestc.msstudio.cloud.recording.LifeCycle)"
			+ ")")
	public void scanLifeCycleAnnotation(){
		/* 这里不需要实例化，仅仅是为了提供一个定义扫描的接口 */
	}
	
	@Around("scanLifeCycleAnnotation()")
	public Object capture(ProceedingJoinPoint pjp) {
		Object result = null;
		try {
			result = pjp.proceed();
			logger.info("Happened in : " + host+":"+port);
		} catch (Throwable e) {
			// TODO Exception occurred and the origin method can not solve this problem
			logger.error("Exception",e);
			e.printStackTrace();
		}
		return result;
	}
	
}