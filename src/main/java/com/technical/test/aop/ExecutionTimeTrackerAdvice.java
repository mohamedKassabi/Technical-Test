package com.technical.test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeTrackerAdvice {
	
	private static final String TIME_TAKEN_TO_EXECUTE = " time taken to execute ";
	private static final String METHOD_NAME = "Method Name ";
	Logger logger = LoggerFactory.getLogger(ExecutionTimeTrackerAdvice.class);
	
	
	@Around("@annotation(com.technical.test.aop.TrackExecutionTime)")
	public Object trackTime(ProceedingJoinPoint pjp) throws Throwable {
		
	
		long startTime = System.currentTimeMillis();
		Object obj  = pjp.proceed();
		long endTime = System.currentTimeMillis();
		
		logger.info(METHOD_NAME + pjp.getSignature() + TIME_TAKEN_TO_EXECUTE +(endTime-startTime) );
		
		return obj;
		
	}

}
