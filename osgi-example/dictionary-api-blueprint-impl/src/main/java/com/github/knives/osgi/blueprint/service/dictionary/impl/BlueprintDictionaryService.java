package com.github.knives.osgi.blueprint.service.dictionary.impl;

import com.github.knives.osgi.service.dictionary.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlueprintDictionaryService implements DictionaryService {
    final private static Logger LOG = LoggerFactory.getLogger(BlueprintDictionaryService.class);

	final private String[] dictionary = { "welcome", 
			"to", 
			"the", 
			"blueprint", 
			"dictionary", 
			"tutorial" };

	public void startUp() {
        LOG.info("Welcome to blueprint dictionary service");
	}
	
	@Override
	public boolean checkWord(String word) {

		word = word.toLowerCase();

		for (int i = 0; i < dictionary.length; i++) {
			if (dictionary[i].equals(word)) {
				return true;
			}
		}
		return false;
	}


}
