package com.hsbc.balance.common.aop.annotation;

import java.lang.annotation.*;

/**
 * LogMonitor
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
@Inherited
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMonitor {
}
