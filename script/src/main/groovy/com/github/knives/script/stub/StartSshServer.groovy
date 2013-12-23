package com.github.knives.script.stub
final int DEFAULT_PORT = 8022


final def cli = new CliBuilder(usage: 'StartFtpServer')
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

// http://mina.apache.org/sshd-project/embedding_ssh.html
import org.apache.sshd.SshServer

final SshServer sshd = SshServer.setUpDefaultServer()

sshd.setPort(port)

sshd.start()