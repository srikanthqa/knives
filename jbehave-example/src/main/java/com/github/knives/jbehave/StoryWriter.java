package com.github.knives.jbehave;

import org.jbehave.core.model.Story;

public interface StoryWriter {
	String toStoryAsText(Story story);
}
