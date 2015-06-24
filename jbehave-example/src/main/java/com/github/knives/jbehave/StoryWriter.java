package com.github.knives.jbehave;

import org.jbehave.core.model.Story;

/**
 * Translate Story object back to a compatible text, 
 * not exactly the same text that create the story,
 * but correct  such that parse again, you would get the same Story.
 */
public interface StoryWriter {
	String toStoryAsText(Story story);
}
