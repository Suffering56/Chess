package com.example.chess.aspects.advice;

import com.example.chess.aspects.ProfileExecutionTime;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Log4j2
@Aspect
@Component
public class ProfileExecutionTimeAdvice {

    @Around("@annotation(com.example.chess.aspects.ProfileExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ProfileExecutionTime profileExecutionTime = method.getAnnotation(ProfileExecutionTime.class);

        String signatureText = signature.getDeclaringType().getSimpleName() + "." + signature.getName();
        if (profileExecutionTime.showMethodArgsCount()) {
            signatureText = signatureText + "[" + +signature.getMethod().getParameterCount() + "]";
        }
        log.info("<" + signatureText + "> executed in " + executionTime + "ms");

        return proceed;
    }
}
