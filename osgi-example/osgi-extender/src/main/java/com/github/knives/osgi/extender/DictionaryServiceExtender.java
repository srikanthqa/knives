package com.github.knives.osgi.extender;

import com.github.knives.osgi.service.dictionary.DictionaryService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DictionaryServiceExtender implements BundleTrackerCustomizer<Bundle> {
    final private static Logger LOG = LoggerFactory.getLogger(DictionaryServiceExtender.class);

    private static final String DICTIONARY_SERVICE_HEADER = "Dictionary-ServiceClass";

    private final Map<String, ServiceRegistration<DictionaryService>> registrations
            = new ConcurrentHashMap<String, ServiceRegistration<DictionaryService>>();

    @Override
    public Bundle addingBundle(Bundle bundle, BundleEvent event) {
        LOG.trace("adding Bundle event [{}]", bundle.getLocation());

        final String className = (String) bundle.getHeaders().get(DICTIONARY_SERVICE_HEADER);
        if (className == null) {
            LOG.trace("No header found, return null for not track");
            return null;
        }

        try {
            Class<?> serviceClass = bundle.loadClass(className);
            if (serviceClass == null) {
                LOG.warn("Unable to load class [{}]", className);
                return null;
            }

            if (!DictionaryService.class.isAssignableFrom(serviceClass)) {
                LOG.warn("Class [{}] is not a DictionaryService", className);
                return null;
            }

            DictionaryService instance = (DictionaryService)serviceClass.newInstance();
            ServiceRegistration<DictionaryService> reg = bundle.getBundleContext().registerService(DictionaryService.class,
                    instance, null);

            registrations.put(bundle.getLocation(), reg);

            return bundle;

        } catch (ClassNotFoundException e) {
            LOG.error("Cannot found class [{}]", className, e);
        } catch (InstantiationException e) {
            LOG.error("Unable to create [{}]", className, e);
        } catch (IllegalAccessException e) {
            LOG.error("Don't have access to [{}]", className, e);
        }

        return null;
    }

    @Override
    public void modifiedBundle(Bundle bundle, BundleEvent event, Bundle object) {
        LOG.info("modified Bundle event [{}]", bundle.getLocation());
    }

    @Override
    public void removedBundle(Bundle bundle, BundleEvent event, Bundle object) {
        ServiceRegistration<DictionaryService> reg = registrations.remove(bundle.getLocation());
        if (reg != null) reg.unregister();
    }
}
