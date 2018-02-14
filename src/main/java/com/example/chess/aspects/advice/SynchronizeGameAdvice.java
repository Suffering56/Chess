package com.example.chess.aspects.advice;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class SynchronizeGameAdvice {

    @Around(value = "@annotation(com.example.chess.aspects.SynchronizeGame)")
    public Object synchronizeGame(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("aspect.synchronizeGame");
        Object proceed = joinPoint.proceed();
        return proceed;
    }
}
