package com.github.knives.jgit
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder

final def cli = new CliBuilder(usage: 'FindGitRootDirectory -d <path/to/repository>')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.d( longOpt: 'dir', argName: 'd', required: true, args: 1, 'repo directory')
//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage()
	return
}

final FileRepositoryBuilder builder = new FileRepositoryBuilder()
final Repository repository = builder.readEnvironment() 
		.findGitDir(new File(opt.d)) 
		.build()
		
println repository.directory