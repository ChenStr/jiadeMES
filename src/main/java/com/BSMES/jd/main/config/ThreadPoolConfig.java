package com.BSMES.jd.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * 数据链接池配置
 */
//@Configuration
//@EnableAsync
public class ThreadPoolConfig {

    @Bean
    public Executor asyncServiceExecutor() {
        //ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        //配置核心线程数

        executor.setCorePoolSize(10);

        //配置最大线程数

        executor.setMaxPoolSize(20);

        //配置队列大小

        executor.setQueueCapacity(50);

        //配置线程池中的线程的名称前缀

        executor.setThreadNamePrefix("async-service-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务

        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        //执行初始化

        executor.initialize();

        return executor;

    }


}
