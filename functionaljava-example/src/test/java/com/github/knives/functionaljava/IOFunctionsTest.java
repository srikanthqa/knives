package com.github.knives.functionaljava;

import org.junit.Test;
import fj.data.IOFunctions;

import java.io.IOException;

/**
 * Created by developer on 2/8/15.
 */
public class IOFunctionsTest {
    @Test
    public void testStdout() throws IOException {
        IOFunctions.stdoutPrintln("hello world").run();
    }
}
