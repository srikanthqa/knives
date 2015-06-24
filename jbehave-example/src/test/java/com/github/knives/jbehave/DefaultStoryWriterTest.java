package com.github.knives.jbehave;

import static org.junit.Assert.assertEquals;

import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryLoader;
import org.jbehave.core.model.Story;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.parsers.StoryParser;
import org.junit.Test;

public class DefaultStoryWriterTest {
	
	private final StoryLoader storyLoader = new LoadFromClasspath();
	private final StoryParser storyParser = new RegexStoryParser();
	private final StoryWriter storyWriter = new DefaultStoryWriter(new Keywords());
	private final static Keywords keywords = new Keywords();
	
	private Story loadStory(String storyPath) {
		String storyAsText = storyLoader.loadStoryAsText(storyPath);
		return storyParser.parseStory(storyAsText);
	}
	
	@Test
	public void testStoryWithOneScenario() {
		Story story = loadStory("stories/givenstories_story.story");
		
		String storyAsText = storyWriter.toStoryAsText(story);
		
		Story dupStory = storyParser.parseStory(storyAsText);
		
		System.out.println(storyAsText);
		assertEqualsStory(story, dupStory);
	}
	
	@Test
	public void testStoryWithTwoScenarios() {
		Story story = loadStory("stories/lifecycle_story.story");
		
		String storyAsText = storyWriter.toStoryAsText(story);
		
		Story dupStory = storyParser.parseStory(storyAsText);
		
		System.out.println(storyAsText);
		assertEqualsStory(story, dupStory);
	}
	
	@Test
	public void testStoryWithMeta() {
		Story story = loadStory("stories/meta_story.story");
		
		String storyAsText = storyWriter.toStoryAsText(story);
		
		Story dupStory = storyParser.parseStory(storyAsText);
		
		System.out.println(storyAsText);
		assertEqualsStory(story, dupStory);
	}
	
	
	public static void assertEqualsStory(Story expected, Story actual) {
		assertEquals(expected.getDescription().asString(), actual.getDescription().asString());
		
		assertEquals(expected.getMeta().isEmpty(), actual.getMeta().isEmpty());
		
		if (!expected.getMeta().isEmpty()) {
			assertEquals(expected.getMeta().getPropertyNames(),
						 actual.getMeta().getPropertyNames());
			
			for (String name : expected.getMeta().getPropertyNames()) {
				assertEquals(expected.getMeta().getProperty(name), actual.getMeta().getProperty(name));
			}
		}
		
		assertEquals(expected.getNarrative().isEmpty(), actual.getNarrative().isEmpty());
		
		if (!expected.getNarrative().isEmpty()) {
			assertEquals(expected.getNarrative().asString(keywords), actual.getNarrative().asString(keywords));
		}
		
		assertEquals(expected.getGivenStories().asString(), actual.getGivenStories().asString());
		
		assertEquals(expected.getLifecycle().isEmpty(), actual.getLifecycle().isEmpty());
		
	}
}
