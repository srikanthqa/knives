package com.github.knives.quartz;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.h2.tools.Server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class JobstoreTest {
    @Rule
    public TemporaryFolder tmpDir = new TemporaryFolder();

    @Test
    public void test() throws InterruptedException, ClassNotFoundException {
        try {
            final Server server = Server.createTcpServer("-tcpPort", "9123", "-tcpAllowOthers").start();

            // init tables;
            final String url = "jdbc:h2:tcp://localhost:9123/"
                    + tmpDir.getRoot().getCanonicalPath();

            final String urlWithInitialize = url + ";INIT=runscript from 'src/test/resources/tables_h2.sql'";
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(urlWithInitialize);
            conn.close();

            // setup custom Scheduler by using properties programatically
            Properties properties = new Properties();
            properties.setProperty("org.quartz.scheduler.instanceName", JobstoreTest.class.getName());
            properties.setProperty("org.quartz.threadPool.threadCount", "3");
            properties.setProperty("org.quartz.scheduler.skipUpdateCheck", "true");
            // JobStoreTX managed all its transactions instead of replying on application JTA
            // if you want to reply on application JTA, use org.quartz.impl.jdbcjobstore.JobStoreCMT
            properties.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");

            // h2 could work with stdJDBCDelegate
            properties.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
            properties.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");
            // this data source name is needed when configure datasource properties
            properties.setProperty("org.quartz.jobStore.dataSource", "JobstoreTestDS");

            // INIT=create schema if not exists test\;runscript from '~/sql/populate.sql
            // data source properties
            properties.setProperty("org.quartz.dataSource.JobstoreTestDS.driver", "org.h2.Driver");
            properties.setProperty("org.quartz.dataSource.JobstoreTestDS.URL", url);
            properties.setProperty("org.quartz.dataSource.JobstoreTestDS.user", "");
            properties.setProperty("org.quartz.dataSource.JobstoreTestDS.password", "");
            properties.setProperty("org.quartz.dataSource.JobstoreTestDS.maxConnections", "5");

            StdSchedulerFactory schedulerFactory = new StdSchedulerFactory(properties);

            Scheduler scheduler = schedulerFactory.getScheduler();

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
                            .withIntervalInSeconds(5)
                            .repeatForever())
                    .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);

            Thread.sleep(10000);

            scheduler.shutdown();

            server.stop();
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
