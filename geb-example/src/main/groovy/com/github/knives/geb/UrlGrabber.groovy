package com.github.knives.geb
import geb.Browser

class UrlGrabber {
	static void main(args) {
		final def cli = new CliBuilder(usage: 'UrlGrabber')
		cli.h( longOpt: 'help', required: false, 'show usage information' )
		cli.u( longOpt: 'url', argName: 'u', required: true, args: 1, 'absolute url path i.e. http://google.com' )
		
		//--------------------------------------------------------------------------
		final def opt = cli.parse(args)
		if (!opt) { return }
		if (opt.h) {
			cli.usage();
			return
		}
		
		final def url = opt.u
		
		Browser.drive {
			go url
			 
			$('a').allElements().each {
				println it.getAttribute('href')
			}
			
			$('img').allElements().each {
				println it.getAttribute('src')
			}
			
			quit()
		}
	}
}