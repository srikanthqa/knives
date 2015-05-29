package com.github.knives.java.concurrent;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadDumpTest {
    @Test
    public void generateThreadDump() {
        final StringBuilder dump = new StringBuilder();
        final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        final ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), 100);
        for (ThreadInfo threadInfo : threadInfos) {
            dump.append('"')
                .append(threadInfo.getThreadName())
                .append("\" ");

            final Thread.State state = threadInfo.getThreadState();
            dump.append("\n java.lang.Thread.State: ").append(state);

            final StackTraceElement[] stackTraceElements = threadInfo.getStackTrace();
            for (final StackTraceElement stackTraceElement : stackTraceElements) {
                dump.append("\n    at ");
                dump.append(stackTraceElement);
            }
            dump.append("\n\n");
        }

        System.out.println(dump.toString());
        // interesting thread dump
        // Monitor Ctrl-Break for server
        // Signal Dispatcher
        // Finalizer
        // Reference Handler
        // main
    }
}
