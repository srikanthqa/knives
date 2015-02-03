package com.github.knives.osgi.blueprint.service.dictionary.impl;

import com.github.knives.osgi.service.dictionary.DictionaryService;

public class BlueprintDictionaryService implements DictionaryService {

	final private String[] dictionary = { "welcome", 
			"to", 
			"the", 
			"blueprint", 
			"dictionary", 
			"tutorial" };

	public void startUp() {
		System.out.println("Welcome to blueprint dictionary service");
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
