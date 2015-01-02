package com.github.knives.browsermob;

import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.ProxyServer;

import org.junit.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowsermobProxyText {

	@Test
	public void test() throws Exception {
		// start the proxy
		ProxyServer server = new ProxyServer(4444);
		server.start();

		// get the Selenium proxy object
		Proxy proxy = server.seleniumProxy();

		// configure it as a desired capability
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.PROXY, proxy);

		// start the browser up
		WebDriver driver = new FirefoxDriver(capabilities);

		// create a new HAR with the label "yahoo.com"
		server.newHar("yahoo.com");

		// open yahoo.com
		driver.get("http://yahoo.com");

		// get the HAR data
		Har har = server.getHar();
		
		driver.quit();
	}

}
