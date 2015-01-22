package com.github.knives.quartz;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

import org.junit.Test;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;



public class QuartzTest {

	public static class HelloJob implements Job {
		@Override
		public void execute(JobExecutionContext context)
				throws JobExecutionException {
			System.out.println("hello world");
			
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

            // Trigger the job to run now, and then repeat every 40 seconds
            Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(40)
                        .repeatForever())            
                .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);
            
            Thread.sleep(60000);
            
            scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
	}

}
