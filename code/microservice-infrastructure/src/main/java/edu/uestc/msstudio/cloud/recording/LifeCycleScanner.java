package edu.uestc.msstudio.cloud.recording;


import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;

import edu.uestc.msstudio.cloud.userservice.entity.User;

@Aspect
@Component
public class LifeCycleScanner {
	
	private static final String projectRoot = "edu.uestc.msstudio.cloud.service";
	
	@Autowired
	private RecordRepository recordDao;
	
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
	
	HttpServletRequest getRequestInfo(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	User tokenToUser(String authToken){
		User user = new User();
		user.setId(1l);
		return user;
	}
	
	@Around("scanLifeCycleAnnotation()")
	public Object capture(ProceedingJoinPoint pjp) {
		/*HttpServletRequest request = getRequestInfo();
		logger.info(request.getHeader("token"));// this can use to get the very first HTTP request, can it get from inner functions?
		*/
		Object result = null;
		Record target = new Record();
		target.setInstance(host+":"+port);
		target.setStartTime(new Date(System.currentTimeMillis()));//start time
		try {
			// filter the methods that has no LifeCycle annotation
			if (!ifInLifeCycle(pjp)){
				return pjp.proceed();
			}else{
				
				LifeCycle lifeCycle = captureAnnotation(pjp);
				//  catch the target object , judge the type of the argument type
				setOperationType(lifeCycle,target);
				
				setAction(lifeCycle, target);// including set the source type and the target type
				
				setSource(lifeCycle,target, pjp.getArgs());

				result = pjp.proceed();
				
				setTarget(lifeCycle,target, result);
				
				logger.info("Happened in : " + target.getInstance());
				target.setEndTime(new Date(System.currentTimeMillis()));//end time
				target.setOk(true);
				
				recordDao.save(target);
				logger.info("save done:"+new Gson().toJson(target));
				return result;
			}
		} catch (Throwable e) {
			// TODO Exception occurred and the origin method can not solve this problem
			logger.error("Happened in : " + target.getInstance());
			logger.error("Exception",e);
			target.setEndTime(new Date(System.currentTimeMillis()));//end time
			target.setOk(false);
			recordDao.save(target);
		}
		return result;
	}
	
	private void setAction(LifeCycle lifeCycle, Record target) {
			target.setAction(lifeCycle.action());
			VectorDesc desc = VectorList.getVectorDescription(lifeCycle.action());
			target.setTargetObject(VectorList.transTypeToClass(desc.getTargetType()));
			target.setSourceObject(VectorList.transTypeToClass(desc.getSourceType()));
			return ;
	}
	
	private void setTarget(LifeCycle lifeCycle, Record target, Object result) {
		solveTarget(result, target);
		return ;
	}

	private void setSource(LifeCycle lifeCycle, Record target, Object[] args) {
		target.setJsonSource(new Gson().toJson(args));
	}

	private LifeCycle captureAnnotation(ProceedingJoinPoint pjp) throws NoSuchMethodException, SecurityException {
		// get the target annotation which can describe the whole action
		String methodName = pjp.getSignature().getName();
		Class<?>[] paramTypes = ((MethodSignature)pjp.getSignature()).getMethod().getParameterTypes();
		Method targetMethod = pjp.getTarget().getClass().getMethod(methodName,paramTypes);
		targetMethod.setAccessible(true);
		return targetMethod.getDeclaredAnnotation(LifeCycle.class);
	}

	public Boolean ifInLifeCycle(ProceedingJoinPoint pjp) throws NoSuchMethodException, SecurityException{
		String methodName = pjp.getSignature().getName();
		Class<?>[] paramTypes = ((MethodSignature)pjp.getSignature()).getMethod().getParameterTypes();
		Method targetMethod = pjp.getTarget().getClass().getMethod(methodName,paramTypes);
		targetMethod.setAccessible(true);
		return targetMethod.isAnnotationPresent(LifeCycle.class);
	}
	
	private void setOperationType(LifeCycle lifeCycle,Record target){
		VectorDesc vector = VectorList.getVectorDescription(lifeCycle.action());
		
		
		target.setSourceObject(VectorList.transTypeToClass(vector.getSourceType()));
		target.setTargetObject(VectorList.transTypeToClass(vector.getTargetType()));
	}
	
	private void solveTarget(Object result, Record target) {
		String json = new Gson().toJson(result);
		target.setJsonTarget(json);
	}
}
