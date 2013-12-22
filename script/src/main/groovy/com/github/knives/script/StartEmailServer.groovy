package com.github.knives.script
import groovy.transform.Canonical
import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.ServerSetup
import javax.mail.internet.MimeMessage


public class StartEmailServer {
	@Canonical
	static private class RunWhenShuttingDown extends Thread {
		final GreenMail server
		
		public void run() {
			println "Shutting down the email server"
			
			server.stop()
			
			final MimeMessage[] messages = server.getReceivedMessages()
			
			println "Received ${messages.size()} messages"
			
			messages.each {
				println it
			}
		}
	}
	
	public static void main(String[] args) {
		final def cli = new CliBuilder(usage: 'StartEmailServer')
		cli.h( longOpt: 'help', required: false, 'show usage information' )
		
		//--------------------------------------------------------------------------
		final def opt = cli.parse(args)
		if (!opt) { return }
		if (opt.h) {
			cli.usage()
			return
		}
		
		final def config = [
			new ServerSetup(8025, "localhost", "smtp"),
			new ServerSetup(8110, "localhost", "pop3"),
			new ServerSetup(8143, "localhost", "imap")
		] as ServerSetup[]
		
		final GreenMail server = new GreenMail(config)
		
		Runtime.getRuntime().addShutdownHook(new RunWhenShuttingDown(server))
		
		println "Ctrl+C to stop the email server"
		
		server.start()
	}
}