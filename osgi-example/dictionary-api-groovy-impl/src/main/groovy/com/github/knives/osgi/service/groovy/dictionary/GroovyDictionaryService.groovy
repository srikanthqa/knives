package com.github.knives.osgi.service.groovy.dictionary

import com.github.knives.osgi.service.dictionary.DictionaryService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

public class GroovyDictionaryService implements DictionaryService {
    final private static Logger LOG = LoggerFactory.getLogger(GroovyDictionaryService.class);

    final private static def WORDS = ['groovy', 'is', 'awesome']

    void startUp() {
        LOG.info("Start up with words: [{}]", WORDS);
    }

    @Override
    boolean checkWord(String word) {
        return WORDS.contains(word)
    }
}
