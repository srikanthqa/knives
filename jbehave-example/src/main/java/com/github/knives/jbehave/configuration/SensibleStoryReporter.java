package com.github.knives.jbehave.configuration;

import java.util.List;
import java.util.Map;

import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.model.GivenStories;
import org.jbehave.core.model.Lifecycle;
import org.jbehave.core.model.Meta;
import org.jbehave.core.model.Narrative;
import org.jbehave.core.model.OutcomesTable;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.model.StoryDuration;
import org.jbehave.core.reporters.StoryReporter;

public class SensibleStoryReporter implements StoryReporter {

	@Override
	public void storyNotAllowed(Story story, String filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void storyCancelled(Story story, StoryDuration storyDuration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeStory(Story story, boolean givenStory) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterStory(boolean givenStory) {
		// TODO Auto-generated method stub

	}

	@Override
	public void narrative(Narrative narrative) {
		// TODO Auto-generated method stub

	}

	@Override
	public void lifecyle(Lifecycle lifecycle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scenarioNotAllowed(Scenario scenario, String filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeScenario(String scenarioTitle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scenarioMeta(Meta meta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterScenario() {
		// TODO Auto-generated method stub

	}

	@Override
	public void givenStories(GivenStories givenStories) {
		// TODO Auto-generated method stub

	}

	@Override
	public void givenStories(List<String> storyPaths) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeExamples(List<String> steps, ExamplesTable table) {
		// TODO Auto-generated method stub

	}

	@Override
	public void example(Map<String, String> tableRow) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterExamples() {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeStep(String step) {
		// TODO Auto-generated method stub

	}

	@Override
	public void successful(String step) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ignorable(String step) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pending(String step) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notPerformed(String step) {
		// TODO Auto-generated method stub

	}

	@Override
	public void failed(String step, Throwable cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void failedOutcomes(String step, OutcomesTable table) {
		// TODO Auto-generated method stub

	}

	@Override
	public void restarted(String step, Throwable cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void restartedStory(Story story, Throwable cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dryRun() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pendingMethods(List<String> methods) {
		// TODO Auto-generated method stub

	}

}
