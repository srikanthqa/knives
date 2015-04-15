package com.github.knives.jmeter;

import org.junit.Test;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class JmeterFileExample {
    @Test
    public void test() throws IOException {
        // JMeter Engine
        StandardJMeterEngine jmeter = new StandardJMeterEngine();

        // Initialize Properties, logging, locale, etc.
        JMeterUtils.loadJMeterProperties("src/test/resources/jmeter.properties");
        JMeterUtils.setJMeterHome("/opt/apache-jmeter-2.13");
        //JMeterUtils.initLogging();// you can comment this line out to see extra log messages of i.e. DEBUG level
        //JMeterUtils.initLocale();

        // Initialize JMeter SaveService
        SaveService.loadProperties();

        // Load existing .jmx Test Plan
        HashTree testPlanTree = SaveService.loadTree(Paths.get("src/test/resources/jmeter-example.jmx").toFile());

        // Run JMeter Test
        jmeter.configure(testPlanTree);
        jmeter.run();
    }
}


