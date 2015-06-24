package com.github.knives.jbehave;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.model.Description;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.model.GivenStories;
import org.jbehave.core.model.Lifecycle;
import org.jbehave.core.model.Meta;
import org.jbehave.core.model.Narrative;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;

public class DefaultStoryWriter implements StoryWriter {
	private static final char NEWLINE = '\n';
	private static final char SPACE = ' ';
	
	private final Keywords keywords;
	
	public DefaultStoryWriter(Keywords keywords) {
		this.keywords = keywords;
	}
	
	@Override
	public String toStoryAsText(Story story) {
		final StringBuilder builder = new StringBuilder();
		
		writeDescription(builder, story.getDescription());
		writeMeta(builder, story.getMeta());
		writeNarrative(builder, story.getNarrative());
		writeGivenStories(builder, story.getGivenStories());
		writeLifecycle(builder, story.getLifecycle());

		story.getScenarios().forEach(it -> writeScenario(builder, it));
		
		return builder.toString();
	}
	
	protected void writeDescription(StringBuilder builder, Description description) {
		if (!StringUtils.isBlank(description.asString())) {
			builder.append(description.asString())
				.append(NEWLINE)
				.append(NEWLINE);
		}
	}
	
	protected void writeMeta(StringBuilder builder, Meta meta) {
		if (!meta.isEmpty()) {
			builder.append(keywords.meta())
				.append(NEWLINE);
				
			for (String name : meta.getPropertyNames()) {
				builder.append(keywords.metaProperty())
					.append(name).append(SPACE)
					.append(meta.getProperty(name))
					.append(NEWLINE);
			}	
				
			builder.append(NEWLINE);
		}
	}

	protected void writeNarrative(StringBuilder builder, Narrative narrative) {
		if (!narrative.isEmpty()) {
			builder.append(keywords.narrative())
				.append(narrative.asString(keywords))
				.append(NEWLINE)
				.append(NEWLINE);	
		}
	}
	
	protected void writeGivenStories(StringBuilder builder, GivenStories givenStories) {
		if (!givenStories.getStories().isEmpty()) {
			builder.append(keywords.givenStories())
				.append(SPACE)
				.append(givenStories.asString())
				.append(NEWLINE)
				.append(NEWLINE);			
		}
	}
	
	protected void writeLifecycle(StringBuilder builder, Lifecycle lifecycle) {
		if (!lifecycle.isEmpty()) {
			builder.append(keywords.lifecycle())
				.append(NEWLINE);
			
			
			if (!lifecycle.getBeforeSteps().isEmpty()) {
				builder.append(keywords.before())
					.append(NEWLINE);
				
				lifecycle.getBeforeSteps()
					.forEach(it -> builder.append(it).append(NEWLINE));
			}
			
			if (!lifecycle.getAfterSteps().isEmpty()) {
				builder.append(keywords.after())
					.append(NEWLINE);
				
				final Set<Outcome> outcomes = lifecycle.getOutcomes();
				
				for (Outcome outcome : outcomes) {
					builder.append(keywords.outcome())
						.append(SPACE);
					
					switch (outcome) {
					case ANY:
						builder.append(keywords.outcomeAny());
						break;
					case SUCCESS:
						builder.append(keywords.outcomeSuccess());
						break;
					case FAILURE:
						builder.append(keywords.outcomeFailure());
						break;
					}
					
					builder.append(NEWLINE);
					
					lifecycle.getAfterSteps(outcome)
						.forEach(it -> builder.append(it).append(NEWLINE));
				}
			}
			
			builder.append(NEWLINE);
		}
	}
	
	protected void writeScenario(StringBuilder builder, Scenario scenario) {
		
		builder.append(keywords.scenario());
		
		if (!StringUtils.isBlank(scenario.getTitle())) {
			builder.append(SPACE)
				.append(scenario.getTitle());
		}
		
		builder.append(NEWLINE).append(NEWLINE);
		
		writeMeta(builder, scenario.getMeta());
		
		writeGivenStories(builder, scenario.getGivenStories());
		
		scenario.getSteps().forEach(it -> builder.append(it).append(NEWLINE));
		
		writeExamplesTable(builder, scenario.getExamplesTable());
		
		builder.append(NEWLINE);
	}
	
	protected void writeExamplesTable(StringBuilder builder, ExamplesTable examplesTable) {
		final String examplesTableAsString = examplesTable.asString();
		if (!StringUtils.isBlank(examplesTableAsString)) {
			builder.append(NEWLINE)
				.append(keywords.examplesTable())
				.append(NEWLINE)
				.append(examplesTableAsString)
				.append(NEWLINE);
		}
	}
}
