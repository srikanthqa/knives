package com.github.knives.script.git
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevTree
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.revwalk.RevSort
import org.eclipse.jgit.storage.file.FileRepositoryBuilder

final def cli = new CliBuilder(usage: 'GitRevisionWalk -u <url> -d <path/to/repository>')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.d( longOpt: 'dir', argName: 'd', required: true, args: 1, 'repository directory')
//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

final FileRepositoryBuilder builder = new FileRepositoryBuilder()

final Repository repo = builder.readEnvironment()
		.findGitDir(new File(opt.d))
		.build()
		
final RevWalk revWalk = new RevWalk(repo)
final ObjectId lastCommitId = repo.resolve(Constants.HEAD)
final RevCommit lastCommit = revWalk.parseCommit(lastCommitId)


revWalk.sort(RevSort.TOPO, true)
revWalk.sort(RevSort.REVERSE, true)
revWalk.markStart(lastCommit)

RevCommit commit

while ((commit = revWalk.next()) != null) {
	println commit.getFullMessage()
}
