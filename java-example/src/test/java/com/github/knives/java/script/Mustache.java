package com.github.knives.java.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

public class Mustache {

	public static void main(String[] args) throws Throwable {
		ScriptEngineManager engineManager = new ScriptEngineManager();
		ScriptEngine engine = engineManager.getEngineByName("nashorn");
		engine.eval(new FileReader("src/test/resources/mustache.js"));
		Invocable invocable = (Invocable) engine;

		String template = "Email addresses of {{contact.name}}:\n"
				+ "{{#contact.emails}}\n" 
				+ "- {{.}}\n" 
				+ "{{/contact.emails}}";

		String contactJson = "{" 
				+ "\"contact\": {"
				+ "\"name\": \"Mr A\", \"emails\": ["
				+ "\"contact@some.tld\", \"sales@some.tld\"" 
				+ "]}}";

		Object json = engine.eval("JSON");
		Object data = invocable.invokeMethod(json, "parse", contactJson);

		Object mustache = engine.eval("Mustache");
		System.out.println(invocable.invokeMethod(mustache, "render", template, data));
	}
}
