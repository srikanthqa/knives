package com.github.knives.quartz;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class JobExceptionTest {
    final private static Logger LOG = LoggerFactory.getLogger(JobExceptionTest.class);

    private static final String COUNTER_KEY = "counter";

    // Here is a though experiment with retry
    // with maximum number of retry / quartz is not supporting officially
    // with delay between retry / quartz is not supporting officially
    //
    // 1. We could
    // create a new job with the job at specific schedule retry time
    // context.getScheduler()
    // handling misfire etc...
    //
    // 2. We could
    // implements counter mechanism like below and Thread.sleep for delay
    // This approach would take up threads mean while Scheduler only have limited number of threads
    @PersistJobDataAfterExecution
    @DisallowConcurrentExecution
    public static class RepeatableBadJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            final JobDataMap data = context.getJobDetail().getJobDataMap();
            final int count = data.getInt(COUNTER_KEY);
            data.put(COUNTER_KEY, count + 1);

            LOG.info("RepeatableBadJob repeat local counter [{}]", count);

            boolean throwException = count == 0;
            if (throwException) {
                JobExecutionException e = new JobExecutionException("I just like to retry");
                e.refireImmediately();
                throw e;
            }
        }
    }

    public static class DiscardCurrentTriggerWhenGoingBadJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            // should still see the following log print 4 times
            LOG.info("DiscardCurrentTriggerWhenGoingBadJob");
            JobExecutionException e = new JobExecutionException();
            e.setUnscheduleFiringTrigger(true);
            throw e;
        }
    }

    public static class DiscardJobWhenGoingReallyBad implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            // should still see the following log print 1 time
            LOG.info("DiscardJobWhenGoingReallyBad");

            JobExecutionException e = new JobExecutionException();
            e.setUnscheduleAllTriggers(true);
            throw e;
        }
    }

    @Test
    public void testRetryImmediate() throws InterruptedException {
        testJob(RepeatableBadJob.class);
    }

    // I can't tell the difference, the following test
    @Test
    public void testDiscardCurrentTrigger() throws InterruptedException {
        testJob(DiscardCurrentTriggerWhenGoingBadJob.class);
    }

    @Test
    public void testDiscardAllTriggers() throws InterruptedException {
        testJob(DiscardJobWhenGoingReallyBad.class);
    }

    private <T extends Job> void testJob(Class<T> jobClass) throws InterruptedException {
        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            // define the job and tie it to our HelloJob class
            JobDetail job = newJob(jobClass)
                    .withIdentity("job1", "group1")
                    .usingJobData(COUNTER_KEY, 0)
                    .usingJobData(new JobDataMap())
                    .build();

            // Trigger the job to run now, and then repeat every 40 seconds
            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(2)
                            .withRepeatCount(4))
                    .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);

            Thread.sleep(10000);

            scheduler.shutdown();

        } catch (SchedulerException e) {
            LOG.error("Caught", e);
        }
    }
}
