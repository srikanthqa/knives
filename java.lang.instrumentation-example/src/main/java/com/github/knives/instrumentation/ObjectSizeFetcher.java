package com.github.knives.instrumentation;

import java.lang.instrument.Instrumentation;

/**
 * http://stackoverflow.com/questions/52353/in-java-what-is-the-best-way-to-determine-the-size-of-an-object 
 * 
 * Simply get the instrumentation injection
 */
public class ObjectSizeFetcher {
    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        return instrumentation.getObjectSize(o);
    }
}
