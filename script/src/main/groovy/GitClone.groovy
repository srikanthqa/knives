@Grapes([
	@Grab(group='org.eclipse.jgit', module='org.eclipse.jgit', version='3.1.0.201310021548-r')
])

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.api.CloneCommand

final def cli = new CliBuilder(usage: 'GitClone -u <url> -d <path/to/where/to/you/want>')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.u( longOpt: 'url', argName: 'u', required: true, args: 1, 'absolute url path i.e. https://github.com/khaing211/knives.git' )
cli.d( longOpt: 'dir', argName: 'd', required: true, args: 1, 'optional directory you want to clone into /tmp/12345')
//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

final def url = opt.u
final def dir = new File(opt.d)

final FileRepositoryBuilder builder = new FileRepositoryBuilder()

final Repository repository = builder.setGitDir(dir).readEnvironment().findGitDir().build()

final Git git = new Git(repository)

final CloneCommand clone = git.cloneRepository()
	.setBare(false)
	.setCloneAllBranches(true)
	.setDirectory(dir)
	.setURI(url)
	
clone.call()
