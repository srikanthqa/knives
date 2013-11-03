@Grapes([
	@Grab(group = 'org.seleniumhq.selenium', module = 'selenium-java', version = '2.37.1'),
	@Grab(group = 'org.seleniumhq.selenium', module = 'selenium-firefox-driver', version = '2.37.1'),
	@Grab(group = 'org.gebish', module = 'geb-core', version = '0.9.2')
])

import geb.Browser
import geb.Configuration
import geb.report.ScreenshotReporter
import org.openqa.selenium.firefox.FirefoxBinary
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.firefox.FirefoxDriver
import java.lang.Thread.UncaughtExceptionHandler

final def cli = new CliBuilder(usage: 'TakeScreenShot i.e. groovy TakeScreenshot.groovy -u http://google.com -i')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.u( longOpt: 'url', argName: 'u', required: true, args: 1, 'absolute url path i.e. http://google.com' )
cli.d( longOpt: 'dir', argName: 'd', required: false, args: 1, 'screenshot stored dir i.e. /tmp default to current directory')
cli.f( longOpt: 'file', argName: 'f', required: false, args: 1, 'PNG file name default to tmp.png')
cli.i( longOpt: 'invisible', argName: 'i', required: false, args: 0, 'showing browser or not default to false. only set to true if you have xvfb installed')
//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

final def url = opt.u
final def dir = opt.d ? opt.d : '.'
final def fname = opt.f ? opt.f : 'tmp'
final def invisible = opt.i as boolean

// Fix on display 10 (hack for now, there is no sniffing yet like ephermal-x.sh
final def DISPLAY_NUM = ':10'
final def firefoxProfile = new FirefoxProfile()
final def firefoxBinary = new FirefoxBinary()

def process = null

try {
	if (invisible) {
		process = "Xvfb ${DISPLAY_NUM} -ac".execute()
		// consume process output (java pitfall on process)
		// suppress race condition on consume stream (a groovy bug) by setting
		// silent exception handler
		process.consumeProcessOutputStream(System.out)
			.setDefaultUncaughtExceptionHandler({ Thread t, Throwable e -> return } as UncaughtExceptionHandler)
			
		process.consumeProcessErrorStream(System.err)
			.setDefaultUncaughtExceptionHandler({ Thread t, Throwable e -> return } as UncaughtExceptionHandler)
		
		firefoxBinary.setEnvironmentProperty('DISPLAY', DISPLAY_NUM);
	}
	
	final def firefoxDriver = new FirefoxDriver(firefoxBinary, firefoxProfile)
	
	final def conf = new Configuration([
		reportsDir : dir,
		reporter: new ScreenshotReporter(),
		driver: { firefoxDriver } // driver need to be wrap in closure if it is of WebDriver
	])
	
	Browser.drive(conf) {
		go url
		 
		report fname
		
		quit()
	}
} finally {
	if (invisible && process != null) {
		process.waitForOrKill(10)
	}
}
