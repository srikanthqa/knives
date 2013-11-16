/**
 * @CompileStatic is to improve performance
 * 
 * https://sites.google.com/a/athaydes.com/renato-athaydes/posts/experimentingwithgroovycompilestatic
 */

import groovy.transform.CompileStatic

@CompileStatic
private double distance(double v1, double v2) {
	return v2 - v1
}

println distance(2.0, 3.0)