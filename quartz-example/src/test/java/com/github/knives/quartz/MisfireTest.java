package com.github.knives.quartz;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.dateOf;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class MisfireTest {
    final private static Logger LOG = LoggerFactory.getLogger(MisfireTest.class);

    public static class HelloJob implements Job {
        @Override
        public void execute(JobExecutionContext context)
                throws JobExecutionException {
            LOG.info("hello world");

        }
    }

    @Test
    public void test() throws InterruptedException {
        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            // define the job and tie it to our HelloJob class
            JobDetail job = newJob(HelloJob.class)
                    .withIdentity("job1", "group1")
                    .build();

            // misfire policy is tricky per situation
            // simpleSchedule misfire policy is more headache then cron misfire policy

            // ignore policy = trigger ALL misfire(s) even if multiple as soon as possible. Then continue with schedule.
            //
            // next policy = in generally, you want to run a job at particular scheduled time regardless of misfires
            // next policy variation: existing count and remaining count
            //      existing count = misfire will be keep count, if you miss 2, there will be additional 2 triggers
            //      example: schedule per hour, start at 10 p.m., and repeat 5 times. The expected end time is 3 p.m.
            //               if you have 2 misfire, the expected end time is now 5 p.m.
            //               if you have endAt date, then all additional misfire past the date will be discard
            //               if you have a forever repeat instead of limited repeat, it will be same as remaining count
            //                  (cuz infinite + misfire count = infinity)
            //
            //      remaining count = simply discard misfire
            //
            // now policy = for job running periodically, but when misfire, you want to run as soon as possible
            //              and only run once upon multiple misfires. Also, the schedule time = now + interval time.
            // misfire at 10:15 p.m. for supposed to fire at 10:00 p.m..
            // Then the next schedule time is 11:15 instead of 11:00 p.m.
            //
            // now policy variation: existing count and remaining count. same idea as next policy variation
            // Since it is fire once now policy, existing count will be deducted by one.

            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startAt(new Date(new Date().getTime() - 11000L))
                    .withSchedule(simpleSchedule()
                            .withMisfireHandlingInstructionIgnoreMisfires()
                            .withIntervalInSeconds(5)
                            .withRepeatCount(4))
                    .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);

            Thread.sleep(20000);

            scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}
