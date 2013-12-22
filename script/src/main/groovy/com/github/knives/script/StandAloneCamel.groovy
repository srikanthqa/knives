package com.github.knives.script
import org.apache.camel.main.Main
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.Processor
import org.apache.camel.Exchange

/**
 * Example with a little fix
 * http://camel.apache.org/running-camel-standalone-and-have-it-keep-running.html
 * 
 * Fixes:
 * 
 * 1. org.apache.camel.Main is deprecated used org.apache.camel.main.Main
 * 2. MyBean need to parameter Exchange otherwise due to groovy many methods is added to the object
 * 		and create AmbiguousMethodCallException
 * 3. Improvement: try out log endpoint
 */

class MyBean {
	// org.apache.camel.component.bean.AmbiguousMethodCallException occurs
	// if you don't have exchange to distinguish from all methods that added by groovy
	public void callMe(Exchange exchange) {
		println "MyBean.calleMe method has been called"
	}
}

// create a Main instance
Main main = new Main();

// enable hangup support so you can press ctrl + c to terminate the JVM
main.enableHangupSupport();

// bind MyBean into the registery
main.bind("foo", new MyBean());

// add routes
main.addRouteBuilder(new RouteBuilder() {
	@Override
	public void configure() throws Exception {
		from("timer:foo?delay=2000")
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					println "Invoked timer at ${new Date()}"  
				}
			})
			.beanRef("foo")
			.to("log:message")
	}
})

// run until you terminate the JVM
println "Starting Camel. Use ctrl + c to terminate the JVM." 

main.run();

