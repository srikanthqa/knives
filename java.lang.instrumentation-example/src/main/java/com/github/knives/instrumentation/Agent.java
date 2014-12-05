package com.github.knives.instrumentation;

import java.lang.instrument.Instrumentation;

/**
 * http://stackoverflow.com/questions/52353/in-java-what-is-the-best-way-to-determine-the-size-of-an-object 
 * 
 * Simply get the instrumentation injection
 */
public class Agent {
    private static Instrumentation instrumentation;

	public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }
	

    public static Instrumentation getInstrumentation() {
    	if (instrumentation == null) {
    		throw new IllegalStateException("instrumentation is not initialize, please run with -javaagent:Agent.jar");
    	}
		return instrumentation;
	}

}
