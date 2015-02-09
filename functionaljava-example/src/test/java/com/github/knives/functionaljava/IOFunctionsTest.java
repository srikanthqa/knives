package com.github.knives.functionaljava;

import org.junit.Test;
import fj.data.IOFunctions;
/**
 * Created by developer on 2/8/15.
 */
public class IOFunctionsTest {
    @Test
    public void testStdout() {
        IOFunctions.stdoutPrintln("hello world");
    }
}
