package com.hsbc.balance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * ThreadPoolConfig
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
@Configuration
@EnableAsync
public class ThreadPoolConfig {
    /**
     * 使用spring自带的线程池
     * @return 配置好的线程池执行器对象。
     */
    @Bean(name = "eventTaskExecutor")
    public Executor eventTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("eventExecutor-");
        executor.initialize();
        return executor;
    }
}
