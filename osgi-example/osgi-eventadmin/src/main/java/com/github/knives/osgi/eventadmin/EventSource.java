package com.github.knives.osgi.eventadmin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventSource {
    final private static Logger LOG = LoggerFactory.getLogger(EventSource.class);

    final private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    
	private EventAdmin eventAdmin;

	public void startUp() {
		LOG.info("Schedule to postEvent per 30 seconds");
		
    	executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					LOG.info("posting event");
	
					Map<String, Object> message = new HashMap<String, Object>();
					message.put("hello", "world");
					message.put("time", System.currentTimeMillis());
					eventAdmin.postEvent(new Event("broadcast", message));
					
					LOG.info("posted event");
				} catch (Exception e) {
					LOG.error("Caught exception", e);
				}
			}
		}, 0, 30, TimeUnit.SECONDS);
	}
	
	public void shutdown() {
		executor.shutdownNow();
		
		LOG.info("executor shutdown");
	}
	
	
	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}
}
