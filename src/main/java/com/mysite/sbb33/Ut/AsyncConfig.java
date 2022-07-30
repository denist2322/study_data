package com.mysite.sbb33.Ut;

import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

public class AsyncConfig extends AsyncConfigurerSupport {

    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500);
        //executor.setThreadNamePrefix("hanumoka-async-");
        executor.initialize();
        return executor;
    }
}
