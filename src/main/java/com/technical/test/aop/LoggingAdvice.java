package com.technical.test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class LoggingAdvice {

	private static final String RESPONSE = " Response ";
	private static final String ARGUMENTS = " arguments : ";
	private static final String METHOD_INVOKED = " method invoked ";
	Logger log = LoggerFactory.getLogger(LoggingAdvice.class);

	@Pointcut(value = "execution(* com.technical.test.controller.*.*(..))")
	public void myPointCut() {

	}

	@Around("myPointCut()")
	public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		String methodName = pjp.getSignature().getName();
		String className = pjp.getTarget().getClass().getName();
		Object[] array = pjp.getArgs();
		log.info(METHOD_INVOKED + className + " : " + methodName + "()" + ARGUMENTS
				+ mapper.writeValueAsString(array));

		Object object = pjp.proceed();

		log.info(className + ":" + methodName + "()" + RESPONSE + mapper.writeValueAsString(object));

		return object;
	}
}
