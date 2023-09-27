package com.tofu.demo.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ScheduledJob implements Job {
    public ScheduledJob() {

    }
    private static long secs = 0;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        var prev = jobExecutionContext.getPreviousFireTime();
        if (prev == null)
            System.out.println("New day on the job!");
        else {
            var now = jobExecutionContext.getFireTime();
            var duration = Duration.between(prev.toInstant(), now.toInstant()).getSeconds();
            secs += duration;
            System.out.println("Been working on the job for "+ secs + " seconds");
        }
    }
}
