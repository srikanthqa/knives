package com.github.knives.jbehave.configuration;

import java.net.URL;

import org.jbehave.core.io.LoadFromRelativeFile;
import org.jbehave.core.io.StoryLoader;

public class SensibleStoryLoader extends LoadFromRelativeFile implements StoryLoader {

	public SensibleStoryLoader(URL location, StoryFilePath... traversals) {
		super(location, traversals);
	}

	public SensibleStoryLoader(URL location) {
		super(location);
	}
}
