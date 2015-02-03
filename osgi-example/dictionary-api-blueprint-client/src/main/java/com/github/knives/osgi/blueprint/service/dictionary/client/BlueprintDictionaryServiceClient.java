package com.github.knives.osgi.blueprint.service.dictionary.client;

import com.github.knives.osgi.service.dictionary.DictionaryService;

public class BlueprintDictionaryServiceClient {
	private DictionaryService dictionaryService;

	public void startUp() {
		System.out.println("Welcome to blueprint dictionary client");
		System.out.println("Is blueprint in dictionary? " 
				+ dictionaryService.checkWord("blueprint"));
	}
	
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}
