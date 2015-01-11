package com.github.knives.configuration;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.junit.Test;

public class CompositeConfigurationTest {

	@Test(expected = NoSuchElementException.class)
	public void testDoNotExistOnPrimitiveGetter() throws ConfigurationException {
		CompositeConfiguration config = new CompositeConfiguration();
		config.addConfiguration(new SystemConfiguration());
		//config.addConfiguration(new PropertiesConfiguration("application.properties"));
		
		config.getBoolean("do_not_exist");
	}
	
	@Test
	public void testDoNotExistOnStringGetter() {
		CompositeConfiguration config = new CompositeConfiguration();
		config.addConfiguration(new SystemConfiguration());
		
		assertNull(config.getString("do_not_exist"));
	}
	
	@Test
	public void testReloadingStragety() throws ConfigurationException {
		PropertiesConfiguration config = new PropertiesConfiguration("usergui.properties");
		config.setReloadingStrategy(new FileChangedReloadingStrategy());
	}
	
	@Test
	public void testHierarchyProperties() throws ConfigurationException {
		XMLConfiguration config = new XMLConfiguration("tables.xml");
	}

}
