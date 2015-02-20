package com.github.knives.quartz;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class SayHelloJobTest {

    public static class SayHelloJob implements Job {
        final private static Logger LOG = LoggerFactory.getLogger(SayHelloJob.class);

        @Override
        public void execute(JobExecutionContext context)
                throws JobExecutionException {
            LOG.info("hello {}", context.getMergedJobDataMap().getString("name"));
        }
    }

    @Test
    public void test() throws InterruptedException {
        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            // define the job and tie it to our Job class
            JobDetail job = newJob(SayHelloJob.class)
                    .withIdentity("job1", "group1")
                    .usingJobData("name", "john")
                    .build();

            // Trigger the job to run now, and then repeat every 40 seconds
            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withRepeatCount(1)
                            .withIntervalInSeconds(5))
                    .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);

            Thread.sleep(10000);

            scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}
