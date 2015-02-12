package com.github.knives.osgi.blueprint.service.dictionary.client;

import com.github.knives.osgi.service.dictionary.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlueprintDictionaryServiceClient {
    final private static Logger LOG = LoggerFactory.getLogger(BlueprintDictionaryServiceClient.class);

	private DictionaryService dictionaryService;

	public void startUp() {
        LOG.info("Welcome to blueprint dictionary client");
        LOG.info("Is blueprint in dictionary? {}", dictionaryService.checkWord("blueprint"));
	}
	
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}
