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
	
    /**
     * JVM hook to dynamically load javaagent at runtime.
     * 
     * The agent class may have an agentmain method for use when the agent is
     * started after VM startup.
     * 
     * @param args
     * @param inst
     * @throws Exception
     */
    public static void agentmain(String args, Instrumentation inst) throws Exception {
        instrumentation = inst;
    }
    
    public static void initialize() {
    	if (instrumentation == null) {
    		AgentLoader.loadAgent();
    	}
    }
    
    public static Instrumentation getInstrumentation() {
    	initialize();
    	
    	if (instrumentation == null) {
    		throw new IllegalArgumentException("Unable to find Instrumentation, please run with -javagent:/path/to/jar");
    	}
    	
		return instrumentation;
	}

}
