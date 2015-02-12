package com.github.knives.osgi.eventadmin;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
    final private static Logger LOG = LoggerFactory.getLogger(Activator.class);

    @Override
    public void start(BundleContext context) throws Exception {
    	LOG.info("Start EventAdmin example");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    	LOG.info("Stop EventAdmin example");
    }
}
