package com.github.knives.quartz;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class PersistentJobStateTest {
    public static final String FAVORITE_COLOR = "color";
    public static final String EXECUTION_COUNT = "count";

    /*
        The annotations cause behavior as their names describe. Multiple instances of the
        job will not be allowed to run concurrently (consider a case where a job has code in
        its execute() method that takes 34 seconds to run, but it is scheduled with a trigger
        that repeats every 30 seconds), and will have its JobDataMap contents re-persisted in
        the scheduler's JobStore after each execution. For the purposes of this example, only
        @PersistJobDataAfterExecution annotation is truly relevant, but it's always wise to
        use the @DisallowConcurrentExecution annotation with it, to prevent race-conditions
        on saved data.
     */
    // run longer than expected duration cause the overlap run pick the old value ?
    @PersistJobDataAfterExecution
    @DisallowConcurrentExecution
    public static class ColorMe implements Job {
        final private static Logger LOG = LoggerFactory.getLogger(ColorMe.class);

        private int counter = 1;

        @Override
        public void execute(JobExecutionContext context)
                throws JobExecutionException {

            JobDataMap data = context.getJobDetail().getJobDataMap();
            String favoriteColor = data.getString(FAVORITE_COLOR);
            int count = data.getInt(EXECUTION_COUNT);


            LOG.info("Job fireInstanceId: [{}], fire at [{}], executing at [{}], color: [{}], count: [{}], counter: [{}]",
                    context.getFireInstanceId(), context.getFireTime(), new Date(), favoriteColor, count, counter);

            data.put(EXECUTION_COUNT, count+1);

            // this counter is never persisted, cuz new instance will create every fire
            counter++;
        }
    }

    @Test
    public void test() throws InterruptedException {
        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            JobDetail job1 = newJob(ColorMe.class)
                    .withIdentity("job1", "group1")
                    .usingJobData(FAVORITE_COLOR, "blue")
                    .usingJobData(EXECUTION_COUNT, 1)
                    .build();

            Trigger trigger1 = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withRepeatCount(10)
                            .withIntervalInSeconds(1))
                    .build();

            JobDetail job2 = newJob(ColorMe.class)
                    .withIdentity("job2", "group1")
                    .usingJobData(FAVORITE_COLOR, "red")
                    .usingJobData(EXECUTION_COUNT, 1)
                    .build();

            Trigger trigger2 = newTrigger()
                    .withIdentity("trigger2", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withRepeatCount(5)
                            .withIntervalInSeconds(2))
                    .build();


            scheduler.scheduleJob(job1, trigger1);
            scheduler.scheduleJob(job2, trigger2);

            Thread.sleep(10000);

            scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}
