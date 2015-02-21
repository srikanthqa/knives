package com.github.knives.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloJob implements Job {
    final private static Logger LOG = LoggerFactory.getLogger(JobstoreTest.class);

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        LOG.info("hello world");
    }
}
