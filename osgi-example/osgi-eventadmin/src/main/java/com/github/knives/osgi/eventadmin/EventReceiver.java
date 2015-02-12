package com.github.knives.osgi.eventadmin;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventReceiver implements EventHandler {
    final private static Logger LOG = LoggerFactory.getLogger(EventReceiver.class);

	@Override
	public void handleEvent(Event event) {
		LOG.info("Receive event at topic [{}], time: [{}], hello: [{}]", 
				event.getTopic(), event.getProperty("time"), event.getProperty("hello"));
	}

}
