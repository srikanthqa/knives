package com.github.knives.osgi.whiteboard;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.knives.osgi.service.dictionary.DictionaryService;

public class DictionaryServiceTracker extends ServiceTracker<DictionaryService, DictionaryService> {
    final private static Logger LOG = LoggerFactory.getLogger(DictionaryServiceTracker.class);

	public DictionaryServiceTracker(BundleContext context) {
		super(context, DictionaryService.class, null);
	}
	
	public void checkWord(String word) {
		final ServiceReference<DictionaryService> references[] = getServiceReferences();
		for (ServiceReference<DictionaryService> reference : references) {
			DictionaryService dictionaryService = getService(reference);
			LOG.info("Is {} in dictionary? {}", word, dictionaryService.checkWord(word));
		}
		LOG.info("=========================");
	}
}
