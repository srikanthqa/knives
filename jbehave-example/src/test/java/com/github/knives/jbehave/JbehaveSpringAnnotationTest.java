package com.github.knives.jbehave;

import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.spring.UsingSpring;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.spring.SpringAnnotatedEmbedderRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromPath;

/**
 * jbehave-spring is integration module between jbehave and spring
 *
 * i.e. you might tie to certain way of integration and disallow from using some part of spring that you want to
 *
 * On the other hand, this is shorter way to initialize jBehave using spring
 *
 *
 *
 */
@RunWith(SpringAnnotatedEmbedderRunner.class)
@UsingEmbedder(embedder = Embedder.class, generateViewAfterStories = true, ignoreFailureInStories = true, ignoreFailureInView = true)
@Configure
@UsingSpring(resources = { "spring/configuration.xml", "spring/steps.xml" })
public class JbehaveSpringAnnotationTest extends InjectableEmbedder {

    @Test
    @Override
    public void run() throws Throwable {
        // runStoriesAsPaths is the execute/entry method of Embedder
        injectedEmbedder().runStoriesAsPaths(storyPaths());
    }

    protected List<String> storyPaths() {
        String searchInDirectory = codeLocationFromPath("../trader/src/main/java").getFile();
        return new StoryFinder().findPaths(searchInDirectory, asList("**/*.story"), null);
    }
}
