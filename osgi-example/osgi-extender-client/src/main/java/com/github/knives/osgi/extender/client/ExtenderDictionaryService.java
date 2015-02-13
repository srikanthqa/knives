package com.github.knives.osgi.extender.client;

import com.github.knives.osgi.service.dictionary.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtenderDictionaryService implements DictionaryService {
    final private static Logger LOG = LoggerFactory.getLogger(ExtenderDictionaryService.class);

    public ExtenderDictionaryService() {
        LOG.info("Created");
    }

    @Override
    public boolean checkWord(String word) {
        return false;
    }
}
