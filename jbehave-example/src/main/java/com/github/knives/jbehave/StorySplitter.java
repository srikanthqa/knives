package com.github.knives.jbehave;

import org.jbehave.core.model.Story;

import java.util.List;

public interface StorySplitter {
    List<Story> splitStory(Story story);
}
