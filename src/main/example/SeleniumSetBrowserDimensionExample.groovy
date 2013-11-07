@Grapes([
	@Grab(group = 'org.seleniumhq.selenium', module = 'selenium-java', version = '2.37.1'),
	@Grab(group = 'org.seleniumhq.selenium', module = 'selenium-firefox-driver', version = '2.37.1')
	@Grab(group = 'org.gebish', module = 'geb-core', version = '0.9.2')
])

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.Dimension
import geb.Browser

final def driver = new FirefoxDriver()
driver.manage().window().setSize(new Dimension(420, 700))
//driver.manage().window().maximize()

final def conf = new Configuration([ driver: { driver }])

Browser.drive(conf) {

}
