package com.github.knives.jbehave.configuration;

import org.jbehave.core.configuration.Configuration;

public class SensibleConfiguration extends Configuration {

	public SensibleConfiguration() {
		useDefaultStoryReporter(new SensibleStoryReporter());
		useFailureStrategy(new SensibleFailureStrategy());
		useKeywords(new SensibleKeywords());
		useParameterControls(new SensibleParameterControls());
		// TODO: more
	}
}
