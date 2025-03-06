package com.app.school.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Aspect
@Component
public class LoggerAspect {

    @Around("execution(* com.app.school..*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodSignature = joinPoint.getSignature().toString();
        log.info("Executing: {}", methodSignature);

        Instant start = Instant.now();
        Object returnObj = joinPoint.proceed();
        long timeElapsed = Duration.between(start, Instant.now()).toMillis();

        log.info("Executed: {} | Time Taken: {} ms", methodSignature, timeElapsed);
        return returnObj;
    }

    @AfterThrowing(value = "execution(* com.app.school..*.*(..))", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        log.error("Exception in {} | Message: {}", joinPoint.getSignature(), exception.getMessage(), exception);
    }
}