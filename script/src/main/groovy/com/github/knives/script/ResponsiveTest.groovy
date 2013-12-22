package com.github.knives.script
/**
 * TODO: add user agent, some site don't support responsive unless you have user agent
 * TODO: add cli to pass in the dimension
 */

import geb.Browser
import geb.Configuration
import geb.report.ScreenshotReporter
import org.openqa.selenium.Dimension

final def cli = new CliBuilder(usage: 'ResponsiveTest i.e. groovy ResponsiveTest -u http://getbootstrap.com/')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.u( longOpt: 'url', argName: 'u', required: true, args: 1, 'absolute url path i.e. http://getbootstrap.com/' )
cli.d( longOpt: 'dir', argName: 'd', required: false, args: 1, 'screenshot stored dir i.e. /tmp default to current directory')

//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

final def COMMON_DEVICE_WIDTH = [240, 320, 360, 400, 480, 600, 768, 800, 1020]
final def FIX_HEIGHT = 800
final def url = opt.u
final def dir = opt.d ? opt.d : '.'
final def height = FIX_HEIGHT

final def conf = new Configuration([
	reportsDir : dir,
	reporter: new ScreenshotReporter(),
	driver:  'firefox'])

Browser.drive(conf) {
	go url
	
	COMMON_DEVICE_WIDTH.each { final width ->
		
		driver.manage().window().setSize(new Dimension(width, height))
		
		report "${width}x${height}"
	}
	
	quit()
}
