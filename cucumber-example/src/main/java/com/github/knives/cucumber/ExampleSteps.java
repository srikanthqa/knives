package com.github.knives.cucumber;

import java.util.List;

import cucumber.api.java.en.Given;

public class ExampleSteps {
	
	@Given("I have (\\d+) cukes in my belly")
    public void I_have_cukes_in_my_belly(int cukes) {
        System.out.format("Cukes: %d\n", cukes);
    }
	
	@Given("the following animals: (.*)")
	public void the_following_animals(List<String> animals) {
        System.out.format("animals: %s\n", animals.toString());
	}
	
	// You can't have both animals / birds step, It create ambiguous exception
	@Given("the following birds:")
	public void the_following_birds(List<String> birds) {
        System.out.format("birds: %s\n", birds.toString());
	}
}
