package com.hsbc.balance.common.aop.aspect;

import com.hsbc.balance.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * LogMonitorAspect
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
@Slf4j
@Aspect
@Component
public class LogMonitorAspect {

    @Around("@annotation(com.hsbc.balance.common.aop.annotation.LogMonitor)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        // Build the entering log message
        String methodName = joinPoint.getSignature().getName();
        String packageName = joinPoint.getSignature().getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();
        StringBuilder enterLog = new StringBuilder("Entering method: ");
        enterLog.append(packageName)
                .append(".")
                .append(methodName)
                .append("; 入参：");
        for (Object arg : args) {
            enterLog.append(arg).append(", ");
        }
        log.info(enterLog.toString());

        // Proceed with the original method call
        Object result = joinPoint.proceed();

        // Build the exiting log message
        StringBuilder exitLog = new StringBuilder("Exiting method: ");
        exitLog.append(packageName)
                .append(".")
                .append(methodName)
                .append("; 结果：")
                .append(JsonUtil.toJson(result));
        log.info(exitLog.toString());

        return result;
    }
}
