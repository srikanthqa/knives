package com.github.knives.osgi.service.dictionary.impl;

import com.github.knives.osgi.service.dictionary.DictionaryService;

public class DictionaryServiceImpl implements DictionaryService {

	// The set of words contained in the dictionary.
	final private String[] dictionary = { "welcome", "to", "the", "osgi", "tutorial" };

	/**
	 * Implements DictionaryService.checkWord(). Determines if the passed in
	 * word is contained in the dictionary.
	 * 
	 * @param word
	 *            the word to be checked.
	 * @return true if the word is in the dictionary, false otherwise.
	 **/
	@Override
	public boolean checkWord(String word) {

		word = word.toLowerCase();

		// This is very inefficient
		for (int i = 0; i < dictionary.length; i++) {
			if (dictionary[i].equals(word)) {
				return true;
			}
		}
		return false;
	}
}
