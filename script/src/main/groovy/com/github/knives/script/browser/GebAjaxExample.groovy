package com.github.knives.script.browser
@Grapes([
	@Grab(group = 'org.seleniumhq.selenium', module = 'selenium-java', version = '2.37.1'),
	@Grab(group = 'org.seleniumhq.selenium', module = 'selenium-firefox-driver', version = '2.37.1'),
	@Grab(group = 'org.gebish', module = 'geb-core', version = '0.9.2')
])

import geb.Page
import geb.Browser
import geb.Configuration
import geb.report.ScreenshotReporter
import org.openqa.selenium.Keys

class GooglePage extends Page {
	static url = "https://www.google.com/"
	static at = { title == 'Google' }
	static content = {
		query(wait: true) { $("input", name: "q") }
	}
}

final def conf = new Configuration([
	reportsDir : '.',
	reporter: new ScreenshotReporter(),
	driver: 'firefox'
])

final def testKeywords = [
	'define good', 'define evil', 'define groovy', 'define java'
]

Browser.drive(conf) {
	to GooglePage
	assert at(GooglePage)
	
	testKeywords.each { keyword ->
		query << keyword << Keys.chord(Keys.ENTER)
		
		waitFor { title == "${keyword} - Google Search" }
		
		query.value('')
	}
	
	quit()
}