package com.github.knives.osgi.whiteboard;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
    final private static Logger LOG = LoggerFactory.getLogger(Activator.class);
    
    final private static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    
    private DictionaryServiceTracker tracker; 
    
    
    @Override
    public void start(BundleContext context) throws Exception {
    	LOG.info("Start Whiteboard example");
    	tracker = new DictionaryServiceTracker(context);
    	tracker.open();
    	
    	executor.scheduleAtFixedRate(() -> tracker.checkWord("blueprint"), 
    			0, 30, TimeUnit.SECONDS);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    	tracker.close();
    	executor.shutdownNow();
    	LOG.info("Stop Whiteboard example");
    }
    
    
}
