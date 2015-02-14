package com.github.knives.osgi.cm.consumer;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Dictionary;

public class ConfigurationConsumer implements ManagedService {

    final private static Logger LOG = LoggerFactory.getLogger(ConfigurationConsumer.class);

    @Override
    public void updated(Dictionary<String, ?> properties) throws ConfigurationException {
        LOG.info("Receive update [{}]", properties);
    }
}
