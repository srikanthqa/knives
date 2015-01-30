package com.github.knives.annotation.processing;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Should see the following run gradle compileTest
 
Note: Methods found in Foo:
  equals
  toString
  hashCode
  annotationType
  getInvocationHandler
  newProxyInstance
  getProxyClass
  isProxyClass
  wait
  wait
  wait
  getClass
  notify
  notifyAll

 *
 */
public class MetricTest {

	@Metric
	public static class Foo {
		
	}
	
	@Test
	public void test() {
		
	}

}
