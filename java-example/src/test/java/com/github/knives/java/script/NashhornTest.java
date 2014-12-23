package com.github.knives.java.script;

import java.util.Arrays;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;

public class NashhornTest {

	// rhino is placed by nashorn in java8
	// http://docs.oracle.com/javase/8/docs/technotes/guides/scripting/nashorn/intro.html
	//
	// http://www.oracle.com/technetwork/articles/java/jf14-nashorn-2126515.html
	// legacy importPackage = JavaImporter
	@Test
	public void test() throws ScriptException {
		ScriptEngineManager mgr = new ScriptEngineManager();
		// we are using the rhino javascript engine
		ScriptEngine engine = mgr.getEngineByName("nashorn");

		// pass a Java collection to javascript
		List<String> list1 = Arrays.asList("Homer", "Bart", "Marge", "Maggie", "Lisa");
		engine.put("list1", list1);
		String jsCode = "var stdout = java.lang.System.out;" 
				+ "var index; "
				+ "var values = list1.toArray();"
				+ "stdout.println('*** Java object to Javascript');"
				+ "for(index in values) {" + "  stdout.println(values[index]);"
				+ "}";

		engine.eval(jsCode);

		// pass a collection from javascript to java
		jsCode = "var collections = new JavaImporter(java.util);"
				+ "with (collections) {"
				+ "var list2 = Arrays.asList(['Moe', 'Barney', 'Ned']); " + "}";
		engine.eval(jsCode);
		
		List<String> list2 = (List<String>) engine.get("list2");
		System.out.println("*** Javascript object to Java");
		for (String val : list2) {
			System.out.println(val);
		}
	}

}
