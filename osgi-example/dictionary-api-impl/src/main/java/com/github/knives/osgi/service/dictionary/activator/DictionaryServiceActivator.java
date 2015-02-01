package com.github.knives.osgi.service.dictionary.activator;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.github.knives.osgi.service.dictionary.DictionaryService;
import com.github.knives.osgi.service.dictionary.impl.DictionaryServiceImpl;

public class DictionaryServiceActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		Hashtable<String, String> props = new Hashtable<String, String>();
        props.put("Language", "English");
        context.registerService(DictionaryService.class.getName(), 
        		new DictionaryServiceImpl(), props);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
	}
}
