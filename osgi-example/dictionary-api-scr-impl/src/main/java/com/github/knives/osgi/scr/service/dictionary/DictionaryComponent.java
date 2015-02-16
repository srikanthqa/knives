package com.github.knives.osgi.scr.service.dictionary;

import com.github.knives.osgi.service.dictionary.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class DictionaryComponent implements DictionaryService {
    final private static Logger LOG = LoggerFactory.getLogger(DictionaryComponent.class);

    @Override
    public boolean checkWord(String word) {
        LOG.info("checkWord: [{}]", word);
        return false;
    }

    public void activate(Map<String,Object> props) {
        LOG.info("activate: [{}]", props);
    }

    public void deactivate() {
        LOG.info("deactivate");
    }

    public void modified(Map<String,Object> props) {
        LOG.info("modified: [{}]", props);
    }
}
