package com.github.knives.jbehave;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromPath;

import java.util.List;

import javax.inject.Inject;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/application-context.xml")
public class JbehaveSpringTest {

    private ApplicationContext applicationContext;

    @Test
    public void testProgramYourConfiguration() {
		Configuration configuration = new MostUsefulConfiguration();
    	
    	Embedder embedder = new Embedder();
    	embedder.useConfiguration(configuration);
    	embedder.useStepsFactory(new SpringStepsFactory(configuration, applicationContext));
    	
    	embedder.runStoriesAsPaths(storyPaths());
    }

    protected List<String> storyPaths() {
        String searchInDirectory = codeLocationFromPath("../trader/src/main/java").getFile();
        return new StoryFinder().findPaths(searchInDirectory, asList("**/*.story"), null);
    }
    
    @Inject
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
