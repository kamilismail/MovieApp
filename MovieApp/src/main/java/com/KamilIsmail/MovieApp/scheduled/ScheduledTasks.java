package com.KamilIsmail.MovieApp.scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    @Scheduled(cron = "0 0 8 * * *") //everyday at 8am
    public void checkTVGuide() {
        log.info("Checking tv guide at: ", dateFormat.format(new Date()));
    }

    @Scheduled(cron = "0 0/10 * 1/1 * ? *") //every 10 minutes
    public void checkReminders() {
        log.info("Checking reminders at: ", dateFormat.format(new Date()));
    }
}