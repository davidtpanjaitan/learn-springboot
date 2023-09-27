package com.tofu.demo.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReportScheduler {
    private static int secs = 0;
    @Scheduled(fixedRate = 5000)
    public void reportTime() {
        secs += 5;
        System.out.println(secs + " seconds has passed since the start");
    }
}
