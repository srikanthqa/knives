package com.github.knives.jbehave;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryLoader;
import org.jbehave.core.model.Lifecycle;
import org.jbehave.core.model.Meta;
import org.jbehave.core.model.Scenario;
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
		testStory("stories/givenstories_story.story");
	}
	
	@Test
	public void testStoryWithTwoScenarios() {
		testStory("stories/lifecycle_story.story");
	}
	
	@Test
	public void testStoryWithMeta() {
		testStory("stories/meta_story.story");
	}
	
	@Test
	public void testStoryWithStepHaveParametersInput() {
		testStory("stories/step_have_parameters.story");
	}
	
	private void testStory(String path) {
		Story story = loadStory(path);
		
		String storyAsText = storyWriter.toStoryAsText(story);
		
		Story dupStory = storyParser.parseStory(storyAsText);
		
		System.out.println(storyAsText);
		assertEqualsStory(story, dupStory);
	}
	
	public static void assertEqualsStory(Story expected, Story actual) {
		assertEquals(expected.getDescription().asString(), actual.getDescription().asString());
		
		assertEqualsMeta(expected.getMeta(), actual.getMeta());
		
		assertEquals(expected.getNarrative().isEmpty(), actual.getNarrative().isEmpty());
		
		if (!expected.getNarrative().isEmpty()) {
			assertEquals(expected.getNarrative().asString(keywords), actual.getNarrative().asString(keywords));
		}
		
		assertEquals(expected.getGivenStories().asString(), actual.getGivenStories().asString());
		
		assertEqualsLifecycle(expected.getLifecycle(), actual.getLifecycle());
		
		assertEquals(expected.getScenarios().size(), actual.getScenarios().size());
		
		for (int i = 0; i < expected.getScenarios().size(); i++) {
			assertEqualsScenario(expected.getScenarios().get(i), actual.getScenarios().get(i));
		}
	}
	
	public static void assertEqualsMeta(Meta expected, Meta actual) {
		assertEquals(expected.isEmpty(), actual.isEmpty());
		
		if (!expected.isEmpty()) {
			assertEquals(expected.getPropertyNames(),
						 actual.getPropertyNames());
			
			for (String name : expected.getPropertyNames()) {
				assertEquals(expected.getProperty(name), actual.getProperty(name));
			}
		}
	}
	
	public static void assertEqualsLifecycle(Lifecycle expected, Lifecycle actual) {
		assertEquals(expected.isEmpty(), actual.isEmpty());
		
		if (!expected.isEmpty()) {
			
			assertEquals(expected.getBeforeSteps().size(), actual.getBeforeSteps().size());
			assertEquals(expected.getBeforeSteps(), actual.getBeforeSteps());

			assertEquals(expected.getAfterSteps().isEmpty(), actual.getAfterSteps().isEmpty());
			assertEquals(expected.getOutcomes(), actual.getOutcomes());

			if (!expected.getAfterSteps().isEmpty()) {
				final Set<Outcome> outcomes = expected.getOutcomes();

				for (Outcome outcome : outcomes) {
					assertEquals(expected.getAfterSteps(outcome), actual.getAfterSteps(outcome));
				}
			}
		}
	}
	
	
	public static void assertEqualsScenario(Scenario expected, Scenario actual) {
		assertEquals(expected.getTitle(), actual.getTitle());
		
		assertEqualsMeta(expected.getMeta(), actual.getMeta());
		
		assertEquals(expected.getGivenStories().asString(), actual.getGivenStories().asString());

		assertEquals(expected.getSteps().size(), actual.getSteps().size());
		assertEquals(expected.getSteps(), actual.getSteps());
		
		assertEquals(expected.getExamplesTable().asString(), actual.getExamplesTable().asString());
	}
}
