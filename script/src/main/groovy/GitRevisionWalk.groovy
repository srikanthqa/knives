@Grapes([
	@Grab(group='org.eclipse.jgit', module='org.eclipse.jgit', version='3.1.0.201310021548-r')
])

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevTree
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.lib.Constants


final def cli = new CliBuilder(usage: 'GitClone -u <url> -d <path/to/where/to/you/want>')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.d( longOpt: 'dir', argName: 'd', required: true, args: 1, 'repo directory')
//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

final def gitWorkDir = new File(opt.d)

final Git git = Git.open(gitWorkDir)
final Repository repo = git.getRepository()
final RevWalk revWalk = new RevWalk(repo)
final ObjectId lastCommitId = repo.resolve(Constants.HEAD)
final RevCommit commit = revWalk.parseCommit(lastCommitId)

println commit.getFullMessage()
