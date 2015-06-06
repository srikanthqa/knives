package com.github.knives.jbehave.configuration;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.jbehave.core.model.StepPattern;
import org.jbehave.core.steps.StepMonitor;
import org.jbehave.core.steps.StepType;

public class SensibleStepMonitor implements StepMonitor {

	@Override
	public void stepMatchesType(String stepAsString, String previousAsString,
			boolean matchesType, StepType stepType, Method method,
			Object stepsInstance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stepMatchesPattern(String step, boolean matches,
			StepPattern stepPattern, Method method, Object stepsInstance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void convertedValueOfType(String value, Type type, Object converted,
			Class<?> converterClass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performing(String step, boolean dryRun) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void usingAnnotatedNameForParameter(String name, int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void usingParameterNameForParameter(String name, int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void usingTableAnnotatedNameForParameter(String name, int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void usingTableParameterNameForParameter(String name, int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void usingNaturalOrderForParameter(int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void foundParameter(String parameter, int position) {
		// TODO Auto-generated method stub
		
	}

}
