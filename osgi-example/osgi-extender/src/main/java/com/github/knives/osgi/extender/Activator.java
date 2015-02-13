package com.github.knives.osgi.extender;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.BundleTracker;

public class Activator implements BundleActivator {

    private volatile BundleTracker<Bundle> tracker;

    @Override
    public void start(BundleContext context) throws Exception {
        tracker = new BundleTracker<Bundle>(context,
                Bundle.ACTIVE, new DictionaryServiceExtender());
        tracker.open();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        tracker.close();
    }
}
