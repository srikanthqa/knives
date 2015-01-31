package com.github.knives.instrumentation;

import java.lang.management.ManagementFactory;
import java.net.URL;

import com.sun.tools.attach.VirtualMachine;

public class AgentLoader {
    public static void loadAgent() {
    	final Class<?> agentClass = Agent.class;
    	final URL classURL = agentClass.getResource("/" 
    			+ agentClass.getCanonicalName().replace('.', '/') 
    			+ ".class");
    	
    	final String classPath = classURL.getPath();
    	final int beginIndex = classPath.indexOf(':') + 1;
    	final int endIndex = classPath.indexOf('!');
    	
    	final String jarPath = classPath.substring(beginIndex, endIndex);
    	
        final String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        final int p = nameOfRunningVM.indexOf('@');
        final String pid = nameOfRunningVM.substring(0, p);

        try {
            VirtualMachine vm = VirtualMachine.attach(pid);
            vm.loadAgent(jarPath, "");
            vm.detach();
        } catch (Exception ignore) { }
    }
}
