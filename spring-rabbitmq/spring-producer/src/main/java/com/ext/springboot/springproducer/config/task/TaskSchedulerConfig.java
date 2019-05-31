package com.ext.springboot.springproducer.config.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author: xting
 * @CreateDate: 2019/5/29 10:37
 *
 * 定时任务配置
 * @EnableScheduling 启动定时任务
 */
@Configuration
@EnableScheduling
public class TaskSchedulerConfig implements SchedulingConfigurer {


    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskScehduler());
    }

    /**
     * 定时任务线程池
     */
    public Executor taskScehduler(){
         return Executors.newScheduledThreadPool(100);
    }
}
