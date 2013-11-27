@Grapes([
	@Grab(group='javax.activation', module='activation', version='1.1.1', force=true),
	@Grab(group='javax.mail', module='mail', version='1.4.4', force=true),
	@Grab(group='dumbster', module='dumbster', version='1.6', transitive=false)
])

import com.dumbster.smtp.SimpleSmtpServer
import groovy.transform.Canonical

@Canonical
class RunWhenShuttingDown extends Thread {
	final SimpleSmtpServer server
	
	public void run() {
		println "Shutting down SMTP server"
		
		server.stop()
		
		println "Received ${server.getReceivedEmailSize()} emails"
		
		server.getReceivedEmail().toList().each {
			println it
		}
	}
}

final int DEFAULT_PORT = 8025

final def cli = new CliBuilder(usage: 'StartSmtpServer')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.p( longOpt: 'port', argName: 'p', required: false, args: 1, "default port ${DEFAULT_PORT}")

//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage()
	return
}

final int port = opt.p ? opt.p as int : DEFAULT_PORT

final SimpleSmtpServer server = new SimpleSmtpServer(port)

Runtime.getRuntime().addShutdownHook(new RunWhenShuttingDown(server))

println "Ctrl+C to stop the smtp server"

server.run()