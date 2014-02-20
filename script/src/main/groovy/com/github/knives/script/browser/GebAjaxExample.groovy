package com.github.knives.script.browser

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