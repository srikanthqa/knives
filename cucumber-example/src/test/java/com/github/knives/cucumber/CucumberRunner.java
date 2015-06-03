package com.github.knives.cucumber;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

// If not declare features, it would pick current package as classpath to features
// if you declare directory, it would pick all *.feature, no need to use *
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:build/cucumber"}, features = {"classpath:features"})
public class CucumberRunner {


}
