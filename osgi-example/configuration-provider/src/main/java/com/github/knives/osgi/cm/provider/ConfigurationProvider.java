package com.github.knives.osgi.cm.provider;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

public class ConfigurationProvider {
    final private static Logger LOG = LoggerFactory.getLogger(ConfigurationProvider.class);

    private ConfigurationAdmin configurationAdmin;

    public void startUp() throws IOException {
        LOG.info("Update configurationConsumer properties");

        Configuration config = configurationAdmin.getConfiguration("configurationConsumer", null);
        Dictionary props = config.getProperties();

        // if null, the configuration is new
        if (props == null) {
            props = new Hashtable();
        }

        // set some properties
        props.put("hello", "configurationConsumer");

        // update the configuration
        config.update(props);
    }

    public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
        this.configurationAdmin = configurationAdmin;
    }
}
