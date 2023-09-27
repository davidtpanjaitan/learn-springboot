package com.tofu.demo;

import com.tofu.demo.scheduler.ScheduledJob;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class QuartzConfig {

    @Autowired
    private JobFactory jobFactory;
    @Bean
    @Primary
    public SchedulerFactoryBean schedulerFactoryBean(
                DataSource dataSource) throws SchedulerException {

            SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
            schedulerFactoryBean.setDataSource(dataSource);
            schedulerFactoryBean.setJobFactory(jobFactory);
            schedulerFactoryBean.setQuartzProperties(quartzProperties());
            schedulerFactoryBean.setSchedulerName("MyScheduler");


            schedulerFactoryBean.setOverwriteExistingJobs(true);
            schedulerFactoryBean.setAutoStartup(true);
            schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);

            return schedulerFactoryBean;
    }

    @Bean
    public Properties quartzProperties() {
        Properties properties = new Properties();
//        properties.setProperty("org.quartz.scheduler.instanceName", schedulerName);
//        properties.setProperty("org.quartz.scheduler.instanceId", schedulerInstanceId);
        // Set other Quartz properties here...
        return properties;
    }
    @Bean
    public JobDetail myJobDetail() {
        JobDetail jobDetail = JobBuilder.newJob(ScheduledJob.class)
                .withIdentity("myJob", "group1")
                .storeDurably()
                .build();

        // Debugging output
        System.out.println("Job created with key: " + jobDetail.getKey());

        return jobDetail;
    }

    @Bean
    public Trigger myTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(myJobDetail())
                .withIdentity("reportTrigger", "group1")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?"))
                .build();

    }
}
