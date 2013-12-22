import org.apache.ftpserver.FtpServerFactory
import org.apache.ftpserver.FtpServer
import org.apache.ftpserver.listener.ListenerFactory
import com.google.common.io.Files
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory
import org.apache.ftpserver.ftplet.UserManager
import org.apache.ftpserver.usermanager.impl.BaseUser
import org.apache.ftpserver.usermanager.impl.WritePermission

final int DEFAULT_PORT = 8021
final String DEFAULT_USER = "guest"
final String DEFAULT_PASS = "password"
final boolean DEFAULT_READONLY = false
final boolean DEFAULT_KEEP_TMPDIR_ON_EXIT = false
final boolean DEFAULT_SECURE_FTP = false

final def cli = new CliBuilder(usage: 'StartFtpServer')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.p( longOpt: 'port', argName: 'p', required: false, args: 1, "default port ${DEFAULT_PORT}")
cli.d( longOpt: 'dir', argName: 'd', required: false, args: 1, 'default directory is a random directory under /tmp/')
cli.u( longOpt: 'user', argName: 'u', required: false, args: 1, "default user '${DEFAULT_USER}'")
cli.j( longOpt: 'password', argName: 'j', required: false, args: 1, "default password '${DEFAULT_PASS}'")
cli.r( longOpt: 'readonly', argName: 'r', "readonly default to ${DEFAULT_READONLY}")
cli.k( longOpt: 'keeptmpdir', argName: 'k', "keep tmp directory on exit default to ${DEFAULT_KEEP_TMPDIR_ON_EXIT}")
cli.s( longOpt: 'secure', argName: 's', "Using sftp default to ${DEFAULT_SECURE_FTP}")
//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage()
	return
}


final boolean keepTmpDirOnExit = opt.k ? true : DEFAULT_KEEP_TMPDIR_ON_EXIT
final int port = opt.p ? opt.p as int : DEFAULT_PORT
final String directory = opt.d ? new File(opt.d).getCanonicalPath() : 
	Files.createTempDir().with { 
		if (keepTmpDirOnExit == false) deleteOnExit() 
		getCanonicalPath() 
	}

final String user = opt.u ? opt.u : DEFAULT_USER
final String password = opt.j ? opt.j : DEFAULT_PASS
final boolean readonly = opt.r ? true : DEFAULT_READONLY
final boolean secure = opt.s ? true : DEFAULT_SECURE_FTP

final FtpServerFactory serverFactory = new FtpServerFactory()
final ListenerFactory factory = new ListenerFactory()
final PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
final UserManager userManager = userManagerFactory.createUserManager();

println "The root directory is at ${directory}"
println "The login credential is ${user}/${password}"
println "The root directory is ${readonly ? '' : 'not'} readonly"
println "Should keep tmp root directory on exit : ${keepTmpDirOnExit}"

// add default user
new BaseUser().with {
	setName(user)
	setPassword(password)
	setHomeDirectory(directory) 
	if (readonly == false) setAuthorities([ new WritePermission() ]);
	
	userManager.save(it)
}

// set the port of the listener
factory.setPort(port)

// replace the default listener
serverFactory.addListener("default", factory.createListener())

// setting user manager
serverFactory.setUserManager(userManager)

// start the server
final FtpServer server = serverFactory.createServer()

server.start()