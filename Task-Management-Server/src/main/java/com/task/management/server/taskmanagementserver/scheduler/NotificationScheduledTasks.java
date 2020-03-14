package com.task.management.server.taskmanagementserver.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@EnableAsync
public class NotificationScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(NotificationScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM:HH:mm:ss");


    @Async
    @Scheduled(initialDelay = 5000, fixedRate = 600000)
    public void scheduleTaskWithFixedRate() throws InterruptedException {
        logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        Thread.sleep(5000);
        logger.info("Done", dateTimeFormatter.format(LocalDateTime.now()));

    }
}